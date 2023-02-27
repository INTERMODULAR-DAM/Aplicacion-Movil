package ejercicios.dam.intermodulardam.map

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import ejercicios.dam.intermodulardam.main.domain.entity.User
import ejercicios.dam.intermodulardam.ui.composable.MainBottomBar
import ejercicios.dam.intermodulardam.ui.composable.MainDrawer
import ejercicios.dam.intermodulardam.ui.composable.MainTopBar

@Composable
fun Mapa(navController:NavHostController, mapaViewModel: MapViewModel) {
    val currentUser by mapaViewModel.user.observeAsState(initial = User("","","","", "","",  false, "", "", 0))

    mapaViewModel.onInit()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        val scaffoldState = rememberScaffoldState()
        val coroutineScope = rememberCoroutineScope()
        Scaffold(
            modifier = Modifier
                .padding(0.dp)
                .fillMaxSize(),
            scaffoldState = scaffoldState,
            topBar = { MainTopBar(coroutineScope, scaffoldState) },
            content = { MapaScreen(navController = navController, mapaViewModel = mapaViewModel) },
            bottomBar = { MainBottomBar(navController = navController) },
            drawerContent = { MainDrawer(navController = navController, currentUser, coroutineScope, scaffoldState) },
            drawerGesturesEnabled = false
        )
    }
}

@SuppressLint("MissingPermission")
@Composable
fun MapaScreen(navController: NavHostController, mapaViewModel: MapViewModel) {
    val routes by mapaViewModel.routes.observeAsState(initial = listOf())

    val context = LocalContext.current as Activity
    mapaViewModel.fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    var currentLocation by rememberSaveable() { mutableStateOf(LatLng(38.55359897196608, -0.12057169825429333)) }
    val cameraPosition = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(currentLocation, 13F)
    }

    val permission = Manifest.permission.ACCESS_FINE_LOCATION

    val isPermissionGranted by rememberSaveable() { mutableStateOf(isPermissionGranted(context, permission)) }
    var permissionOk by rememberSaveable() { mutableStateOf(false) }

    val launcher = permissionLauncher() { permissionOk = it }
    if(!isPermissionGranted) {
        SideEffect {
            launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    } else {
        mapaViewModel.fusedLocationClient
            .getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
            .addOnSuccessListener { location ->
                if (location != null) {
                    currentLocation = LatLng(location.latitude, location.longitude)
                }
            }
        permissionOk = true
    }
    if(permissionOk) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 55.dp)) {
            GoogleMap(
                cameraPositionState = cameraPosition,
                properties = MapProperties(mapType = MapType.HYBRID, isMyLocationEnabled = true),
                uiSettings = MapUiSettings(myLocationButtonEnabled = true),
            ) {
                routes.forEach { route ->
                    val latlngList:MutableList<LatLng> = mutableListOf()
                    for(i in 0 until route.track.size) {
                        latlngList += LatLng(route.track[i].lat, route.track[i].lng)
                    }
                    Marker(
                        state = rememberMarkerState(position = latlngList[0]),
                        title = route.name,
                        snippet = "Type: ${route.category}"

                    )
                    Polyline(points = latlngList, color = Color.Red)
                    Marker(
                        state = rememberMarkerState(position = latlngList[latlngList.size -1]),
                        title = "End of the route",
                        snippet = "Duration:  ${route.duration}"
                    )
                }
            }
        }
    }

}

@Composable
fun permissionLauncher(onClick: (Boolean) -> Unit): ManagedActivityResultLauncher<String, Boolean> {
    return rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted:Boolean ->
        onClick(isGranted)
    }
}

fun isPermissionGranted(
    context: Context,
    permission: String
): Boolean {
    return ContextCompat.checkSelfPermission(
        context,
        permission
    ) == PackageManager.PERMISSION_GRANTED
}

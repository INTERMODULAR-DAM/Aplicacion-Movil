package ejercicios.dam.intermodulardam.map

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import ejercicios.dam.intermodulardam.R
import ejercicios.dam.intermodulardam.main.domain.entity.User
import ejercicios.dam.intermodulardam.models.Routes
import ejercicios.dam.intermodulardam.ui.theme.calibri
import ejercicios.dam.intermodulardam.utils.MainGreen
import ejercicios.dam.intermodulardam.utils.backgroundGreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun Mapa(navController:NavHostController, mapaViewModel: MapaViewModel) {
    val currentUser: User = User("","","","", "","",  false, "", "")

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
            topBar = { MapaTopBar(coroutineScope, scaffoldState) },
            content = { MapaScreen(navController = navController, mapaViewModel = mapaViewModel) },
            bottomBar = { BottomNavigationBar(navController = navController) },
            drawerContent = { MapaDrawer(navController = navController, currentUser, coroutineScope, scaffoldState) },
            drawerGesturesEnabled = false
        )
    }
}

@Composable
fun MapaScreen(navController: NavHostController, mapaViewModel: MapaViewModel) {
    val currentLocation by mapaViewModel.currentLocation.observeAsState(initial = LatLng(38.55359897196608, -0.12057169825429333))

    val cameraPosition = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(currentLocation, 13F)
    }

    Column(modifier = Modifier.fillMaxSize().padding(bottom = 55.dp)) {
        GoogleMap(
            cameraPositionState = cameraPosition,
            properties = MapProperties(isMyLocationEnabled = true, mapType = MapType.HYBRID)
        ) {

        }
    }
}

@Composable
fun MapaTopBar(coroutineScope: CoroutineScope, scaffoldState: ScaffoldState) {
    TopAppBar(modifier = Modifier
        .fillMaxWidth()
        .padding(0.dp),
        backgroundColor = (MaterialTheme.colors.MainGreen)) {
        Row(modifier= Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { coroutineScope.launch { scaffoldState.drawerState.open() } }, modifier = Modifier.weight(1F)) {
                Icon(imageVector = Icons.Filled.Menu , contentDescription = "Desplegar Menu lateral", tint = Color.White)
            }
            Text(modifier = Modifier.weight(7F), text = "Wikitrail", color = Color.White, fontWeight = FontWeight.W800)
        }
    }
}


@Composable
fun BottomNavigationBar(navController: NavHostController) {
    BottomAppBar(modifier = Modifier
        .fillMaxWidth()
        .padding(0.dp),
        backgroundColor = MaterialTheme.colors.MainGreen)
    {
        Row(modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly) {
            IconButton(onClick = { navController.navigate(Routes.Main.route) }) {
                Icon(imageVector = Icons.Filled.Home, contentDescription = "Página Principal", tint = Color.White)
            }
            IconButton(onClick = { navController.navigate(Routes.CrearRuta.route) }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Crear Ruta", tint = Color.White)
            }
            IconButton(onClick = { navController.navigate(Routes.Mapa.route) }, enabled = false) {
                Icon(imageVector = Icons.Filled.Map, contentDescription = "Mapa", tint = Color.White)
            }
        }
    }
}

@Composable
fun MapaDrawer(navController: NavHostController, user: User, coroutineScope: CoroutineScope, scaffoldState: ScaffoldState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.backgroundGreen),
    ) {
        Row(modifier = Modifier.padding(start = 8.dp, top = 32.dp), verticalAlignment = Alignment.Bottom) {
            Box(
                modifier = Modifier
                    .size(63.dp)
                    .clip(CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Image(
                    modifier = Modifier
                        .scale(1f),
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = "",
                )

                Image(
                    modifier = Modifier
                        .scale(1f),
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "",
                )
            }
            Box(modifier = Modifier.padding(start = 8.dp)) {
                Text(text = "user.name", color = Color.White, style = TextStyle(fontFamily = calibri, fontSize = 20.sp))
            }

            Box(modifier = Modifier) {
                IconButton(
                    modifier = Modifier
                        .absoluteOffset(130.dp, (-32).dp),
                    onClick = { coroutineScope.launch { scaffoldState.drawerState.close() } }) {
                    Icon(imageVector = Icons.Filled.Menu, contentDescription = "Cerrar Drawer", tint = Color.White)
                }
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Row(modifier = Modifier.padding(start = 8.dp, top = 32.dp), verticalAlignment = Alignment.Bottom) {
            Box() {
                Icon(imageVector = Icons.Filled.Person, contentDescription = "Ir a perfil", tint = Color.White)
            }
            Box(modifier = Modifier.padding(start = 8.dp)) {
                ClickableText(
                    text = AnnotatedString("Perfil"),
                    style = TextStyle(fontFamily = calibri, fontSize = 20.sp, color = Color.White),
                    onClick = {navController.navigate(Routes.Perfil.createRoute(user.id))}
                )
            }
        }
    }
}
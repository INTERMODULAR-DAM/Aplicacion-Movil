package ejercicios.dam.intermodulardam.main.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import ejercicios.dam.intermodulardam.R
import ejercicios.dam.intermodulardam.main.domain.entity.User
import ejercicios.dam.intermodulardam.map.MapViewModel
import ejercicios.dam.intermodulardam.map.isPermissionGranted
import ejercicios.dam.intermodulardam.map.permissionLauncher
import ejercicios.dam.intermodulardam.register.ui.PlaceholderForField
import ejercicios.dam.intermodulardam.ui.composable.MainBottomBar
import ejercicios.dam.intermodulardam.ui.composable.MainDrawer
import ejercicios.dam.intermodulardam.ui.composable.MainTopBar
import ejercicios.dam.intermodulardam.ui.theme.backgroundGreen
import ejercicios.dam.intermodulardam.ui.theme.calibri
import ejercicios.dam.intermodulardam.ui.theme.textStyleLogin
import ejercicios.dam.intermodulardam.utils.DisabledBrown
import ejercicios.dam.intermodulardam.utils.MainBrown
import ejercicios.dam.intermodulardam.utils.backgroundGreen

@Composable
fun CreateRoute(navController: NavHostController, mapViewModel: MapViewModel, mainViewModel: MainViewModel) {
    val currentUser by mainViewModel.currentUser.observeAsState(initial = User("","","","", "","",  false, "", "", 0))

    LaunchedEffect(key1 = true){
        mapViewModel.onInit()
    }
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
            content = { CreateRouteScreen(navController, mapViewModel, currentUser) },
            bottomBar = { MainBottomBar(navController = navController) },
            drawerContent = { MainDrawer(navController = navController, currentUser, coroutineScope, scaffoldState) },
            drawerGesturesEnabled = false
        )
    }
}

@Composable
fun CreateRouteScreen(navController: NavHostController, mapaViewModel: MapViewModel, user: User) {
    val name by mapaViewModel.name.observeAsState(initial = "")
    val category by mapaViewModel.category.observeAsState(initial = "")
    val difficulty by mapaViewModel.difficulty.observeAsState(initial = "")
    val track by mapaViewModel.track.observeAsState(initial = arrayListOf())
    val duration by mapaViewModel.duration.observeAsState(initial = "")
    val description by mapaViewModel.description.observeAsState(initial = "")
    val isPrivate by mapaViewModel.isPrivate.observeAsState(initial = false)

    val scrollState = rememberScrollState()

    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colors.backgroundGreen)
        .scrollable(scrollState, Orientation.Vertical)
        .padding(top = 8.dp)) {
        item {
            Column(horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 5.dp, end = 5.dp, bottom = 60.dp)
                    .background(backgroundGreen)){
                Title(modifier = Modifier.align(Alignment.CenterHorizontally))
                Map(mapaViewModel) {
                    mapaViewModel.onRouteChanged(
                        name = name,
                        category = category,
                        difficulty = difficulty,
                        track = it,
                        duration = duration,
                        description = description,
                        isPrivate = isPrivate
                    )
                }
                Spacer(modifier = Modifier.padding(10.dp))
                Name(name) {
                    mapaViewModel.onRouteChanged(
                        name = it,
                        category = category,
                        difficulty = difficulty,
                        track = track,
                        duration = duration,
                        description = description,
                        isPrivate = isPrivate
                    )
                }
                Spacer(modifier = Modifier.padding(10.dp))
                Category(category) {
                    mapaViewModel.onRouteChanged(
                        name = name,
                        category = it,
                        difficulty = difficulty,
                        track = track,
                        duration = duration,
                        description = description,
                        isPrivate = isPrivate
                    )
                }
                Spacer(modifier = Modifier.padding(10.dp))
                Difficulty(difficulty){
                    mapaViewModel.onRouteChanged(
                        name = name,
                        category = category,
                        difficulty = it,
                        track = track,
                        duration = duration,
                        description = description,
                        isPrivate = isPrivate)
                }
                Spacer(modifier = Modifier.padding(10.dp))
                Duration(duration) {
                    mapaViewModel.onRouteChanged(
                        name = name,
                        category = category,
                        difficulty = difficulty,
                        track = track,
                        duration = it,
                        description = description,
                        isPrivate = isPrivate
                    )
                }
                Spacer(modifier = Modifier.padding(10.dp))
                Description(description) {
                    mapaViewModel.onRouteChanged(
                        name = name,
                        category = category,
                        difficulty = difficulty,
                        track = track,
                        duration = duration,
                        description = it,
                        isPrivate = isPrivate
                    )
                }
                Spacer(modifier = Modifier.padding(10.dp))
                Private(isPrivate, Modifier.align(Alignment.CenterHorizontally)) {
                    mapaViewModel.onRouteChanged(
                        name = name,
                        category = category,
                        difficulty = difficulty,
                        track = track,
                        duration = duration,
                        description = description,
                        isPrivate = it
                    )
                }
                Spacer(modifier = Modifier.padding(10.dp))
                CreateRouteButton(mapaViewModel, user.id, navController)

            }
        }
    }
}
@Composable
fun Title(modifier: Modifier) {
    Column(modifier = modifier
        .fillMaxWidth()
        .padding(bottom = 10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painter = painterResource(id = R.drawable.logo_letters), contentDescription = "WikiTrail logo")
        Text(text = "Create route!", fontSize = 28.sp,
            color = Color.White,
            fontWeight = FontWeight.ExtraLight,)
    }

}

@SuppressLint("MissingPermission")
@Composable
fun Map(mapaViewModel: MapViewModel, onMapClick: (MutableList<LatLng>) -> Unit) {
    var currentLocation = LatLng(38.55359897196608, -0.12057169825429333)
    val cameraPosition = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(currentLocation, 13F)
    }
    var track by rememberSaveable { mutableStateOf<List<LatLng>>(value = listOf()) }

    val context = LocalContext.current as Activity
    mapaViewModel.fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
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
        GoogleMap(
            cameraPositionState = cameraPosition,
            properties = MapProperties(isMyLocationEnabled = true, mapType = MapType.HYBRID),
            onMapClick = { location ->
                track = track + location
                onMapClick(track.toMutableList())
            },
            modifier = Modifier.height(300.dp)
        ) {
            track.forEach { point ->
                Marker(state = MarkerState(point))
            }
            Polyline(points = track, color = Color.Red)
        }
    }
}

@Composable
fun Name(name:String, onTextChanged: (String) -> Unit) {
    TextField(
        modifier = Modifier
            .border(
                width = 1.dp,
                brush = Brush.horizontalGradient(listOf(Color.White, Color.White)),
                shape = RoundedCornerShape(12.dp)
            )
            .width(290.dp),
        value = name,
        onValueChange = { onTextChanged(it) },
        label = { PlaceholderForField("Name of the route") },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            textColor = Color.White
        ),
        textStyle = textStyleLogin
    )
}

@Composable
fun Duration(duration:String, onTextChanged: (String) -> Unit) {
    TextField(
        modifier = Modifier.border(
            width = 1.dp,
            brush = Brush.horizontalGradient(listOf(Color.White,Color.White)),
            shape = RoundedCornerShape(12.dp)
        ).width(290.dp),
        value = duration,
        onValueChange = { onTextChanged(it) },
        label = { PlaceholderForField("Duration") },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            textColor = Color.White
        ),
        textStyle = textStyleLogin
    )
}

@Composable
fun Description(description:String, onTextChanged: (String) -> Unit) {
    TextField(
        modifier = Modifier.border(
            width = 1.dp,
            brush = Brush.horizontalGradient(listOf(Color.White,Color.White)),
            shape = RoundedCornerShape(12.dp)
        ).width(290.dp),
        value = description,
        maxLines = 2,
        onValueChange = { onTextChanged(it) },
        label = { PlaceholderForField("Description") },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            textColor = Color.White
        ),
        textStyle = textStyleLogin
    )
}

@Composable
fun Private(private:Boolean, modifier : Modifier ,onCheckChanged: (Boolean) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier){
        Checkbox(
            checked = private,
            onCheckedChange = {onCheckChanged(it)},
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colors.MainBrown,
                checkmarkColor = Color.White
            )
        )
        Text(text = "Make private", color = Color.White, fontFamily = calibri, fontSize = 20.sp)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Category(category:String, onTextChanged: (String) -> Unit) {
    val options = listOf("Hiking", "Roller Skating", "Kayaking")
    var expanded by rememberSaveable { mutableStateOf(false) }
    var selectedOptionText by rememberSaveable {mutableStateOf(category) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        TextField(
            readOnly = true,
            value = selectedOptionText,
            onValueChange = {},
            label = { Text("Choose a category", color = Color.White) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(
                textColor = Color.White,
                placeholderColor = Color.White,
                trailingIconColor = Color.White,
                focusedLabelColor = Color.White,
                focusedIndicatorColor = Color.White,
                unfocusedIndicatorColor = Color.White,
                focusedTrailingIconColor = Color.White
            ),
            textStyle = TextStyle(
                fontFamily = calibri,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    onClick = {
                        selectedOptionText = selectionOption
                        expanded = false
                        onTextChanged(selectionOption)
                    }
                ) {
                    Text(text = selectionOption,fontFamily = calibri)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Difficulty(difficulty:String, onTextChanged: (String) -> Unit) {
    val options = listOf("Easy", "Medium", "Hard", "Expert")
    var expanded by rememberSaveable { mutableStateOf(false) }
    var selectedOptionText by rememberSaveable {mutableStateOf(difficulty) }

    onTextChanged(difficulty)

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        TextField(
            readOnly = true,
            value = selectedOptionText,
            onValueChange = {},
            label = { Text("Choose a difficulty", color = Color.White) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(
                textColor = Color.White,
                placeholderColor = Color.White,
                trailingIconColor = Color.White,
                focusedLabelColor = Color.White,
                focusedIndicatorColor = Color.White,
                unfocusedIndicatorColor = Color.White,
                focusedTrailingIconColor = Color.White
            ),
            textStyle = TextStyle(
                fontFamily = calibri,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    onClick = {
                        selectedOptionText = selectionOption
                        expanded = false
                        onTextChanged(selectionOption)
                    }
                ) {
                    Text(text = selectionOption, fontFamily = calibri)
                }
            }
        }
    }
}

@Composable
fun CreateRouteButton(mapaViewModel: MapViewModel, id:String, navController: NavHostController) {
    val isButtonEnabled by mapaViewModel.isButtonEnabled.observeAsState(initial = false)

    val context = LocalContext.current
    Button(
        modifier = Modifier
            .width(240.dp)
            .height(60.dp),
        onClick = {mapaViewModel.onCreateButtonClick(id, context, navController)},
        shape = RoundedCornerShape(40.dp),
        border= BorderStroke(1.dp, Color.Black),
        enabled = isButtonEnabled,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.DisabledBrown,
            disabledBackgroundColor = Color(0xFF6a6a6a),
        )
    ) {
        Text(
            modifier = Modifier.align(Alignment.CenterVertically),
            text = "Create Route",
            textAlign = TextAlign.Center,
            color = Color.White,
            fontSize = 20.sp,
            fontFamily = calibri
        )
    }
}
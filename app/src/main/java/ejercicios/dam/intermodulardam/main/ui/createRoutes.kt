package ejercicios.dam.intermodulardam.createRoutes.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import ejercicios.dam.intermodulardam.Routes
import ejercicios.dam.intermodulardam.main.domain.entity.User
import ejercicios.dam.intermodulardam.main.ui.MainViewModel
import ejercicios.dam.intermodulardam.map.MapaViewModel
import ejercicios.dam.intermodulardam.map.isPermissionGranted
import ejercicios.dam.intermodulardam.map.permissionLauncher
import ejercicios.dam.intermodulardam.register.ui.PlaceholderForField
import ejercicios.dam.intermodulardam.ui.composable.MainBottomBar
import ejercicios.dam.intermodulardam.ui.composable.MainDrawer
import ejercicios.dam.intermodulardam.ui.composable.MainTopBar
import ejercicios.dam.intermodulardam.ui.theme.calibri
import ejercicios.dam.intermodulardam.ui.theme.textStyleLogin
import ejercicios.dam.intermodulardam.utils.Constants
import ejercicios.dam.intermodulardam.utils.DisabledBrown
import ejercicios.dam.intermodulardam.utils.backgroundGreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun CrearRuta(navController: NavHostController, mapaViewModel: MapaViewModel, mainViewModel: MainViewModel) {
    val currentUser by mainViewModel.currentUser.observeAsState(initial = User("","","","", "","",  false, "", "", 0))

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
            content = { CrearRutaScreen(navController, mapaViewModel, currentUser) },
            bottomBar = { MainBottomBar(navController = navController) },
            drawerContent = { MainDrawer(navController = navController, currentUser, coroutineScope, scaffoldState) },
            drawerGesturesEnabled = false
        )
    }
}

@Composable
fun CrearRutaScreen(navController: NavHostController, mapaViewModel: MapaViewModel, user: User) {
    val name by mapaViewModel.name.observeAsState(initial = "")
    val category by mapaViewModel.category.observeAsState(initial = "Hiking")
    val distance by mapaViewModel.distance.observeAsState(initial = "")
    val difficulty by mapaViewModel.difficulty.observeAsState(initial = "Easy")
    val track by mapaViewModel.track.observeAsState(initial = arrayListOf())
    val duration by mapaViewModel.duration.observeAsState(initial = "")
    val description by mapaViewModel.description.observeAsState(initial = "")
    val isPrivate by mapaViewModel.isPrivate.observeAsState(initial = false)

    val scrollState = rememberScrollState()

    Column(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colors.backgroundGreen)
        .scrollable(scrollState, Orientation.Vertical)
        .padding(top = 8.dp)) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .weight(1F),
        horizontalArrangement = Arrangement.Center) {
            Name(name) {
                mapaViewModel.onRouteChanged(
                    name = it,
                    category = category,
                    distance = distance,
                    difficulty = difficulty,
                    track = track,
                    duration = duration,
                    description = description,
                    isPrivate = isPrivate
                )
            }
        }
        Row(modifier = Modifier
            .fillMaxWidth()
            .weight(1F),
            horizontalArrangement = Arrangement.Center) {
            Distance(distance) {
                mapaViewModel.onRouteChanged(
                    name = name,
                    category = category,
                    distance = it,
                    difficulty = difficulty,
                    track = track,
                    duration = duration,
                    description = description,
                    isPrivate = isPrivate
                )
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        Row(modifier = Modifier
            .fillMaxWidth()
            .weight(1F),
            horizontalArrangement = Arrangement.Center) {
            Category(category) {
                mapaViewModel.onRouteChanged(
                    name = name,
                    category = it,
                    distance = distance,
                    difficulty = difficulty,
                    track = track,
                    duration = duration,
                    description = description,
                    isPrivate = isPrivate
                )
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        Row(modifier = Modifier
            .fillMaxWidth()
            .weight(1F),
            horizontalArrangement = Arrangement.Center) {
            Difficulty(difficulty) {
                mapaViewModel.onRouteChanged(
                    name = name,
                    category = category,
                    distance = distance,
                    difficulty = it,
                    track = track,
                    duration = duration,
                    description = description,
                    isPrivate = isPrivate
                )
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        Row(modifier = Modifier
            .fillMaxWidth()
            .weight(1F),
            horizontalArrangement = Arrangement.Center) {
            Duration(duration) {
                mapaViewModel.onRouteChanged(
                    name = name,
                    category = category,
                    distance = distance,
                    difficulty = difficulty,
                    track = track,
                    duration = it,
                    description = description,
                    isPrivate = isPrivate
                )
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        Row(modifier = Modifier
            .fillMaxWidth()
            .weight(1F),
            horizontalArrangement = Arrangement.Center) {
            Description(description) {
                mapaViewModel.onRouteChanged(
                    name = name,
                    category = category,
                    distance = distance,
                    difficulty = difficulty,
                    track = track,
                    duration = duration,
                    description = it,
                    isPrivate = isPrivate
                )
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        Row(modifier = Modifier
            .fillMaxWidth()
            .weight(1F),
            horizontalArrangement = Arrangement.Center) {
            Private(isPrivate) {
                mapaViewModel.onRouteChanged(
                    name = name,
                    category = category,
                    distance = distance,
                    difficulty = difficulty,
                    track = track,
                    duration = duration,
                    description = description,
                    isPrivate = it
                )
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        Row(modifier = Modifier
            .fillMaxWidth()
            .weight(3F)) {
            Mapa(mapaViewModel) {
                mapaViewModel.onRouteChanged(
                    name = name,
                    category = category,
                    distance = distance,
                    difficulty = difficulty,
                    track = it,
                    duration = duration,
                    description = description,
                    isPrivate = isPrivate
                )
            }
        }
        Row(modifier = Modifier
            .fillMaxWidth()
            .weight(1F),
            horizontalArrangement = Arrangement.Center) {
            CreateRouteButton(mapaViewModel, user.id, navController)
        }
        Spacer(modifier = Modifier.height(80.dp))
    }
}

@SuppressLint("MissingPermission")
@Composable
fun Mapa(mapaViewModel: MapaViewModel, onMapClick: (MutableList<LatLng>) -> Unit) {
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
            }
        ) {
            track.forEach { point ->
                Marker(state = MarkerState(point))
            }
            Polyline(points = track, color = Color.Red)
        }
    }
}



@Composable
fun CrearRutaTopBar(coroutineScope: CoroutineScope, scaffoldState: ScaffoldState) {
    TopAppBar(modifier = Modifier
        .fillMaxWidth()
        .padding(0.dp),
        backgroundColor = (MaterialTheme.colors.backgroundGreen)) {
        Row(modifier= Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { coroutineScope.launch { scaffoldState.drawerState.open() } }, modifier = Modifier.weight(1F)) {
                Icon(imageVector = Icons.Filled.Menu , contentDescription = "Left-hand menu", tint = Color.White)
            }
            Text(modifier = Modifier.weight(7F), text = "Wikitrail", color = Color.White, fontWeight = FontWeight.W800)
        }
    }
}


@Composable
fun CrearRutaDrawer(navController: NavHostController, user: User, coroutineScope: CoroutineScope, scaffoldState: ScaffoldState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.backgroundGreen),
    ) {
        Row(modifier = Modifier
            .padding(start = 8.dp, top = 32.dp)
            .fillMaxWidth(), verticalAlignment = Alignment.Bottom) {
            Box(
                modifier = Modifier
                    .size(63.dp)
                    .clip(CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Image(
                    modifier = Modifier
                        .size(63.dp)
                        .scale(1F),
                    painter = rememberAsyncImagePainter(
                        "http://${Constants.IP_ADDRESS}/api/v1/imgs/users/"+ user.pfp_path
                    ),
                    contentDescription = "Foto de Perfil",
                    contentScale = ContentScale.Crop)
            }
            Box(modifier = Modifier.padding(start = 8.dp)) {
                Text(text = user.name, color = Color.White, style = TextStyle(fontFamily = calibri, fontSize = 24.sp))
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
        Spacer(modifier = Modifier.height(6.dp))
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 32.dp)) {
            Text(text = "Following: ${user.following}", color = Color.White, style = TextStyle(fontFamily = calibri, fontSize = 16.sp))
        }
        Spacer(modifier = Modifier.height(24.dp))
        Row(modifier = Modifier
            .padding(start = 8.dp, top = 32.dp)
            .fillMaxWidth(), verticalAlignment = Alignment.Bottom) {
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

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    BottomAppBar(modifier = Modifier
        .fillMaxWidth()
        .padding(0.dp),
        backgroundColor = MaterialTheme.colors.backgroundGreen)
    {
        Row(modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly) {
            IconButton(onClick = { navController.navigate(Routes.Main.route) }) {
                Icon(imageVector = Icons.Filled.House, contentDescription = "Página Principal", tint = Color.White)
            }
            IconButton(onClick = { navController.navigate(Routes.CrearRuta.route) }, enabled = false) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Crear Ruta", tint = Color.White)
            }
            IconButton(onClick = { navController.navigate(Routes.Mapa.route) }) {
                Icon(imageVector = Icons.Filled.Map, contentDescription = "Mapa", tint = Color.White)
            }
        }
    }
}

@Composable
fun Name(name:String, onTextChanged: (String) -> Unit) {
    TextField(
        modifier = Modifier.border(
            width = 1.dp,
            brush = Brush.horizontalGradient(listOf(Color.White,Color.White)),
            shape = RoundedCornerShape(12.dp)
        ),
        value = name,
        onValueChange = { onTextChanged(it) },
        label = { PlaceholderForField("name") },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            textColor = Color.White
        ),
        textStyle = textStyleLogin
    )
}

@Composable
fun Distance(distance:String, onTextChanged: (String) -> Unit) {
    TextField(
        modifier = Modifier.border(
            width = 1.dp,
            brush = Brush.horizontalGradient(listOf(Color.White,Color.White)),
            shape = RoundedCornerShape(12.dp)
        ),
        value = distance,
        onValueChange = { onTextChanged(it) },
        label = { PlaceholderForField("distance") },
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
        ),
        value = duration,
        onValueChange = { onTextChanged(it) },
        label = { PlaceholderForField("duration") },
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
        ),
        value = description,
        onValueChange = { onTextChanged(it) },
        label = { PlaceholderForField("description") },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            textColor = Color.White
        ),
        textStyle = textStyleLogin
    )
}

@Composable
fun Private(private:Boolean, onCheckChanged: (Boolean) -> Unit) {
    Checkbox(
        checked = private,
        onCheckedChange = {onCheckChanged(it)}
    ) 
    Text(text = "Make private", color = Color.White)
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Category(category:String, onTextChanged: (String) -> Unit) {
    val options = listOf("Hiking", "Roller Skating", "Kayaking")
    var expanded by rememberSaveable { mutableStateOf(false) }
    var selectedOptionText by rememberSaveable {mutableStateOf(category) }

    //set hiking as default option in viewModel
    onTextChanged(category)

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
            label = { Text("Choose a category") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors()
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
                    Text(text = selectionOption)
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

    //set easy as default option in viewModel
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
            label = { Text("Choose a difficulty") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors()
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
                    Text(text = selectionOption)
                }
            }
        }
    }
}

@Composable
fun CreateRouteButton(mapaViewModel: MapaViewModel, id:String, navController: NavHostController) {
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
package ejercicios.dam.intermodulardam.main.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import ejercicios.dam.intermodulardam.main.domain.entity.Publication
import ejercicios.dam.intermodulardam.main.domain.entity.User
import ejercicios.dam.intermodulardam.models.Routes
import ejercicios.dam.intermodulardam.ui.theme.calibri
import ejercicios.dam.intermodulardam.utils.MainGreen
import ejercicios.dam.intermodulardam.utils.backgroundGreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*

@Composable
fun Main(navController:NavHostController, mainViewModel: MainViewModel) {
    val currentUser by mainViewModel.user.observeAsState(initial = User("","","","", "","",  false, "", ""))
    val routes by mainViewModel.routes.observeAsState(initial = listOf())

    mainViewModel.onButtonPress()

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
            content = { MainScreen(navController, mainViewModel, currentUser, routes) },
            bottomBar = { BottomNavigationBar(navController = navController)},
            drawerContent = { MainDrawer(navController = navController, currentUser, coroutineScope, scaffoldState)}
        )
    }
}

@Composable
fun MainTopBar(coroutineScope: CoroutineScope, scaffoldState: ScaffoldState) {
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
            IconButton(onClick = { navController.navigate(Routes.Main.route) }, enabled = false) {
                Icon(imageVector = Icons.Filled.House, contentDescription = "Página Principal", tint = Color.White)
            }
            IconButton(onClick = { navController.navigate(Routes.CrearRuta.route) }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Crear Ruta", tint = Color.White)
            }
            IconButton(onClick = { navController.navigate(Routes.Mapa.route) }) {
                Icon(imageVector = Icons.Filled.Map, contentDescription = "Mapa", tint = Color.White)
            }
        }
    }
}

@Composable
fun MainDrawer(navController: NavHostController, user: User, coroutineScope: CoroutineScope, scaffoldState: ScaffoldState) {
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
                        .size(63.dp)
                        .scale(1F),
                    painter = rememberAsyncImagePainter(
                    "http://192.168.230.74:8080/api/v1/imgs/users/"+ user.pfp_path
                    ),
                    contentDescription = "Foto de Perfil")
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


@Composable
fun MainScreen(navController: NavHostController, mainViewModel: MainViewModel, user: User, routes:List<Publication>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(routes.size) { index ->
            MainCards(navController = navController, mainViewModel = mainViewModel, user = user, routes[index])
            Spacer(modifier = Modifier.height(5.dp))
        }
    }
    Spacer(modifier = Modifier.height(60.dp))
}

@Composable
fun MainCards(navController: NavHostController, mainViewModel: MainViewModel, user: User, route:Publication) {
    Card(modifier = Modifier
        .padding(5.dp)
        .fillMaxWidth(), elevation = 5.dp) {
        Column(modifier = Modifier.fillMaxSize().padding(5.dp)) {
            Row(modifier = Modifier.fillMaxWidth().height(200.dp)) {
                if(route.photos > 0) {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = rememberAsyncImagePainter(
                            "http://192.168.230.74:8080/api/v1/imgs/posts/"
                        ), contentDescription = "Foto de la ruta",
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = rememberAsyncImagePainter(
                            "http://192.168.230.74:8080/api/v1/imgs/posts/noPhotos.png"
                        ), contentDescription = "Foto por defecto",
                        contentScale = ContentScale.Crop
                    )
                }
            }
            Spacer(modifier = Modifier.height(2.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(text = route.name)
            }
        }
    }
}

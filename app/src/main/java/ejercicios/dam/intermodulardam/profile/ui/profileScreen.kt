package ejercicios.dam.intermodulardam.profile.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import ejercicios.dam.intermodulardam.comments.ui.CommentViewModel
import ejercicios.dam.intermodulardam.main.domain.entity.Publication
import ejercicios.dam.intermodulardam.main.domain.entity.User
import ejercicios.dam.intermodulardam.Routes
import ejercicios.dam.intermodulardam.main.ui.*
import ejercicios.dam.intermodulardam.ui.theme.calibri
import ejercicios.dam.intermodulardam.utils.Constants.IP_ADDRESS
import ejercicios.dam.intermodulardam.utils.MainBrown
import ejercicios.dam.intermodulardam.utils.backgroundGreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

@Composable
fun Perfil(navController:NavHostController, id:String, perfilViewModel: PerfilViewModel, comentariosViewModel: CommentViewModel) {
    val currentUser by perfilViewModel.user.observeAsState(initial = User("","","","", "","",  false, "", "", 0))
    val routes by perfilViewModel.posts.observeAsState(initial = listOf())

    perfilViewModel.onInit()

    if(id.isEmpty()) {
        navController.navigate("main")
    } else {
        val scaffoldState = rememberScaffoldState()
        val coroutineScope = rememberCoroutineScope()
        Scaffold(
            modifier = Modifier
                .padding(0.dp)
                .fillMaxSize(),
            scaffoldState = scaffoldState,
            topBar = { PerfilTopBar(coroutineScope, scaffoldState) },
            content = { PerfilScreen(navController, perfilViewModel, currentUser, routes) },
            bottomBar = { PerfilBottomBar(navController = navController) },
            drawerContent = { PerfilDrawer(navController = navController, currentUser, coroutineScope, scaffoldState) },
            drawerGesturesEnabled = false
        )
    }
}

@Composable
fun PerfilScreen(navController: NavHostController, perfilViewModel: PerfilViewModel, user: User, routes: List<Publication>) {
    val scrollState = rememberScrollState()

    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .scrollable(scrollState, Orientation.Vertical)
        .padding(bottom = 60.dp)) {
        items(routes.size) { index ->
            val postCreator =
                ProfileCards(navController, perfilViewModel, user, routes[index])
            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}

@Composable
fun PerfilTopBar(coroutineScope: CoroutineScope, scaffoldState: ScaffoldState) {
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
fun PerfilBottomBar(navController: NavHostController) {
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
fun PerfilDrawer(navController: NavHostController, user:User, coroutineScope: CoroutineScope, scaffoldState: ScaffoldState) {
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
                        "http://$IP_ADDRESS/api/v1/imgs/users/"+ user.pfp_path
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
        Spacer(modifier = Modifier.height(24.dp))
        Row(modifier = Modifier.padding(start = 8.dp, top = 32.dp), verticalAlignment = Alignment.Bottom) {
        }
    }
}

@SuppressLint("SimpleDateFormat")
@Composable
fun ProfileCards(navController: NavHostController, perfilViewModel: PerfilViewModel, user:User, route:Publication) {
    Card(modifier = Modifier
        .padding(5.dp)
        .fillMaxWidth()
        .clip(RoundedCornerShape(10.dp))
        .border(1.dp, MaterialTheme.colors.MainBrown, RoundedCornerShape(10.dp))
        .clickable {
            navController.navigate(
                Routes.Publicacion.createRoute(
                    route.id
                )
            )
        },
        elevation = 20.dp) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)
            .height(200.dp)){
            RouteTitle(route.name, Modifier.align(Alignment.Start))
            RouteCategory(route.category)
            Divider(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                color = Color.LightGray)
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                Column(verticalArrangement =Arrangement.SpaceEvenly ,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(vertical = 5.dp)) {
                    Box(contentAlignment = Alignment.Center){
                        RouteImage(route)
                    }
                    RouteUser(user, Modifier.align(Alignment.CenterHorizontally))
                }

                Divider(
                    Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                        .padding(vertical = 10.dp))

                Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                    Column(modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(horizontal = 10.dp)) {
                        RouteParameter("Distance",route.distance)
                        RouteParameter("Difficulty",route.difficulty)
                    }
                    Column(modifier = Modifier
                        .align(Alignment.CenterVertically)) {
                        RouteParameter("Duration",route.duration)
                        RouteParameter("Date", SimpleDateFormat("dd/MM/yyyy").format(route.date) )
                    }
                }
            }
        }
    }

}
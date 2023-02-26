package ejercicios.dam.intermodulardam.profile.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import ejercicios.dam.intermodulardam.Routes
import ejercicios.dam.intermodulardam.main.domain.entity.Publication
import ejercicios.dam.intermodulardam.main.domain.entity.User
import ejercicios.dam.intermodulardam.main.ui.*
import ejercicios.dam.intermodulardam.ui.theme.backgroundGreen
import ejercicios.dam.intermodulardam.ui.theme.calibri
import ejercicios.dam.intermodulardam.ui.theme.lightGreenCard
import ejercicios.dam.intermodulardam.ui.theme.mediumGreenCard
import ejercicios.dam.intermodulardam.utils.Constants.IP_ADDRESS
import ejercicios.dam.intermodulardam.utils.MainBrown
import ejercicios.dam.intermodulardam.utils.backgroundGreen
import ejercicios.dam.intermodulardam.utils.standardQuadFromTo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

@Composable
fun Perfil(navController:NavHostController, id:String, perfilViewModel: PerfilViewModel) {
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
    
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(top = 10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        PerfilImage(user)
        Spacer(modifier = Modifier.height(10.dp))
        UserInfo(user, navController)
        Spacer(modifier = Modifier.height(10.dp))
        Row(Modifier.fillMaxWidth().padding(start = 5.dp), horizontalArrangement = Arrangement.Start) {Text(text = "Posts: ")}
        Spacer(modifier = Modifier.height(5.dp))
        Row {
            LazyColumn(modifier = Modifier
                .scrollable(scrollState, Orientation.Vertical)
                .padding(bottom = 60.dp)) {
                items(routes.size) { index ->
                    val postCreator =
                        ProfileCards(navController, user, routes[index])
                    Spacer(modifier = Modifier.height(5.dp))
                }
            }
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

@Composable
fun PerfilImage(user:User) {
    Row{
        Image(
            rememberAsyncImagePainter(model = "http://$IP_ADDRESS/api/v1/imgs/users/${user.pfp_path}"),
            contentDescription = "User PFP",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(CircleShape)
                .size(78.dp)
                .align(Alignment.CenterVertically)
        )
    }
}

@Composable
fun UserInfo(user:User, navController: NavHostController) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = user.nick, fontSize = 34.sp, fontFamily = calibri, modifier = Modifier
            .padding(10.dp)
            .align(Alignment.CenterVertically))
        IconButton(onClick = { navController.navigate(Routes.EditProfile.createRoute(user.id)) }) {
            Icon(imageVector = Icons.Filled.Edit, contentDescription = "Editar Perfil")
        }
    }
}

@Composable
fun ProfileCards(navController: NavHostController, user: User, route: Publication) {
    Card(modifier = Modifier
        .padding(5.dp)
        .fillMaxWidth()
        .border(4.dp, MaterialTheme.colors.MainBrown, CutCornerShape(20.dp, 0.dp, 20.dp, 0.dp))
        .clip(CutCornerShape(20.dp, 0.dp, 20.dp, 0.dp))
        .clickable {
            navController.navigate(
                Routes.Publicacion.createRoute(
                    route.id,
                )
            )
        },
        elevation = 20.dp) {
        BoxWithConstraints(modifier = Modifier
            .background(backgroundGreen)
            .padding(7.5.dp)
            .aspectRatio(1f)
            .clip(CutCornerShape(20.dp, 0.dp, 20.dp, 0.dp)))
        {
            val width = constraints.maxWidth
            val height = constraints.maxHeight

            val mediumColoredPoint1 = Offset(0f, height * 0.3f)
            val mediumColoredPoint2 = Offset(width * 0.12f, height * 0.40f)
            val mediumColoredPoint3 = Offset(width * 0.4f, height * 0.55f)
            val mediumColoredPoint4 = Offset(width * 0.75f, height * 0.75f)
            val mediumColoredPoint5 = Offset(width * 1.4f, - -height.toFloat() / 10f)

            val mediumColoredPath = Path().apply {
                moveTo(mediumColoredPoint1.x, mediumColoredPoint1.y)
                standardQuadFromTo(mediumColoredPoint1, mediumColoredPoint2)
                standardQuadFromTo(mediumColoredPoint2, mediumColoredPoint3)
                standardQuadFromTo(mediumColoredPoint3, mediumColoredPoint4)
                standardQuadFromTo(mediumColoredPoint4, mediumColoredPoint5)
                lineTo(width.toFloat() + 100f, height.toFloat() + 100f)
                lineTo(-100f, height.toFloat() + 100f)
                close()
            }

            val lightPoint1 = Offset(0f, height * 0.8f)
            val lightPoint2 = Offset(width * 0.1f, height * 0.85f)
            val lightPoint3 = Offset(width * 0.3f, height * 0.90f)
            val lightPoint4 = Offset(width * 0.65f, height * 0.75F)
            val lightPoint5 = Offset(width * 1.4f, -height.toFloat() / 10f)

            val lightColoredPath = Path().apply {
                moveTo(lightPoint1.x, lightPoint1.y)
                standardQuadFromTo(lightPoint1, lightPoint2)
                standardQuadFromTo(lightPoint2, lightPoint3)
                standardQuadFromTo(lightPoint3, lightPoint4)
                standardQuadFromTo(lightPoint4, lightPoint5)
                lineTo(width.toFloat() + 100f, height.toFloat() + 100f)
                lineTo(-100f, height.toFloat() + 100f)
                close()
            }

            Canvas(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                drawPath(

                    path = mediumColoredPath,
                    color = mediumGreenCard
                )
                drawPath(
                    path = lightColoredPath,
                    color = lightGreenCard
                )
            }
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(5.dp)
                .height(250.dp)){
                RouteTitle(route.name,
                    Modifier
                        .align(Alignment.Start)
                        .padding(bottom = 10.dp))
                RouteCategory(route.category)
                Divider(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    color = Color.LightGray)
                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    Column(verticalArrangement =Arrangement.SpaceEvenly ,
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(vertical = 5.dp)
                            .width(150.dp)) {
                        RouteImage(route)
                        RouteUser(user, Modifier.align(Alignment.CenterHorizontally))
                    }
                    Divider(
                        Modifier
                            .fillMaxHeight()
                            .width(1.dp)
                            .padding(vertical = 10.dp)
                            .background(Color.White))
                    Column(
                        verticalArrangement = Arrangement.SpaceEvenly, modifier = Modifier
                            .fillMaxHeight()
                            .padding(horizontal = 10.dp)
                    ) {
                        RouteParameter("Distance", route.distance)
                        RouteParameter("Difficulty", route.difficulty)
                        RouteParameter("Duration", route.duration)
                        RouteParameter("Created at", SimpleDateFormat("dd/MM/yyyy").format(route.date))
                    }
                }
            }
        }
    }
}
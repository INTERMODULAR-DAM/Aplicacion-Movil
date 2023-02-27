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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import ejercicios.dam.intermodulardam.Routes
import ejercicios.dam.intermodulardam.main.domain.entity.Publication
import ejercicios.dam.intermodulardam.main.domain.entity.User
import ejercicios.dam.intermodulardam.main.ui.*
import ejercicios.dam.intermodulardam.main.ui.WhiteWaitingScreen
import ejercicios.dam.intermodulardam.ui.composable.MainBottomBar
import ejercicios.dam.intermodulardam.ui.composable.MainDrawer
import ejercicios.dam.intermodulardam.ui.composable.MainTopBar
import ejercicios.dam.intermodulardam.ui.theme.backgroundGreen
import ejercicios.dam.intermodulardam.ui.theme.calibri
import ejercicios.dam.intermodulardam.ui.theme.lightGreenCard
import ejercicios.dam.intermodulardam.ui.theme.mediumGreenCard
import ejercicios.dam.intermodulardam.utils.Constants.IP_ADDRESS
import ejercicios.dam.intermodulardam.utils.MainBrown
import ejercicios.dam.intermodulardam.utils.standardQuadFromTo
import java.text.SimpleDateFormat

@Composable
fun Perfil(navController:NavHostController, id:String, profileViewModel: ProfileViewModel) {
    val currentUser by profileViewModel.user.observeAsState(initial = User("","","","", "","",  false, "", "", 0))
    LaunchedEffect(key1 = true){
        profileViewModel.onInit()
    }

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
            topBar = { MainTopBar(coroutineScope, scaffoldState) },
            content = { ProfileScreen(navController, profileViewModel, currentUser) },
            bottomBar = { MainBottomBar(navController = navController) },
            drawerContent = { MainDrawer(navController = navController, currentUser, coroutineScope, scaffoldState) },
            drawerGesturesEnabled = false
        )
    }
}

@Composable
fun ProfileScreen(navController: NavHostController, profileViewModel: ProfileViewModel, user: User) {
    val scrollState = rememberScrollState()
    val routes by profileViewModel.posts.observeAsState(initial = listOf())
    val isLoading by profileViewModel.isLoading.observeAsState(false)

    BoxWithConstraints(modifier = Modifier
        .fillMaxSize()
        .background(backgroundGreen)
        ) {
        BackgroundCard(constraints = constraints)
        Column( horizontalAlignment = Alignment.CenterHorizontally) {
            ProfileImage(user, Modifier.align(Alignment.CenterHorizontally))
            Spacer(modifier = Modifier.height(10.dp))
            UserInfo(user, navController)
            Divider(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp), color = Color.White)
            Text(
                text = "Publications",
                fontFamily = calibri,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 26.sp,
            )
            Divider(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp), color = Color.White)
            if(isLoading){
                WhiteWaitingScreen()
            }else{
                Row(modifier = Modifier) {
                    LazyColumn(modifier = Modifier
                        .scrollable(scrollState, Orientation.Vertical)
                        .padding(bottom = 60.dp)) {
                        items(routes.size) { index ->
                            ProfileCards(navController, user, routes[index])
                            Spacer(modifier = Modifier.height(5.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileImage(user:User, modifier : Modifier) {
    Image(
        rememberAsyncImagePainter(model = "http://$IP_ADDRESS/api/v1/imgs/users/${user.pfp_path}"),
        contentDescription = "User PFP",
        contentScale = ContentScale.Crop,
        modifier = modifier
            .clip(CircleShape)
            .size(78.dp)
    )
}

@Composable
fun UserInfo(user:User, navController: NavHostController) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.background(
        backgroundGreen) ) {
        Text(text = user.nick, fontSize = 34.sp, fontFamily = calibri, color = Color.White,modifier = Modifier
            .padding(10.dp)
            .align(Alignment.CenterVertically))
        IconButton(onClick = { navController.navigate(Routes.EditProfile.createRoute(user.id)) }) {
            Icon(imageVector = Icons.Filled.Edit, contentDescription = "Edit profile", tint= Color.White)
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
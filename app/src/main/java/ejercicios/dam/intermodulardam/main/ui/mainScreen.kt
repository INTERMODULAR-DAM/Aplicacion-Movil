package ejercicios.dam.intermodulardam.main.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
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
import ejercicios.dam.intermodulardam.main.domain.entity.Publication
import ejercicios.dam.intermodulardam.main.domain.entity.User
import ejercicios.dam.intermodulardam.Routes
import ejercicios.dam.intermodulardam.login.data.network.dto.UserDTO
import ejercicios.dam.intermodulardam.ui.composable.*
import ejercicios.dam.intermodulardam.ui.theme.*
import ejercicios.dam.intermodulardam.utils.*
import ejercicios.dam.intermodulardam.utils.Constants.IP_ADDRESS
import kotlinx.coroutines.*
import java.text.SimpleDateFormat

@Composable
fun Main(navController:NavHostController, mainViewModel: MainViewModel) {
    val currentUser by mainViewModel.user.observeAsState(initial = User("","","","", "","",  false, "", "", 0))
    val routes by mainViewModel.routes.observeAsState(initial = listOf())

    mainViewModel.onInit()

    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { MainTopBar(coroutineScope, scaffoldState) },
        content = { MainScreen(navController, mainViewModel, currentUser, routes) },
        bottomBar = { MainBottomBar(navController = navController)},
        drawerContent = { MainDrawer(navController = navController, currentUser, coroutineScope, scaffoldState)},
        drawerGesturesEnabled = false
    )

}


@OptIn(DelicateCoroutinesApi::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun MainScreen(navController: NavHostController, mainViewModel: MainViewModel, user: User, routes:List<Publication>) {
    val scrollState = rememberScrollState()

    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .scrollable(scrollState, Orientation.Vertical)
        .padding(bottom = 60.dp)) {
        items(routes.size) { index ->
//            var creatorUser  = UserDTO("", "", "", "", "", "", false, "", "", listOf())
//            GlobalScope.launch(Dispatchers.IO) {
//                creatorUser = mainViewModel.getPostCreator(routes[index].user)
//            }
            MainCards(
                navController = navController,
                mainViewModel = mainViewModel,
                user = user,
                route = routes[index]
            )
        }
    }
}

@SuppressLint("SimpleDateFormat")
@Composable
fun MainCards(
    navController: NavHostController,
    mainViewModel: MainViewModel,
    user: User,
    route: Publication) {
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
                    color = mediumGreenCard)
                drawPath(
                    path = lightColoredPath,
                    color = lightGreenCard
                )
            }
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(5.dp)
                .height(250.dp)){
                RouteTitle(route.name, Modifier.align(Alignment.Start).padding(bottom = 10.dp))
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

                        RouteParameter("Created at", SimpleDateFormat("dd/MM/yyyy").format(route.date))
                        //RouteUser()
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
                    }
                }
            }
        }
    }
}

@Composable
fun RouteUser(user: UserDTO) {
    Column{
        Text(text = "Created by:", color = Color.LightGray, fontSize = 18.sp, fontFamily = calibri, modifier = Modifier.align(Alignment.CenterHorizontally))
        Row{
            Image(
                rememberAsyncImagePainter(model = "http://$IP_ADDRESS/api/v1/imgs/users/${user.pfp_path}"),
                contentDescription = "User PFP",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(40.dp)
            )
            Text(text = user.nick, fontSize = 14.sp, color = Color.White, modifier = Modifier.padding(10.dp))
        }
    }
}

@Composable
fun RouteImage(route : Publication) {
    val modifier = Modifier
        .size(120.dp, 160.dp)
        .padding(top = 40.dp)

    if(route.photos.size > 0) {
        PostPhoto(name = route.id + "/" + route.photos[0], modifier = modifier)
    } else {
        PostPhoto(name = "noPhotos.png", modifier = modifier)
    }
}

@Composable
fun PostPhoto(name : String, modifier : Modifier){
    Image(
        modifier = modifier,
        painter = rememberAsyncImagePainter(
            "http://$IP_ADDRESS/api/v1/imgs/posts/$name"
        ), contentDescription = "Post photo",
        contentScale = ContentScale.Crop
    )
}

@Composable
fun RouteParameter(parameter : String, value: String) {
    Column(Modifier.padding(10.dp)) {
        Text(text = parameter, color = Color.LightGray, fontSize = 18.sp, fontFamily = calibri)
        Text(text = value, fontSize = 22.sp, color = Color.White, fontFamily = calibri, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun RouteCategory(category: String) {
    Text(
        text = category,
        fontFamily = calibri,
        modifier = Modifier.padding(horizontal = 15.dp, vertical = 2.dp),
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Gray
    )
}

@Composable
fun RouteTitle(title : String, modifier: Modifier) {
    Text(
        text = title,
        modifier = modifier.padding(horizontal = 14.dp),
        fontSize = 26.sp,
        fontFamily = calibri,
        color = Color.White,
        fontWeight = FontWeight.ExtraBold,
    )
}

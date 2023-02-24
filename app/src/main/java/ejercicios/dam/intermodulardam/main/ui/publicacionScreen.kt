package ejercicios.dam.intermodulardam.main.ui

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import ejercicios.dam.intermodulardam.Routes
import ejercicios.dam.intermodulardam.comments.domain.entity.Comentarios
import ejercicios.dam.intermodulardam.comments.ui.ComentariosViewModel
import ejercicios.dam.intermodulardam.main.domain.entity.Publication
import ejercicios.dam.intermodulardam.main.domain.entity.User
import ejercicios.dam.intermodulardam.ui.theme.calibri
import ejercicios.dam.intermodulardam.utils.Constants
import ejercicios.dam.intermodulardam.utils.MainBrown
import ejercicios.dam.intermodulardam.utils.backgroundGreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*

@Composable
fun Publicacion(navController: NavHostController, comentariosViewModel: ComentariosViewModel, id: String) {
    val context = LocalContext.current
    if(id.isEmpty()) {
        Toast.makeText(context, "Ha habido un problema con esta publicación", Toast.LENGTH_SHORT).show()
        navController.navigate("main")
    } else {
        val user by comentariosViewModel.user.observeAsState(initial = User("","","","", "","",  false, "", "", 0))
        val route by comentariosViewModel.route.observeAsState(initial = Publication(arrayListOf(), "", Date(), "", "", "", "", listOf(), "", "", false, ""))
        val comments by comentariosViewModel.comments.observeAsState(initial = listOf())

        comentariosViewModel.onInit(id)

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
                topBar = { PublicationTopBar(coroutineScope, scaffoldState) },
                content = { PublicationContent(user, route, comments, comentariosViewModel) },
                bottomBar = { PublicationBottomBar(navController) },
                drawerContent = { PublicationDrawer(navController, user, coroutineScope, scaffoldState) },
                drawerGesturesEnabled = false
            )
        }
    }
}

@Composable
fun PublicationTopBar(coroutineScope: CoroutineScope, scaffoldState: ScaffoldState) {
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
fun PublicationBottomBar(navController: NavHostController) {
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
fun PublicationDrawer(navController: NavHostController, user: User, coroutineScope: CoroutineScope, scaffoldState: ScaffoldState) {
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
                    contentDescription = "Profile photo",
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
            Text(text = "Followers:", color = Color.White, style = TextStyle(fontFamily = calibri, fontSize = 16.sp))
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
                    text = AnnotatedString("Profile"),
                    style = TextStyle(fontFamily = calibri, fontSize = 20.sp, color = Color.White),
                    onClick = {navController.navigate(Routes.Perfil.createRoute(user.id))}
                )
            }
        }
    }
}

@Composable
fun PublicationContent(user:User, route:Publication, comments:List<Comentarios>, comentariosViewModel: ComentariosViewModel) {
    val scrollState = rememberScrollState()

    if(comments.isNotEmpty()) {
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .scrollable(scrollState, Orientation.Vertical)
            .padding(bottom = 60.dp)) {
            items(comments.size) { index ->
                CommentsContent(user = user, route, comments[index], comentariosViewModel)
                Spacer(modifier = Modifier.height(5.dp))
            }
        }
    } else {
        Column(modifier = Modifier
            .fillMaxSize()
            .scrollable(scrollState, Orientation.Vertical)
            .padding(bottom = 60.dp)) {
            Text("No hay comentarios en esta publicación")
        }
    }
}

@Composable
fun CommentsContent(user:User, route: Publication, comment: Comentarios, comentariosViewModel: ComentariosViewModel) {
    val context = LocalContext.current

    Card(modifier = Modifier
        .padding(5.dp)
        .fillMaxWidth()
        .clip(RoundedCornerShape(10.dp))
        .border(1.dp, MaterialTheme.colors.MainBrown, RoundedCornerShape(10.dp)),
        elevation = 20.dp) {
        Text(text = comment.message)
        if(user.id == comment.user) {
            IconButton(onClick = { comentariosViewModel.onDeleteComment(context, comment.user) }) {
                Icon(imageVector = Icons.Filled.Delete, contentDescription = "Borrar Comentario", tint = Color.Red)
            }
        }
    }
}
package ejercicios.dam.intermodulardam.publication

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import ejercicios.dam.intermodulardam.comments.ui.CommentViewModel
import ejercicios.dam.intermodulardam.main.domain.entity.Publication
import ejercicios.dam.intermodulardam.main.domain.entity.User
import ejercicios.dam.intermodulardam.main.ui.PublicationViewModel
import ejercicios.dam.intermodulardam.main.ui.WaitingScreen
import ejercicios.dam.intermodulardam.ui.composable.MainBottomBar
import ejercicios.dam.intermodulardam.ui.composable.MainDrawer
import ejercicios.dam.intermodulardam.ui.composable.MainTopBar

@Composable
fun PublicationView(navController: NavHostController, publicationViewModel: PublicationViewModel, commentsViewModel: CommentViewModel, id: String) {

    publicationViewModel.onInit(id)
    val userCreator by publicationViewModel.user.observeAsState()
    val currentUser by publicationViewModel.currentUser.observeAsState()
    val route by publicationViewModel.route.observeAsState()
    val isLoading by publicationViewModel.isLoading.observeAsState()

    if(isLoading!!){
        WaitingScreen()
    }else{
        val scaffoldState = rememberScaffoldState()
        val coroutineScope = rememberCoroutineScope()
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = { MainTopBar(coroutineScope, scaffoldState) },
            content = {
                ContentPublicationView(
                    route = route!!,
                    userCreator = userCreator!!,
                    currentUser = currentUser!!,
                    commentsViewModel = commentsViewModel
                ) },
            bottomBar = { MainBottomBar(navController = navController) },
            drawerContent = { MainDrawer(navController = navController, currentUser!!, coroutineScope, scaffoldState) },
            drawerGesturesEnabled = false
        )
    }



}

@Composable
fun ContentPublicationView(
    route: Publication,
    userCreator: User,
    currentUser: User,
    commentsViewModel: CommentViewModel) {
    Text("${route.name} ${userCreator.name} ${currentUser.name} ")

}

/*@Composable
fun PublicationView(navController: NavHostController) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(5.dp)) {
        //Mostrar comentarios propios a la derecha con otro background y boton de borrar
        if(user.id == comment.user) {
            Row() {
                Text(text = user.name)
                Text(text = comment.message)
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(horizontalArrangement = Arrangement.End) {
                IconButton(onClick = { comentariosViewModel.onDeleteComment(context, comment.id) }) {
                    Icon(imageVector = Icons.Filled.Delete, contentDescription = "Borrar Comentario", tint = Color.Red)
                }
            }
            //Mostrar otros comentarios a la izquierda con background default
        } else {
            Row() {
                Text(text = user.name)
                Text(text = comment.message)
            }
        }
    }
}*/

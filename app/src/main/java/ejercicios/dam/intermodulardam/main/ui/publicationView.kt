package ejercicios.dam.intermodulardam.publication

import android.widget.Toast
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import ejercicios.dam.intermodulardam.comments.ui.CommentViewModel
import ejercicios.dam.intermodulardam.main.ui.MainScreen
import ejercicios.dam.intermodulardam.ui.composable.MainBottomBar
import ejercicios.dam.intermodulardam.ui.composable.MainDrawer
import ejercicios.dam.intermodulardam.ui.composable.MainTopBar

@Composable
fun Publicacion(navController: NavHostController, commentsViewModel: CommentViewModel, id: String) {
    val context = LocalContext.current
    commentsViewModel.onInit(id)

    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { MainTopBar(coroutineScope, scaffoldState) },
        content = { PublicationView(navController) },
        bottomBar = { MainBottomBar(navController = navController) },
        drawerContent = { MainDrawer(navController = navController, currentUser, coroutineScope, scaffoldState) },
        drawerGesturesEnabled = false
    )
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

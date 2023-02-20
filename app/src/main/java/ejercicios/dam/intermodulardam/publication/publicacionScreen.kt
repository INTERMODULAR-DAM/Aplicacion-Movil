package ejercicios.dam.intermodulardam.publication

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import ejercicios.dam.intermodulardam.comments.ui.ComentariosViewModel

@Composable
fun Publicacion(navController: NavHostController, comentariosViewModel: ComentariosViewModel, id: String) {
    val context = LocalContext.current
    if(id.isEmpty()) {
        Toast.makeText(context, "Ha habido un problema con esta publicación", Toast.LENGTH_SHORT).show()
        navController.navigate("main")
    } else {
        /*TODO SCAFFOLD AND SCREEN DESIGN*/
    }
}
package ejercicios.dam.intermodulardam.publication

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun Publicacion(navController: NavHostController, id: String) {
    if(id.isEmpty()) {
        navController.navigate("main")
    } else {
        /*TODO SCAFFOLD AND SCREEN DESIGN*/
    }
}
package ejercicios.dam.intermodulardam.profile

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun Perfil(navController:NavHostController, id:String) {
    if(id.isEmpty()) {
        navController.navigate("main")
    } else {
        /*TODO SCAFFOLD AND SCREEN DESIGN*/
    }
}
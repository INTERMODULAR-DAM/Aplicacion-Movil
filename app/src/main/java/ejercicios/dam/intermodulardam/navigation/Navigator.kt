package ejercicios.dam.intermodulardam.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ejercicios.dam.intermodulardam.login.ui.Login
import ejercicios.dam.intermodulardam.login.ui.LoginViewModel
import ejercicios.dam.intermodulardam.login.ui.Registro.RegisterScreen
import ejercicios.dam.intermodulardam.login.ui.Registro.RegistroViewModel
import ejercicios.dam.intermodulardam.main.Main
import ejercicios.dam.intermodulardam.main.ui.Favoritas.Favoritas
import ejercicios.dam.intermodulardam.mapa.ui.Mapa
import ejercicios.dam.intermodulardam.mapa.ui.MapaViewModel
import ejercicios.dam.intermodulardam.models.Routes
import ejercicios.dam.intermodulardam.perfil.Perfil
import ejercicios.dam.intermodulardam.publicacion.Publicacion
import ejercicios.dam.intermodulardam.siguiendo.Siguiendo
import ejercicios.dam.intermodulardam.splashscreen.SplashScreen

@Composable
fun CustomNavigator(loginViewModel: LoginViewModel, mapaViewModel: MapaViewModel, registroViewModel: RegistroViewModel) {

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.Splash.route) {
        composable(route= Routes.Splash.route) {
            SplashScreen(navController = navController)
        }
        composable(route=Routes.Login.route) {
            Login(navController = navController, loginViewModel = loginViewModel)
        }
        composable(route=Routes.Mapa.route) {
            Mapa(navController = navController, mapaViewModel = mapaViewModel)
        }
        composable(route=Routes.Registro.route) {
            RegisterScreen(navController = navController, registroViewModel)
        }
        composable(route=Routes.Main.route) {
            Main(navController = navController)
        }
        composable(route=Routes.Favoritas.route) {
            Favoritas(navController = navController)
        }
        composable(
            route = Routes.Publicacion.route,
            arguments = listOf(navArgument("id") {
                type= NavType.IntType
            })
        ) { navBackStackEntry ->
            Publicacion(
                navController = navController,
                id = navBackStackEntry.arguments?.getInt("id")?:0
            )
        }
        composable(
            route = Routes.Perfil.route,
            arguments = listOf(navArgument("id") {
                type = NavType.IntType
            })
        ) { navBackStackEntry ->
            Perfil(
                navController = navController,
                id = navBackStackEntry.arguments?.getInt("id") ?: 0
            )
        }
        composable(
            route = Routes.Siguiendo.route,
            arguments = listOf(navArgument("id") {
                type = NavType.IntType
            })
        ) { navBackStackEntry ->
            Siguiendo(
                navController = navController,
                id = navBackStackEntry.arguments?.getInt("id")?:0
            )
        }
    }
}

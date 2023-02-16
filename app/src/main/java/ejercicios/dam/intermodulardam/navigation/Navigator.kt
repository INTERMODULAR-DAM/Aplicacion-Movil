package ejercicios.dam.intermodulardam.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ejercicios.dam.intermodulardam.createRoutes.ui.CrearRuta
import ejercicios.dam.intermodulardam.login.ui.Login
import ejercicios.dam.intermodulardam.login.ui.LoginViewModel
import ejercicios.dam.intermodulardam.register.ui.RegistroViewModel
import ejercicios.dam.intermodulardam.register.ui.RegisterScreen
import ejercicios.dam.intermodulardam.main.ui.Main
import ejercicios.dam.intermodulardam.main.ui.MainViewModel
import ejercicios.dam.intermodulardam.map.Mapa
import ejercicios.dam.intermodulardam.map.MapaViewModel
import ejercicios.dam.intermodulardam.models.Routes
import ejercicios.dam.intermodulardam.profile.Perfil
import ejercicios.dam.intermodulardam.publication.Publicacion
import ejercicios.dam.intermodulardam.splashscreen.SplashScreen

@Composable
fun CustomNavigator(loginViewModel: LoginViewModel, mapaViewModel: MapaViewModel, registroViewModel: RegistroViewModel, mainViewModel: MainViewModel) {

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
            Main(navController = navController, mainViewModel = mainViewModel)
        }
        composable(route=Routes.CrearRuta.route) {
            CrearRuta(navController = navController, mapaViewModel, mainViewModel)
        }
        composable(
            route = Routes.Publicacion.route,
            arguments = listOf(navArgument("id") {
                type= NavType.StringType
            })
        ) { navBackStackEntry ->
            Publicacion(
                navController = navController,
                id = navBackStackEntry.arguments?.getString("id")?:""
            )
        }
        composable(
            route = Routes.Perfil.route,
            arguments = listOf(navArgument("id") {
                type = NavType.StringType
            })
        ) { navBackStackEntry ->
            Perfil(
                navController = navController,
                id = navBackStackEntry.arguments?.getString("id")?:""
            )
        }
    }
}

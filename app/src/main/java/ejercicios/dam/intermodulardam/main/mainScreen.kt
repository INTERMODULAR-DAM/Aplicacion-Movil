package ejercicios.dam.intermodulardam.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ejercicios.dam.intermodulardam.main.domain.Publication
import ejercicios.dam.intermodulardam.main.domain.User
import ejercicios.dam.intermodulardam.utils.BottomNavigationBar
import ejercicios.dam.intermodulardam.utils.MainTopBar

@Composable
fun Main(navController:NavHostController) {
    /* VARIABLES */
    //val currentUser = (usuario logeado)
    //val routes = (variable para recoger los json de las publicaciones)

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        val scaffoldState = rememberScaffoldState()
        Scaffold(
            modifier = Modifier
                .padding(0.dp)
                .fillMaxSize(),
            scaffoldState = scaffoldState,
            topBar = { MainTopBar() },
            //content = { MainScreen(navController, currentUser, routes)},
            content = { MainScreen(navController)},
            bottomBar = { BottomNavigationBar(navController = navController)}
        )
    }
}

@Composable
fun MainScreen(navController: NavHostController) {
    /*TODO*/
}

/*
@Composable
fun MainScreen(navController: NavHostController, user:User, routes:Publication) {
    /*TODO*/
}
*/

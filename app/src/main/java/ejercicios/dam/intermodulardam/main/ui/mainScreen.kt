package ejercicios.dam.intermodulardam.main.ui

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
import java.util.*

@Composable
fun Main(navController:NavHostController) {
    val currentUser: User = User("","","","", Date(),"", "", false, "", "", "", listOf())
    val routes: List<Publication> = listOf()

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
            content = { MainScreen(navController, currentUser, routes) },
            bottomBar = { BottomNavigationBar(navController = navController)}
        )
    }
}


@Composable
fun MainScreen(navController: NavHostController, user:User, routes:List<Publication>) {

}


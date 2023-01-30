package ejercicios.dam.intermodulardam.utils

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun MainScaffold(navController: NavHostController) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        modifier = Modifier
            .padding(0.dp)
            .fillMaxSize(),
        scaffoldState = scaffoldState,
        topBar = { MainTopBar() },
        bottomBar = { BottomNavigationBar(navController = navController) },
        drawerContent = { Drawer(navController = navController) }
    ) {}
}


@Composable
fun MainTopBar() {
    TopAppBar(modifier = Modifier
        .fillMaxWidth()
        .padding(0.dp),
        backgroundColor = (MaterialTheme.colors.MainGreen)) {
        Row(modifier= Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = {  }, modifier = Modifier.weight(1F)) {
                Icon(imageVector = Icons.Filled.Menu, contentDescription = "Desplegar Menu lateral")
            }
            Text(modifier = Modifier.weight(7F), text = "Wikitrail", color = Color.White, fontWeight = FontWeight.W800)
            Icon(modifier = Modifier.weight(1F), imageVector = Icons.Filled.Add, contentDescription = "Añadir")
        }
    }
}

@Composable
fun LoginTopBar() {
    TopAppBar(modifier = Modifier
        .fillMaxWidth()
        .padding(0.dp),
        backgroundColor = (MaterialTheme.colors.MainGreen)) {
        Row(modifier= Modifier.fillMaxSize().padding(5.dp, 0.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
            Text(modifier = Modifier.weight(7F), text = "Wikitrail", color = Color.White, fontWeight = FontWeight.W800)
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically) {
        /*TODO*/
    }
}

@Composable
fun Drawer(navController: NavHostController) {
    Box {
        /*TODO*/
    }
}
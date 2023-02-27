package ejercicios.dam.intermodulardam.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import ejercicios.dam.intermodulardam.Routes
import ejercicios.dam.intermodulardam.main.domain.entity.User
import ejercicios.dam.intermodulardam.ui.theme.calibri
import ejercicios.dam.intermodulardam.utils.Constants
import ejercicios.dam.intermodulardam.utils.MainBrown
import ejercicios.dam.intermodulardam.utils.backgroundGreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun MainBottomBar(navController: NavHostController) {
    BottomAppBar(modifier = Modifier
        .fillMaxWidth()
        .padding(0.dp),
        backgroundColor = MaterialTheme.colors.backgroundGreen)
    {
        Row(modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly) {
            IconButton(onClick = { navController.navigate(Routes.Main.route) }) {
                Icon(imageVector = Icons.Filled.House, contentDescription = "Main page", tint = Color.White)
            }
            IconButton(onClick = { navController.navigate(Routes.CrearRuta.route) }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Create Route", tint = Color.White)
            }
            IconButton(onClick = { navController.navigate(Routes.Mapa.route) }) {
                Icon(imageVector = Icons.Filled.Map, contentDescription = "Map", tint = Color.White)
            }
        }
    }
}

@Composable
fun MainTopBar(coroutineScope: CoroutineScope, scaffoldState: ScaffoldState) {
    TopAppBar(modifier = Modifier
        .fillMaxWidth()
        .padding(0.dp),
        backgroundColor = (MaterialTheme.colors.backgroundGreen)) {
        Row(modifier= Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { coroutineScope.launch { scaffoldState.drawerState.open() } }, modifier = Modifier.weight(1F)) {
                Icon(imageVector = Icons.Filled.Menu , contentDescription = "Left-hand menu", tint = Color.White)
            }
            Text(modifier = Modifier.weight(8F), text = "Wikitrail", color = Color.White, fontWeight = FontWeight.W800,)
        }
    }
}

@Composable
fun MainDrawer(navController: NavHostController, user: User, coroutineScope: CoroutineScope, scaffoldState: ScaffoldState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.backgroundGreen)
    ) {

        Row(modifier = Modifier
            .padding(start = 8.dp, top = 32.dp)
            .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically) {
            Image(
                modifier = Modifier
                    .size(65.dp)
                    .scale(1F)
                    .clip(RoundedCornerShape(10.dp)),
                painter = rememberAsyncImagePainter(
                    "http://${Constants.IP_ADDRESS}/api/v1/imgs/users/"+ user.pfp_path
                ),
                contentDescription = "Profile photo",
                contentScale = ContentScale.Crop)

            Column(Modifier.width(200.dp)) {
                Text(text = user.nick, color = Color.White, style = TextStyle(fontFamily = calibri, fontSize = 24.sp), maxLines = 1, overflow = TextOverflow.Ellipsis ,modifier = Modifier.padding(start = 20.dp))
                Text(text = "Following: ${user.following}", color = Color.White, style = TextStyle(fontFamily = calibri, fontSize = 16.sp), modifier = Modifier
                    .padding(start = 20.dp))
            }
            IconButton(
               modifier = Modifier
                    .padding(end = 40.dp),
                onClick = { coroutineScope.launch { scaffoldState.drawerState.close()} }) {
                Icon(imageVector = Icons.Filled.Menu, contentDescription = "Close drawer", tint = Color.White)
                }
        }

        Divider(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp), color = Color.White)
        Spacer(modifier = Modifier.height(24.dp))
       Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
           Row(modifier = Modifier
               .padding(start = 20.dp)
               .fillMaxWidth(), verticalAlignment = Alignment.Bottom) {

               Icon(imageVector = Icons.Filled.Person, contentDescription = "Goes to profile view", tint = Color.White, modifier = Modifier.size(30.dp))
               Text(
                   text = "Profile",
                   style = TextStyle(fontFamily = calibri, fontSize = 26.sp, color = Color.White),
                   modifier = Modifier
                       .clickable { navController.navigate(Routes.Perfil.createRoute(user.id)) }
                       .padding(start = 20.dp)
               )
           }
           Row(modifier = Modifier
               .padding(20.dp)
               .fillMaxWidth()
               .align(Alignment.End),
               verticalAlignment = Alignment.CenterVertically) {

               Icon(imageVector = Icons.Filled.Logout, contentDescription = "Goes to profile view", tint = Color.Red, modifier = Modifier.size(30.dp))
               Text(
                   text = "Log out",
                   style = TextStyle(fontFamily = calibri, fontSize = 26.sp, fontWeight = FontWeight.Bold ,color = Color.White),
                   modifier = Modifier
                       .clickable { navController.navigate(Routes.Login.route) }
                       .padding(start = 20.dp)
               )
           }
       }

    }
}
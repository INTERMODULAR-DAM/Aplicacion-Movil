package ejercicios.dam.intermodulardam.profile.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import ejercicios.dam.intermodulardam.main.domain.entity.User
import ejercicios.dam.intermodulardam.ui.composable.MainBottomBar
import ejercicios.dam.intermodulardam.ui.composable.MainDrawer
import ejercicios.dam.intermodulardam.ui.composable.MainTopBar
import ejercicios.dam.intermodulardam.ui.theme.calibri
import ejercicios.dam.intermodulardam.ui.theme.textStyleLogin
import ejercicios.dam.intermodulardam.utils.DisabledBrown
import ejercicios.dam.intermodulardam.utils.backgroundGreen

@Composable
fun EditProfile(navController: NavHostController, id: String, perfilViewModel: PerfilViewModel) {
    val currentUser by perfilViewModel.user.observeAsState(
        initial = User(
            "",
            "",
            "",
            "",
            "",
            "",
            false,
            "",
            "",
            0
        )
    )
    perfilViewModel.onInit()

    if (id.isEmpty()) {
        navController.navigate("main")
    } else {
        val scaffoldState = rememberScaffoldState()
        val coroutineScope = rememberCoroutineScope()
        Scaffold(
            modifier = Modifier
                .padding(0.dp)
                .fillMaxSize(),
            scaffoldState = scaffoldState,
            topBar = { MainTopBar(coroutineScope, scaffoldState) },
            content = { EditProfileContent(currentUser, perfilViewModel, navController) },
            bottomBar = { MainBottomBar(navController) },
            drawerContent = {
                MainDrawer(
                    navController,
                    currentUser,
                    coroutineScope,
                    scaffoldState
                )
            },
            drawerGesturesEnabled = false
        )
    }
}

@Composable
fun EditProfileContent(user: User, perfilViewModel: PerfilViewModel,navController: NavHostController) {
    val name by perfilViewModel.nombre.observeAsState(initial = user.name)
    val lastname by perfilViewModel.apellidos.observeAsState(initial = user.lastname)
    val email by perfilViewModel.email.observeAsState(initial = user.email)
    val phone by perfilViewModel.phone.observeAsState(initial = user.phone_number)
    val nick by perfilViewModel.nick.observeAsState(initial = user.nick)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.backgroundGreen),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        Name(name) {
            perfilViewModel.onEditProfileChanged(
                email = email,
                name = it,
                ape = lastname,
                nick = nick,
                phone = phone
            )
        }
        MySpacer()
        LastName(lastname) {
            perfilViewModel.onEditProfileChanged(
                name = name,
                ape = it,
                nick = nick,
                email = email,
                phone = phone,
            )
        }
        MySpacer()
        Nick(nick) {
            perfilViewModel.onEditProfileChanged(
                name = name,
                ape = lastname,
                nick = it,
                email = email,
                phone = phone,
            )
        }
        MySpacer()
        Email(email) {
            perfilViewModel.onEditProfileChanged(
                name = name,
                ape = lastname,
                nick = nick,
                email = it,
                phone = phone,
            )
        }
        MySpacer()
        Phone(phone) {
            perfilViewModel.onEditProfileChanged(
                name = name,
                ape = lastname,
                nick = nick,
                email = email,
                phone = it,
            )
        }
        MySpacer()
        EditProfileButton(perfilViewModel, navController)
    }
}

@Composable
fun Name(name:String, onTextChanged: (String) -> Unit) {
    Row(modifier = Modifier.padding(0.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
        TextField(
            modifier = Modifier.border(
                width = 1.dp,
                brush = Brush.horizontalGradient(listOf(Color.White, Color.White)),
                shape = RoundedCornerShape(12.dp),
            ),
            value = name,
            onValueChange = { onTextChanged(it) },
            label = { PlaceholderForField("Name") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                textColor = Color.White
            ),
            textStyle = textStyleLogin,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Ascii)
        )
    }
}


@Composable
fun LastName(lastname:String, onTextChanged: (String) -> Unit) {
    Row(modifier = Modifier.padding(0.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
        TextField(
            modifier = Modifier.border(
                width = 1.dp,
                brush = Brush.horizontalGradient(listOf(Color.White,Color.White)),
                shape = RoundedCornerShape(12.dp)
            ),
            value = lastname,
            onValueChange = { onTextChanged(it) },
            label = { PlaceholderForField("Lastname") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                textColor = Color.White
            ),
            textStyle = textStyleLogin,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Ascii)
        )
    }
}

@Composable
fun Nick(nick:String, onTextChanged: (String) -> Unit) {
    Row(modifier = Modifier.padding(0.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
        TextField(
            modifier = Modifier.border(
                width = 1.dp,
                brush = Brush.horizontalGradient(listOf(Color.White,Color.White)),
                shape = RoundedCornerShape(12.dp)
            ),
            value = nick,
            onValueChange = { onTextChanged(it) },
            label = { PlaceholderForField("Nick") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                textColor = Color.White
            ),
            textStyle = textStyleLogin,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Ascii)
        )
    }
}

@Composable
fun Email(email:String, onTextChanged: (String) -> Unit) {
    Row(modifier = Modifier.padding(0.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
        TextField(
            modifier = Modifier.border(
                width = 1.dp,
                brush = Brush.horizontalGradient(listOf(Color.White,Color.White)),
                shape = RoundedCornerShape(12.dp)
            ),
            value = email,
            onValueChange = { onTextChanged(it) },
            label = { PlaceholderForField("Email") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                textColor = Color.White
            ),
            textStyle = textStyleLogin
        )
    }
}

@Composable
fun Phone(phone:String, onTextChanged: (String) -> Unit) {
    Row(modifier = Modifier.padding(0.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
        TextField(
            modifier = Modifier.border(
                width = 1.dp,
                brush = Brush.horizontalGradient(listOf(Color.White,Color.White)),
                shape = RoundedCornerShape(12.dp)
            ),
            value = phone,
            onValueChange = { onTextChanged(it) },
            label = { PlaceholderForField("Phone") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                textColor = Color.White
            ),
            textStyle = textStyleLogin
        )
    }
}

@Composable
fun PlaceholderForField(text : String){
    Text(text = text, color = Color.White, fontFamily = calibri)
}

@Composable
fun MySpacer(){
    Spacer(modifier = Modifier
        .height(10.dp)
        .fillMaxWidth())
}

@Composable
fun EditProfileButton(perfilViewModel: PerfilViewModel, navController: NavHostController) {
    val context = LocalContext.current
    val isButtonEnabled by perfilViewModel.isButtonEnabled.observeAsState(initial = false)
    Row(modifier = Modifier
        .padding(0.dp)
        .fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Button(
            modifier = Modifier
                .width(220.dp)
                .height(60.dp),
            onClick = {
                perfilViewModel.onEditProfile(context, navController)
            },
            shape = RoundedCornerShape(40.dp),
            border= BorderStroke(1.dp, Color.Black),
            enabled = isButtonEnabled,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.DisabledBrown,
                disabledBackgroundColor = Color(0xFF6a6a6a),

                )
        ) {
            Text(
                modifier = Modifier.align(Alignment.CenterVertically),
                text = "Edit Profile",
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 20.sp,
                fontFamily = calibri
            )
        }
    }
}
package ejercicios.dam.intermodulardam.login.ui.Registro

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import ejercicios.dam.intermodulardam.models.Routes
import ejercicios.dam.intermodulardam.utils.*

@Composable
fun Registro(navController: NavHostController, registroViewModel: RegistroViewModel) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        val scaffoldState = rememberScaffoldState()
        Scaffold(
            modifier = Modifier
                .padding(0.dp)
                .fillMaxSize(),
            scaffoldState = scaffoldState,
            topBar = { LoginTopBar() },
            content = {ScreenContent(navController, registroViewModel)}
        )
    }
}

@Composable
fun ScreenContent(navController: NavHostController, registroViewModel: RegistroViewModel) {
    val name: String by registroViewModel.nombre.observeAsState(initial = "")
    val surname: String by registroViewModel.apellidos.observeAsState(initial = "")
    val nick:String by registroViewModel.nick.observeAsState(initial = "")
    val email: String by registroViewModel.email.observeAsState(initial = "")
    val phone:String by registroViewModel.phone.observeAsState(initial = "")
    val password: String by registroViewModel.password.observeAsState(initial = "")
    val isButtonEnabled:Boolean by registroViewModel.isButtonRegisterEnabled.observeAsState(initial = false)

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.LightGray)
        .verticalScroll(rememberScrollState()))
    {
        Column(modifier = Modifier.padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            RegistroTitulo()
            Spacer(modifier = Modifier
                .height(3.dp)
                .fillMaxWidth())
            Nombre(name) {
                registroViewModel.onRegistroChanged(name = it, ape = surname, nick = nick, email = email, phone = phone, password = password)
            }
            Spacer(modifier = Modifier
                .height(3.dp)
                .fillMaxWidth())
            Apellidos(surname) {
                registroViewModel.onRegistroChanged(name = name, ape = it, nick = nick, email = email, phone = phone, password = password)
            }
            Spacer(modifier = Modifier
                .height(3.dp)
                .fillMaxWidth())
            Nick(nick) {
                registroViewModel.onRegistroChanged(name = name, ape = surname, nick = it, email = email, phone = phone, password = password)
            }
            Spacer(modifier = Modifier
                .height(3.dp)
                .fillMaxWidth())
            Email(email) {
                registroViewModel.onRegistroChanged(name = name, ape = surname, nick = nick, email = it, phone = phone, password = password)
            }
            Spacer(modifier = Modifier
                .height(3.dp)
                .fillMaxWidth())
            Phone(phone) {
                registroViewModel.onRegistroChanged(name = name, ape = surname, nick = nick, email = email, phone = it, password = password)
            }
            Spacer(modifier = Modifier
                .height(3.dp)
                .fillMaxWidth())
            Password(password) {
                registroViewModel.onRegistroChanged(name = name, ape = surname, nick = nick, email = email, phone = phone, password = it)
            }
            Spacer(modifier = Modifier
                .height(15.dp)
                .fillMaxWidth())
            BotonRegistro(registerViewModel = registroViewModel, navController = navController, registerEnabled = isButtonEnabled)
            Spacer(modifier = Modifier
                .height(5.dp)
                .fillMaxWidth())
            LinkLogin(navController = navController)
        }
    }
}

@Composable
fun RegistroTitulo() {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(0.dp), horizontalArrangement = Arrangement.Center) {
        Text(text="Regístrate", fontSize = 40.sp, color = Color.Black)
    }
}

@Composable
fun Nombre(nombre:String, onTextChanged: (String) -> Unit) {
    Row(modifier = Modifier.padding(0.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
        OutlinedTextField(
            modifier = Modifier.padding(40.dp, 0.dp),
            value = nombre,
            onValueChange = { onTextChanged(it) },
            label = { Text("Nombre", color = MaterialTheme.colors.MainGreen) },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.MainGreen,
                unfocusedBorderColor = MaterialTheme.colors.DisabledMainGreen
            ),
        )
    }
}

@Composable
fun Apellidos(surname:String, onTextChanged: (String) -> Unit) {
    Row(modifier = Modifier.padding(0.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
        OutlinedTextField(
            modifier = Modifier.padding(40.dp, 0.dp),
            value = surname,
            onValueChange = { onTextChanged(it) },
            label = { Text("Apellidos", color = MaterialTheme.colors.MainGreen) },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.MainGreen,
                unfocusedBorderColor = MaterialTheme.colors.DisabledMainGreen
            ),
        )
    }
}

@Composable
fun Nick(nick:String, onTextChanged: (String) -> Unit) {
    Row(modifier = Modifier.padding(0.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
        OutlinedTextField(
            modifier = Modifier.padding(40.dp, 0.dp),
            value = nick,
            onValueChange = { onTextChanged(it) },
            label = { Text("Nickname", color = MaterialTheme.colors.MainGreen) },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.MainGreen,
                unfocusedBorderColor = MaterialTheme.colors.DisabledMainGreen
            ),
        )
    }
}

@Composable
fun Email(email:String, onTextChanged: (String) -> Unit) {
    Row(modifier = Modifier.padding(0.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
        OutlinedTextField(
            modifier = Modifier.padding(40.dp, 0.dp),
            value = email,
            onValueChange = { onTextChanged(it) },
            label = { Text("Email", color = MaterialTheme.colors.MainGreen) },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.MainGreen,
                unfocusedBorderColor = MaterialTheme.colors.DisabledMainGreen
            ),
        )
    }
}

@Composable
fun Phone(phone:String, onTextChanged: (String) -> Unit) {
    Row(modifier = Modifier.padding(0.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
        OutlinedTextField(
            modifier = Modifier.padding(40.dp, 0.dp),
            value = phone,
            onValueChange = { onTextChanged(it) },
            label = { Text("Teléfono", color = MaterialTheme.colors.MainGreen) },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.MainGreen,
                unfocusedBorderColor = MaterialTheme.colors.DisabledMainGreen
            ),
        )
    }
}

@Composable
fun Password(password:String, onTextChanged: (String) -> Unit) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    Row(modifier = Modifier.padding(0.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
        OutlinedTextField(
            modifier = Modifier.padding(40.dp, 0.dp),
            value = password,
            onValueChange = { onTextChanged(it) },
            label = { Text("Contraseña", color = MaterialTheme.colors.MainGreen) },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.MainGreen,
                unfocusedBorderColor = MaterialTheme.colors.DisabledMainGreen
            ),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff
                val description = if (passwordVisible) "Hide password" else "Show password"

                IconButton(onClick = {passwordVisible = !passwordVisible}){
                    Icon(imageVector  = image, description, tint = MaterialTheme.colors.MainGreen)
                }
            },
        )
    }
}

@Composable
fun BotonRegistro(registerViewModel: RegistroViewModel, navController: NavHostController, registerEnabled:Boolean) {
    Row(modifier = Modifier
        .padding(0.dp)
        .fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Button(
            modifier = Modifier
                .width(220.dp)
                .height(60.dp),
            onClick = {
                registerViewModel.onButtonRegisterPress()
                navController.navigate(Routes.Main.route)
            },
            enabled = registerEnabled,
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.MainGreen, disabledBackgroundColor = MaterialTheme.colors.DisabledMainGreen)
        ) {
            Text(
                modifier = Modifier.align(Alignment.CenterVertically),
                text = "Regístrate",
                textAlign = TextAlign.Center,
                color = Color.White
            )
        }
    }
}

@Composable
fun LinkLogin(navController: NavHostController) {
    Row(modifier = Modifier
        .fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        ClickableText(text = AnnotatedString("¿Ya tienes una cuenta?"), onClick = {navController.navigate("login")}, style = TextStyle(textDecoration = TextDecoration.Underline,fontSize = 16.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Bold))
    }
}
package ejercicios.dam.intermodulardam.login.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
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
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import ejercicios.dam.intermodulardam.R
import ejercicios.dam.intermodulardam.models.Routes
import ejercicios.dam.intermodulardam.utils.DisabledMainGreen
import ejercicios.dam.intermodulardam.utils.LoginTopBar
import ejercicios.dam.intermodulardam.utils.MainGreen

@Composable
fun Login(navController:NavHostController, loginViewModel:LoginViewModel) {
    val isLoading: Boolean by loginViewModel.isLoading.observeAsState(initial = false)
    if(isLoading) {
        WaitingScreen()
    } else {
        LoginScreen(navController, loginViewModel)
    }
}

@Composable
fun LoginScreen(navController: NavHostController, loginViewModel: LoginViewModel) {
    val email: String by loginViewModel.email.observeAsState(initial = "")
    val password: String by loginViewModel.password.observeAsState(initial = "")
    val isLoginEnabled: Boolean by loginViewModel.isButtonLoginEnabled.observeAsState(initial = false)
    var showDialog by rememberSaveable { mutableStateOf(false) }

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
            content ={
                Column(modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray)
                    .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Column(modifier = Modifier
                        .padding(30.dp)
                        .absoluteOffset(0.dp, -(40).dp)) {
                        Logo()
                        Spacer(modifier = Modifier
                            .height(10.dp)
                            .fillMaxWidth())
                        Titulo()
                        Spacer(modifier = Modifier
                            .height(5.dp)
                            .fillMaxWidth())
                        Email(email) {
                            loginViewModel.onLoginChanged(email = it, password = password)
                        }
                        Spacer(modifier = Modifier
                            .height(5.dp)
                            .fillMaxWidth())
                        Password(password) {
                            loginViewModel.onLoginChanged(email = email, password = it)
                        }
                        Spacer(modifier = Modifier
                            .height(20.dp)
                            .fillMaxWidth())
                        BotonLogin(navController,loginViewModel, isLoginEnabled)
                        Spacer(modifier = Modifier
                            .height(30.dp)
                            .fillMaxWidth())
                        LinkRegistro(navController)
                        Spacer(modifier = Modifier
                            .height(5.dp)
                            .fillMaxWidth())
                        Row(modifier = Modifier
                            .fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                            ClickableText(text = AnnotatedString("He olvidado mi contraseña"), onClick = {showDialog = true}, style = TextStyle(textDecoration = TextDecoration.Underline, fontSize = 16.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Bold))
                        }
                    }
                }
                if (showDialog) {
                    RecoveryDialog(true, loginViewModel, onDismiss = { showDialog = false })
                }
            }
        )
    }
}

@Composable
fun Logo() {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(0.dp)
        .absoluteOffset(0.dp, -(30).dp), horizontalArrangement = Arrangement.Center) {
        Image(painter = painterResource(id = R.drawable.logoextended) , contentDescription = "")
    }
}

@Composable
fun Titulo() {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(0.dp),
        horizontalArrangement = Arrangement.Center) {
        Text(text = "Inicia Sesión", fontSize = 30.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun Email(email: String, onTextChanged: (String) -> Unit) {
    Row(modifier = Modifier.padding(20.dp, 0.dp), horizontalArrangement = Arrangement.Center) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = email,
            onValueChange = { onTextChanged(it) },
            label = { Text("Email", color = MaterialTheme.colors.MainGreen) },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.MainGreen,
                unfocusedBorderColor = MaterialTheme.colors.DisabledMainGreen
            ),
            textStyle = TextStyle(fontFamily = FontFamily.Default)
        )
    }
}

@Composable
fun Password(password:String, onTextChanged: (String) -> Unit) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    Row(modifier = Modifier.padding(20.dp, 0.dp), horizontalArrangement = Arrangement.Center) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            onValueChange = {onTextChanged(it)},
            label = { Text(text = "Password", color = MaterialTheme.colors.MainGreen) },
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
            textStyle = TextStyle(fontFamily = FontFamily.Default)
        )
    }
}

@Composable
fun BotonLogin(navController: NavHostController, loginViewModel: LoginViewModel, loginEnabled:Boolean) {
    Row(modifier = Modifier
        .padding(0.dp)
        .fillMaxWidth()
        .height(60.dp), horizontalArrangement = Arrangement.Center) {
        Button(
            modifier = Modifier
                .width(220.dp)
                .height(60.dp),
            onClick = {
                loginViewModel.onButtonLoginPress()
            },
            enabled = loginEnabled,
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.MainGreen, disabledBackgroundColor = MaterialTheme.colors.DisabledMainGreen)
        ) {
            Text(
                modifier = Modifier.align(Alignment.CenterVertically),
                text = "Login",
                textAlign = TextAlign.Center,
                color = Color.White
            )
        }
    }
}

@Composable
fun RecoveryDialog(show:Boolean, loginViewModel: LoginViewModel, onDismiss:() -> Unit) {
    val email:String by loginViewModel.recoveryMail.observeAsState(initial = "")
    val isButtonEnabled:Boolean by loginViewModel.recoveryButton.observeAsState(initial = false)
    if(show) {
        Dialog(onDismissRequest = { onDismiss() }, properties = DialogProperties(dismissOnClickOutside = true, dismissOnBackPress = true)) {
            Column(modifier = Modifier.clip(shape = RoundedCornerShape(5.dp)).background(Color.White)) {
                Row {
                    Text("Introduzca su email y recibirá un correo con la nueva contraseña", textAlign = TextAlign.Center, fontFamily = FontFamily.Default)
                }
                Spacer(modifier = Modifier
                    .height(5.dp)
                    .fillMaxWidth())
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    OutlinedTextField(
                        value = email,
                        onValueChange = {loginViewModel.onRecoveryChanged(email = it)},
                        label = { Text(text = "Email", color = MaterialTheme.colors.MainGreen) },
                        textStyle = TextStyle(fontFamily = FontFamily.Default),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = MaterialTheme.colors.MainGreen,
                            unfocusedBorderColor = MaterialTheme.colors.DisabledMainGreen
                        )
                    )
                }
                Spacer(modifier = Modifier
                    .height(5.dp)
                    .fillMaxWidth())
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    Button(
                        onClick = {
                            onDismiss()
                            loginViewModel.onRecoveryButtonPress()
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.MainGreen, disabledBackgroundColor = MaterialTheme.colors.DisabledMainGreen),
                        enabled = isButtonEnabled
                    ) {
                        Text("Enviar")
                    }
                }
            }
        }
    }
}

@Composable
fun LinkRegistro(navController: NavHostController) {
    Row(modifier = Modifier
        .fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        ClickableText(text = AnnotatedString("¿Aún no te has registrado?"), onClick = {navController.navigate("registro")}, style = TextStyle(textDecoration = TextDecoration.Underline,fontSize = 16.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Bold))
    }
}

@Composable
fun WaitingScreen() {
    /*TODO*/
}

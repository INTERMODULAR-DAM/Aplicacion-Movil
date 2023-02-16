package ejercicios.dam.intermodulardam.login.ui


import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
import ejercicios.dam.intermodulardam.ui.theme.calibri
import ejercicios.dam.intermodulardam.ui.theme.textStyleLogin
import ejercicios.dam.intermodulardam.utils.DisabledBrown
import ejercicios.dam.intermodulardam.utils.DisabledMainGreen
import ejercicios.dam.intermodulardam.utils.MainGreen
import ejercicios.dam.intermodulardam.utils.backgroundGreen

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
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.backgroundGreen)
            .padding(horizontal = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Logo()
        Title()
        Spacer(modifier = Modifier
            .height(15.dp)
            .fillMaxWidth())
        EmailField(email) {
            loginViewModel.onLoginChanged(email = it, password = password)
        }
        Spacer(modifier = Modifier
            .height(15.dp)
            .fillMaxWidth())
        PasswordField(password) {
            loginViewModel.onLoginChanged(email = email, password = it)
        }
        ForgotPassword(
            Modifier
                .clickable { showDialog = true }
                .align(Alignment.End)
                .padding(20.dp))
        LoginButton(navController, loginViewModel, isLoginEnabled)
        Spacer(modifier = Modifier
            .height(30.dp)
            .fillMaxWidth())
        Register(navController)

    }
    if (showDialog) {
        RecoveryDialog(true, loginViewModel, onDismiss = { showDialog = false })
    }
}


@Composable
fun Logo() {
        Image(painter = painterResource(id = R.drawable.white_logo) , contentDescription = "Logo WikiTrail")
}

@Composable
fun Title() {
    Text(text = "Welcome to WikiTrail!", fontSize = 30.sp, fontWeight = FontWeight.ExtraBold, color = Color.White)
    Text(text = "Sign in to continue!", fontSize = 18.sp, color = Color.White, fontWeight = FontWeight.ExtraLight)
}

@Composable
fun EmailField(email: String, onTextChanged: (String) -> Unit) {
    TextField(
        modifier = Modifier.border(
            width = 1.dp,
            brush = Brush.horizontalGradient(listOf(Color.White,Color.White)),
            shape = RoundedCornerShape(12.dp)
        ),
        value = email,
        onValueChange = { onTextChanged(it) },
        label = { PlaceholderForField("User") },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            textColor = Color.White
        ),
        textStyle = textStyleLogin
    )
}

@Composable
fun PasswordField(password:String, onTextChanged: (String) -> Unit) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    TextField(
        modifier = Modifier.border(
            width = 1.dp,
            brush = Brush.horizontalGradient(listOf(Color.White, Color.White)),
            shape = RoundedCornerShape(12.dp)
        ),
        value = password,
        onValueChange = { onTextChanged(it) },
        label = { PlaceholderForField("Password") },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            textColor = Color.White
        ),
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            val image = if (passwordVisible) {
                Icons.Filled.Visibility
            } else Icons.Filled.VisibilityOff
            val description = if (passwordVisible) "Hide password" else "Show password"

            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(imageVector = image, description, tint = Color.White)
            }
        },
        textStyle = textStyleLogin
    )
}

@Composable
fun LoginButton(navController: NavHostController, loginViewModel: LoginViewModel, loginEnabled:Boolean) {
    val context = LocalContext.current
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(60.dp), horizontalArrangement = Arrangement.Center) {
        Button(
            modifier = Modifier
                .width(240.dp)
                .height(60.dp),
            onClick = {loginViewModel.onButtonLoginPress(navController, context)},
            shape = RoundedCornerShape(40.dp),
            border= BorderStroke(1.dp, Color.Black),
            enabled = loginEnabled,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.DisabledBrown,
                disabledBackgroundColor = Color(0xFF6a6a6a),

            )
        ) {
            Text(
                modifier = Modifier.align(Alignment.CenterVertically),
                text = "Login",
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 20.sp,
                fontFamily = calibri
            )
        }
    }
}

@Composable
fun RecoveryDialog(show:Boolean, loginViewModel: LoginViewModel, onDismiss:() -> Unit) {
    val email:String by loginViewModel.recoveryMail.observeAsState(initial = "")
    val context = LocalContext.current
    val isButtonEnabled:Boolean by loginViewModel.recoveryButton.observeAsState(initial = false)
    if(show) {
        Dialog(onDismissRequest = { onDismiss() }, properties = DialogProperties(dismissOnClickOutside = true, dismissOnBackPress = true)) {
            Column(modifier = Modifier
                .clip(shape = RoundedCornerShape(5.dp))
                .background(Color.White)) {
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
                            loginViewModel.onRecoveryButtonPress(email, context)
                            onDismiss()
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
fun Register(navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth(), horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Don't have an account? ",
            fontSize = 18.sp,
            fontFamily = calibri,
            color = Color.White
        )
        Text(
            text = "Sign up",
            style = TextStyle(
                textDecoration = TextDecoration.Underline,
                fontSize = 18.sp,
                fontFamily = calibri,
                fontWeight = FontWeight.Bold,
                color = Color.White
            ),
            modifier = Modifier.clickable {
                navController.navigate("registro")
            }
        )
    }
}

@Composable
fun PlaceholderForField(text : String){
    Text(text = text, color = Color.White, fontFamily = calibri)
}

@Composable
fun ForgotPassword(modifier: Modifier) {
    Text(
        "Forgot your password?",
        style = TextStyle(
            textDecoration = TextDecoration.Underline,
            fontSize = 16.sp,
            fontFamily = calibri,
            fontWeight = FontWeight.Bold,
            color = Color.White
        ),
        modifier = modifier
    )
}


@Composable
fun WaitingScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize().align(Alignment.CenterHorizontally)) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}

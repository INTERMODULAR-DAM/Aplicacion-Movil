package ejercicios.dam.intermodulardam.register.ui

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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import ejercicios.dam.intermodulardam.R
import ejercicios.dam.intermodulardam.ui.theme.calibri
import ejercicios.dam.intermodulardam.ui.theme.textStyleLogin
import ejercicios.dam.intermodulardam.utils.*

@Composable
fun RegisterScreen(navController: NavHostController, registroViewModel: RegisterViewModel) {
    val isLoading: Boolean by registroViewModel.isLoading.observeAsState(initial = false)
    if(isLoading) {
        WaitingScreen()
    } else {
        RegisterContent(navController, registroViewModel)
    }
}

@Composable
fun RegisterContent(navController: NavHostController, registroViewModel: RegisterViewModel) {
    val name: String by registroViewModel.nombre.observeAsState(initial = "")
    val surname: String by registroViewModel.apellidos.observeAsState(initial = "")
    val nick:String by registroViewModel.nick.observeAsState(initial = "")
    val email: String by registroViewModel.email.observeAsState(initial = "")
    val phone:String by registroViewModel.phone.observeAsState(initial = "")
    val password: String by registroViewModel.password.observeAsState(initial = "")
    val isButtonEnabled:Boolean by registroViewModel.isButtonRegisterEnabled.observeAsState(initial = false)

    Column(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colors.backgroundGreen),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center)
    {
        Logo(Modifier.align(Alignment.CenterHorizontally))
        TitleRegister(
            Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 10.dp))
        Name(name) {
            registroViewModel.onRegistroChanged(name = it, ape = surname, nick = nick, email = email, phone = phone, password = password)
        }
        MySpacer()
        LastName(surname) {
            registroViewModel.onRegistroChanged(name = name, ape = it, nick = nick, email = email, phone = phone, password = password)
        }
        MySpacer()
        Nick(nick) {
            registroViewModel.onRegistroChanged(name = name, ape = surname, nick = it, email = email, phone = phone, password = password)
        }
        MySpacer()
        Email(email) {
            registroViewModel.onRegistroChanged(name = name, ape = surname, nick = nick, email = it, phone = phone, password = password)
        }
        MySpacer()
        Phone(phone) {
            registroViewModel.onRegistroChanged(name = name, ape = surname, nick = nick, email = email, phone = it, password = password)
        }
        MySpacer()
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
@Composable
fun Logo(modifier : Modifier){
    Image(painter = painterResource(id = R.drawable.logo_letters), contentDescription = "WikiTrail logo", modifier = modifier)
}

@Composable
fun MySpacer(){
    Spacer(modifier = Modifier
        .height(10.dp)
        .fillMaxWidth())
}


@Composable
fun TitleRegister(modifier: Modifier) {
    Text(
        text = "Sign up!",
        fontSize = 28.sp,
        color = Color.White,
        fontWeight = FontWeight.ExtraLight,
        modifier = modifier
    )
}

@Composable
fun Name(name:String, onTextChanged: (String) -> Unit) {
    Row(modifier = Modifier.padding(0.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
        TextField(
            modifier = Modifier.border(
                width = 1.dp,
                brush = Brush.horizontalGradient(listOf(Color.White,Color.White)),
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
fun Password(password:String, onTextChanged: (String) -> Unit) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    Row(modifier = Modifier.padding(0.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
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
                val image = if (passwordVisible)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff
                val description = if (passwordVisible) "Hide password" else "Show password"

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, description, tint = Color.White)
                }
            },
            textStyle = textStyleLogin
        )
    }
}

@Composable
fun BotonRegistro(registerViewModel: RegisterViewModel, navController: NavHostController, registerEnabled:Boolean) {
    val context = LocalContext.current
    Row(modifier = Modifier
        .padding(0.dp)
        .fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Button(
            modifier = Modifier
                .width(220.dp)
                .height(60.dp),
            onClick = {
               registerViewModel.onButtonRegisterPress(navController, context)
            },
            shape = RoundedCornerShape(40.dp),
            border= BorderStroke(1.dp, Color.Black),
            enabled = registerEnabled,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.DisabledBrown,
                disabledBackgroundColor = Color(0xFF6a6a6a),

                )
        ) {
            Text(
                modifier = Modifier.align(Alignment.CenterVertically),
                text = "Register",
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 20.sp,
                fontFamily = calibri
            )
        }
    }
}

@Composable
fun LinkLogin(navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth(), horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Already have an account? ",
            fontSize = 18.sp,
            fontFamily = calibri,
            color = Color.White
        )
        Text(
            text = "Sign in",
            style = TextStyle(
                textDecoration = TextDecoration.Underline,
                fontSize = 18.sp,
                fontFamily = calibri,
                fontWeight = FontWeight.Bold,
                color = Color.White
            ),
            modifier = Modifier.clickable {
                navController.navigate("login")
            }
        )
    }
}

@Composable
fun PlaceholderForField(text : String){
    Text(text = text, color = Color.White, fontFamily = calibri)
}

@Composable
fun WaitingScreen() {
/*TODO*/
}
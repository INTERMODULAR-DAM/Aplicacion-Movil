package ejercicios.dam.intermodulardam.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ejercicios.dam.intermodulardam.R

val coiny = FontFamily(
    Font(R.font.coiny_regular)
)

val calibri = FontFamily(
    Font(R.font.calibri)
)

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = coiny,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    button = TextStyle(
        fontFamily = coiny,
        fontWeight = FontWeight.W500,
        fontSize = 16.sp
    ),


    )
package ejercicios.dam.intermodulardam.utils

import androidx.compose.material.Colors
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import kotlin.math.abs

val Colors.MainGreen: Color
    get() = Color(0xFF119911)

val Colors.DisabledMainGreen : Color
    get() = Color(0xFF115511)

val Colors.backgroundGreen : Color
    get() = Color(0xFF004200)

val Colors.greenCard : Color
    get() = Color(0xFF6EA760)

val Colors.MainBrown: Color
    get() = Color(0xFF5C330A)

val Colors.DisabledBrown: Color
    get() = Color(0xFF4A2908)

fun Path.standardQuadFromTo(from: Offset, to: Offset) {
    quadraticBezierTo(
        from.x,
        from.y,
        abs(from.x + to.x) / 2f,
        abs(from.y + to.y) / 2f
    )
}
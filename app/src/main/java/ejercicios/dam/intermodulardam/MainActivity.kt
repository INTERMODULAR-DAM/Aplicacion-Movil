package ejercicios.dam.intermodulardam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import ejercicios.dam.intermodulardam.login.ui.LoginViewModel
import ejercicios.dam.intermodulardam.mapa.MapaViewModel
import ejercicios.dam.intermodulardam.navigation.CustomNavigator
import ejercicios.dam.intermodulardam.registro.ui.RegistroViewModel
import ejercicios.dam.intermodulardam.ui.theme.IntermodularDAMTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val loginViewModel: LoginViewModel by viewModels()
    private val mapViewModel: MapaViewModel by viewModels()
    private val registroViewModel: RegistroViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mapViewModel.fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mapViewModel.getCurrentLocation(this)
        setContent {
            IntermodularDAMTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    CustomNavigator(loginViewModel, mapViewModel, registroViewModel)
                }
            }
        }
    }
}






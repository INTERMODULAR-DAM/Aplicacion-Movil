package ejercicios.dam.intermodulardam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import ejercicios.dam.intermodulardam.comments.ui.CommentViewModel
import ejercicios.dam.intermodulardam.login.ui.LoginViewModel
import ejercicios.dam.intermodulardam.main.ui.MainViewModel
import ejercicios.dam.intermodulardam.main.ui.PublicationViewModel
import ejercicios.dam.intermodulardam.map.MapViewModel
import ejercicios.dam.intermodulardam.navigation.CustomNavigator
import ejercicios.dam.intermodulardam.profile.ui.ProfileViewModel
import ejercicios.dam.intermodulardam.register.ui.RegisterViewModel
import ejercicios.dam.intermodulardam.ui.theme.IntermodularDAMTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val loginViewModel: LoginViewModel by viewModels()
    private val mapViewModel: MapViewModel by viewModels()
    private val registerViewModel: RegisterViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels()
    private val commentViewModel: CommentViewModel by viewModels()
    private val publicationViewModel : PublicationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            IntermodularDAMTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    CustomNavigator(
                        loginViewModel,
                        mapViewModel,
                        registerViewModel,
                        mainViewModel,
                        profileViewModel,
                        commentViewModel,
                        publicationViewModel
                    )
                }
            }
        }
    }
}



package ejercicios.dam.intermodulardam.login.ui

import android.content.Context
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import ejercicios.dam.intermodulardam.login.domain.usecase.HasTokenUseCase
import ejercicios.dam.intermodulardam.login.domain.usecase.HasUserLoggedUseCase
import ejercicios.dam.intermodulardam.login.domain.usecase.LoginUseCase
import ejercicios.dam.intermodulardam.login.domain.usecase.RecoverPasswordUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject
    constructor(
        private val loginUseCase: LoginUseCase,
        private val hasToken:HasTokenUseCase,
        private val hasUserLogged:HasUserLoggedUseCase,
        private val recoverPasswordUseCase: RecoverPasswordUseCase
        ) : ViewModel() {

    init {
        viewModelScope.launch {
            if(hasToken() && hasUserLogged()) {
                Log.i("A", "tenemos token y usuario")
            }
        }
    }


    private val _email = MutableLiveData<String>()
    val email : LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password : LiveData<String> = _password

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    private val _isButtonLoginEnabled = MutableLiveData<Boolean>()
    val isButtonLoginEnabled : LiveData<Boolean> = _isButtonLoginEnabled

    private val _loginOk = MutableLiveData<String>()
    val loginOk: LiveData<String> = _loginOk

    fun onLoginChanged(email:String, password:String) {
        _email.value = email
        _password.value = password
        _isButtonLoginEnabled.value = enableLogin(email, password)
    }

    private fun enableLogin(email: String, password: String): Boolean {
        return email.length > 1 && password.length > 6
    }

    fun onButtonLoginPress(navController: NavHostController, context: Context) {
        viewModelScope.launch {
            _isLoading.value = true
            _loginOk.value = loginUseCase(email.value!!, password.value!!)
            if(_loginOk.value!!.isNotEmpty()) {
                navController.navigate("main")
            } else {
                Toast.makeText(context, "Wrong user or password, please try again", Toast.LENGTH_SHORT).show()
            }
            _isLoading.value = false
        }
    }


    private val _recoveryMail = MutableLiveData<String>()
    val recoveryMail: LiveData<String> = _recoveryMail

    private val _recoveryButton = MutableLiveData<Boolean>()
    val recoveryButton: LiveData<Boolean> = _recoveryButton


    fun onRecoveryChanged(email:String) {
        _recoveryMail.value = email
        _recoveryButton.value = enableRecovery(email)
    }

    private fun enableRecovery(email:String):Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun onRecoveryButtonPress(email:String, context: Context) {
        viewModelScope.launch {
            val emailOk = recoverPasswordUseCase.invoke(email)
            if(emailOk) {
                Toast.makeText(context, "The email has been sent successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "No account is linked to this email, please enter another email", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
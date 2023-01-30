package ejercicios.dam.intermodulardam.login.ui

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ejercicios.dam.intermodulardam.login.data.dto.UserDTO
import ejercicios.dam.intermodulardam.login.domain.entity.UserModel
import ejercicios.dam.intermodulardam.login.domain.usecase.LoginUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase) : ViewModel() {

    private val _email = MutableLiveData<String>()
    val email : LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password : LiveData<String> = _password

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    private val _isButtonLoginEnabled = MutableLiveData<Boolean>()
    val isButtonLoginEnabled : LiveData<Boolean> = _isButtonLoginEnabled

    fun onLoginChanged(email:String, password:String) {
        _email.value = email
        _password.value = password
        _isButtonLoginEnabled.value = enableLogin(email, password)
    }

    private fun enableLogin(email: String, password: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches() &&
                password.length > 6
    }

    fun onButtonLoginPress() {
        viewModelScope.launch {
            _isLoading.value = true
            val result = loginUseCase(email.value!!, password.value!!)
            if(result) {
                val validuser = loginUseCase(UserModel(email.value!!, password.value!!))
            }
            _isLoading.value = false
        }
    }

    /*RECOVERY PASSWORD DIALOG TODO */

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

    fun onRecoveryButtonPress() {
        viewModelScope.launch {

        }
    }
}
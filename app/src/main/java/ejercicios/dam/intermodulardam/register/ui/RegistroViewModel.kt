package ejercicios.dam.intermodulardam.register.ui

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
import ejercicios.dam.intermodulardam.register.domain.entity.UserRegistroModel
import ejercicios.dam.intermodulardam.register.domain.usecase.RegistroUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistroViewModel @Inject constructor(private val registroUseCase: RegistroUseCase) :ViewModel() {
    private val _nombre = MutableLiveData<String>()
    val nombre : LiveData<String> = _nombre

    private val _apellidos = MutableLiveData<String>()
    val apellidos : LiveData<String> = _apellidos

    private val _nick = MutableLiveData<String>()
    val nick : LiveData<String> = _nick

    private val _email = MutableLiveData<String>()
    val email : LiveData<String> = _email

    private val _phone = MutableLiveData<String>()
    val phone : LiveData<String> = _phone

    private val _password = MutableLiveData<String>()
    val password : LiveData<String> = _password

    private val _isButtonRegisterEnabled = MutableLiveData<Boolean>()
    val isButtonRegisterEnabled : LiveData<Boolean> = _isButtonRegisterEnabled

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    fun onRegistroChanged(name:String, ape:String, nick:String, email:String, phone:String, password:String) {
        _nombre.value = name
        _apellidos.value = ape
        _nick.value = nick
        _email.value = email
        _phone.value = phone
        _password.value = password
        _isButtonRegisterEnabled.value = enableRegister(name, ape, nick, email, phone, password)
    }

    private fun enableRegister(name:String, ape:String, nick:String, email:String, phone:String, password: String):Boolean {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches() &&
                    Patterns.PHONE.matcher(phone).matches() &&
                    password.length > 6
                    && name.isNotBlank()
                    && ape.isNotBlank()
                    && nick.isNotBlank()
    }

    fun onButtonRegisterPress(navController:NavHostController, context: Context) {
        viewModelScope.launch {
            _isLoading.value = true
            val user = UserRegistroModel(
                email.value!!,
                nombre.value!!,
                apellidos.value!!,
                nick.value!!,
                password.value!!,
                phone.value!!
            )
            val result = registroUseCase(user)
            Log.i("RESULT", result.toString())
            if (result) {
                navController.navigate("main")
            } else {
                Toast.makeText(context, "Ha habido un error con el registro", Toast.LENGTH_SHORT)
                    .show()
            }
            _isLoading.value = false
        }
    }
}
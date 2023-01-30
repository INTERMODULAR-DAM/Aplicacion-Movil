package ejercicios.dam.intermodulardam.login.ui.Registro

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ejercicios.dam.intermodulardam.login.domain.entity.UserModel
import ejercicios.dam.intermodulardam.registro.domain.entity.UserRegistroModel
import ejercicios.dam.intermodulardam.registro.domain.usecase.RegistroUseCase
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

    fun onButtonRegisterPress() {
        viewModelScope.launch {
            _isLoading.value = true
            val user:UserRegistroModel = UserRegistroModel(
                email.value!!,
                nombre.value!!,
                apellidos.value!!,
                nick.value!!,
                password.value!!,
                phone.value!!
            )
            val result = registroUseCase(user)
            _isLoading.value = false
        }
    }
}
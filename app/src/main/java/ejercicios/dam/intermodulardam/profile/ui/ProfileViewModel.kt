package ejercicios.dam.intermodulardam.profile.ui

import android.content.Context
import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import ejercicios.dam.intermodulardam.Routes
import ejercicios.dam.intermodulardam.main.data.MainRepository
import ejercicios.dam.intermodulardam.main.domain.entity.Publication
import ejercicios.dam.intermodulardam.main.domain.entity.User
import ejercicios.dam.intermodulardam.profile.data.ProfileRepository
import ejercicios.dam.intermodulardam.profile.domain.EditProfileUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository,
    private val mainRepository: MainRepository,
    private val editProfileUseCase: EditProfileUseCase
) : ViewModel() {

    private var _user = MutableLiveData<User>()
    var user: LiveData<User> = _user

    private val _posts = MutableLiveData<List<Publication>>()
    val posts: LiveData<List<Publication>> = _posts

    fun onInit() {
        _isLoading.value = true
        viewModelScope.launch {
            _user.value = mainRepository.getUser()
            _posts.value = repository.getOwnPosts()
            _isLoading.value = false
        }
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading = _isLoading

    private val _isButtonEnabled = MutableLiveData<Boolean>()
    val isButtonEnabled: LiveData<Boolean> = _isButtonEnabled

    private val _nombre = MutableLiveData<String>()
    val nombre: LiveData<String> = _nombre

    private val _apellidos = MutableLiveData<String>()
    val apellidos: LiveData<String> = _apellidos

    private val _nick = MutableLiveData<String>()
    val nick: LiveData<String> = _nick

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _phone = MutableLiveData<String>()
    val phone: LiveData<String> = _phone

    fun onEditProfileChanged(
        email: String,
        name: String,
        ape: String,
        nick: String,
        phone: String
    ) {
        _nombre.value = name
        _apellidos.value = ape
        _nick.value = nick
        _email.value = email
        _phone.value = phone
        _isButtonEnabled.value = enableEditProfile(name, ape, nick, email, phone)
    }

    private fun enableEditProfile(
        name: String,
        ape: String,
        nick: String,
        email: String,
        phone: String
    ): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()||
                Patterns.PHONE.matcher(phone).matches()||
                name.isNotBlank() ||
                ape.isNotBlank() ||
                nick.isNotBlank()
    }

    fun onEditProfile(context: Context, navController: NavHostController) {
        viewModelScope.launch {
            val result = editProfileUseCase(_user.value!!)
            if (result) {
                Toast.makeText(
                    context,
                    "User updated",
                    Toast.LENGTH_SHORT).show()
                navController.navigate(Routes.Perfil.createRoute(_user.value!!.id))
            } else {
                Toast.makeText(
                    context,
                    "An error has ocurred updating the user",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

}
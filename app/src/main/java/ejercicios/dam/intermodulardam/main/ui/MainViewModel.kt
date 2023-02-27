package ejercicios.dam.intermodulardam.main.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ejercicios.dam.intermodulardam.login.data.network.dto.UserDTO
import ejercicios.dam.intermodulardam.main.domain.*
import ejercicios.dam.intermodulardam.main.domain.entity.Publication
import ejercicios.dam.intermodulardam.main.domain.entity.User
import ejercicios.dam.intermodulardam.profile.data.ProfileRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val GetUser : GetUserUseCase,
    private val GetAllPosts : GetAllPosts,
    private val GetAllPublicPosts : GetAllPublicPosts,
    private val GetUserCreator : GetUserByIdUseCase,
    private val repository: ProfileRepository,
) : ViewModel() {

    private val _currentUser = MutableLiveData<User>()
    val currentUser = _currentUser

    private val _usersCreators = MutableLiveData<MutableList<User>>()
    val usersCreators = _usersCreators

    private val _routes = MutableLiveData<List<Publication>>()
    val routes = _routes

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading = _isLoading

    init {
        _usersCreators.value = mutableListOf()
    }

   fun onInit(){
       _isLoading.value = true
       _usersCreators.value = mutableListOf()
        viewModelScope.launch{
            _currentUser.value = GetUser()
            if(_currentUser.value!!.admin){
                _routes.value = GetAllPosts()
            }else {
                _routes.value = GetAllPublicPosts().plus(repository.getOwnPosts())?.distinct()
            }
            for (i in 0 until _routes.value!!.size) {
                val user = GetUserCreator(_routes.value!![i].user)
                _usersCreators.value = (_usersCreators.value?.plus(user)?: listOf(user)) as MutableList<User>
            }
            _isLoading.value = false
        }
   }
}
package ejercicios.dam.intermodulardam.main.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ejercicios.dam.intermodulardam.login.data.network.dto.UserDTO
import ejercicios.dam.intermodulardam.main.data.MainRepository
import ejercicios.dam.intermodulardam.main.domain.*
import ejercicios.dam.intermodulardam.main.domain.entity.Publication
import ejercicios.dam.intermodulardam.main.domain.entity.User
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val CreateRouteUseCase : CreateRouteUseCase,
    private val GetUser : GetUser,
    private val GetAllPosts : GetAllPosts,
    private val GetAllPublicPosts : GetAllPublicPosts,
    private val GetUserCreator : GetUserCreator
) : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user:LiveData<User> = _user

    private val _routes = MutableLiveData<List<Publication>>()
    val routes:LiveData<List<Publication>> = _routes

    var creatorUser  = UserDTO("", "", "", "", "", "", false, "", "", listOf())

    fun onInit() {
        viewModelScope.launch {
            _user.value = GetUser()
            if(_user.value!!.admin) {
                _routes.value = GetAllPosts()
            } else {
                _routes.value = GetAllPublicPosts()
            }
        }
    }

    suspend fun getPostCreator(id : String){
        creatorUser = GetUserCreator(id)
    }

}
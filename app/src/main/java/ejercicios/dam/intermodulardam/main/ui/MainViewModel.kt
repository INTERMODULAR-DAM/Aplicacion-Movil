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
    private val GetUserCreator : GetUserByIdUseCase
) : ViewModel() {

    private val _currentUser = MutableLiveData<User>()
    val currentUser:LiveData<User> = _currentUser

    private val _usersCreators = MutableLiveData<MutableList<User>>()
    val usersCreators : LiveData<MutableList<User>> = _usersCreators

    private val _routes = MutableLiveData<List<Publication>>()
    val routes:LiveData<List<Publication>> = _routes

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading = _isLoading

   init{
       isLoading.postValue(true)
        _usersCreators.value = mutableListOf()
        viewModelScope.launch(Dispatchers.IO){
            getCurrentUser()
            getAllPost()
            getAllCreators()
            isLoading.postValue(false)
        }
   }

    private suspend fun getCurrentUser(){
        withContext(Dispatchers.Main){
            _currentUser.postValue(GetUser())
        }
    }


    suspend fun getAllPost() {
        withContext(Dispatchers.Main){
            if(_currentUser.value!!.admin){
                _routes.postValue(GetAllPosts())
            }else {
                _routes.postValue(GetAllPublicPosts())
            }
        }
    }


    suspend fun getAllCreators(){
        withContext(Dispatchers.Main) {
            for (i in 0 until _routes.value!!.size) {
                _usersCreators.value!!.add(GetUserCreator(_routes.value!![i].user))
            }
        }
    }
}
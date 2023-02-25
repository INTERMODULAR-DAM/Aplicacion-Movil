package ejercicios.dam.intermodulardam.main.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ejercicios.dam.intermodulardam.comments.domain.entity.Comment
import ejercicios.dam.intermodulardam.comments.domain.usecase.GetAllCommentsUseCase
import ejercicios.dam.intermodulardam.main.domain.GetPostById
import ejercicios.dam.intermodulardam.main.domain.GetUserByIdUseCase
import ejercicios.dam.intermodulardam.main.domain.GetUserUseCase
import ejercicios.dam.intermodulardam.main.domain.entity.Publication
import ejercicios.dam.intermodulardam.main.domain.entity.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import javax.inject.Inject


@HiltViewModel
class PublicationViewModel @Inject constructor(
    private val GetPostById : GetPostById,
    private val GetUserById : GetUserByIdUseCase,
    private val GetUserUseCase : GetUserUseCase,
    private val GetAllComments : GetAllCommentsUseCase
)  : ViewModel() {

    private val _currentUser = MutableLiveData<User>()
    val currentUser = _currentUser

    private val _userCreator = MutableLiveData<User>()
    val user = _userCreator

    private val _route = MutableLiveData<Publication>()
    val route:LiveData<Publication> = _route

    private val _comments = MutableLiveData<MutableList<Comment>>()
    val comments = _comments

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading = _isLoading

    init {
        _isLoading.postValue(true)
        _comments.postValue(mutableListOf())
    }

    fun onInit(id : String) {
        viewModelScope.launch(Dispatchers.IO) {
            getRoute(id)
            getCurrentUser()
            getUserCreator()
            getComments()
            _isLoading.postValue(false)
        }
    }

    private suspend fun getUserCreator(){
        withContext(Dispatchers.Main){
            _userCreator.postValue(GetUserById(_route.value!!.user))
        }
    }

    private suspend fun getCurrentUser(){
        withContext(Dispatchers.Main){
            _currentUser.postValue(GetUserUseCase())
        }
    }

    private suspend fun getRoute(id : String){
        withContext(Dispatchers.Main){
            _route.postValue(GetPostById(id))
        }
    }

    private suspend fun getComments(){
        withContext(Dispatchers.Main){
            _comments.postValue(GetAllComments(_route.value!!.id))
        }
    }

}
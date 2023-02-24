package ejercicios.dam.intermodulardam.main.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ejercicios.dam.intermodulardam.comments.domain.entity.Comment
import ejercicios.dam.intermodulardam.main.domain.GetPostById
import ejercicios.dam.intermodulardam.main.domain.GetUserByIdUseCase
import ejercicios.dam.intermodulardam.main.domain.GetUserUseCase
import ejercicios.dam.intermodulardam.main.domain.entity.Publication
import ejercicios.dam.intermodulardam.main.domain.entity.User
import kotlinx.coroutines.launch


@HiltViewModel
class PublicationViewModel (
    private val GetPostById : GetPostById,
    private val GetUserById : GetUserByIdUseCase
)  : ViewModel() {
    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val _route = MutableLiveData<Publication>()
    val route: LiveData<Publication> = _route

    private val _comments = MutableLiveData<List<Comment>>()
    val comments: LiveData<List<Comment>> = _comments

    fun onInit(postId:String, userId : String) {
        viewModelScope.launch {
            _user.value = GetUserById(userId)
            _route.value = GetPostById(postId)
            if(_route.value!!.id.isNotEmpty()){
                _comments.value = comentariosUseCase(_route.value!!)
            }
        }
    }

}
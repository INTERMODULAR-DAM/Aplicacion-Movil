package ejercicios.dam.intermodulardam.comments.ui

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ejercicios.dam.intermodulardam.comments.domain.entity.Comentarios
import ejercicios.dam.intermodulardam.comments.domain.usecase.ComentariosUseCase
import ejercicios.dam.intermodulardam.comments.domain.usecase.CreateCommentUseCase
import ejercicios.dam.intermodulardam.main.domain.entity.Publication
import ejercicios.dam.intermodulardam.main.domain.entity.User
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ComentariosViewModel @Inject constructor(
    private val comentariosUseCase: ComentariosUseCase,
    private val createCommentUseCase: CreateCommentUseCase
) : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val _route = MutableLiveData<Publication>()
    val route: LiveData<Publication> = _route

    private val _comments = MutableLiveData<List<Comentarios>>()
    val comments: LiveData<List<Comentarios>> = _comments

    fun Oninit() {
        viewModelScope.launch {
            _comments.value = comentariosUseCase.invoke(_route.value!!)
        }
    }

    private val _message = MutableLiveData<String>()
    val message:LiveData<String> = _message

    private val _isButtonEnabled = MutableLiveData<Boolean>()
    val isButtonEnabled:LiveData<Boolean> = _isButtonEnabled

    fun onCommentChanged(message:String) {
        _message.value = message
        _isButtonEnabled.value = validateMessage(message)
    }

    private fun validateMessage(message:String):Boolean {
        return message.isNotEmpty()
    }

    fun onCreateComment(userID:String, postID:String, context:Context) {
        viewModelScope.launch {
            val comment = Comentarios(_message.value!!, userID, postID)
            val createdOk = createCommentUseCase.invoke(comment)
            if(!createdOk) {
                Toast.makeText(context, "Ha habido un problema al crear el comentario", Toast.LENGTH_LONG).show()
            }
        }
    }
}
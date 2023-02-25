package ejercicios.dam.intermodulardam.comments.ui

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ejercicios.dam.intermodulardam.comments.data.dto.CommentDTO
import ejercicios.dam.intermodulardam.comments.domain.entity.Comment
import ejercicios.dam.intermodulardam.comments.domain.usecase.GetAllCommentsUseCase
import ejercicios.dam.intermodulardam.comments.domain.usecase.CreateCommentUseCase
import ejercicios.dam.intermodulardam.comments.domain.usecase.DeleteCommentUseCase
import ejercicios.dam.intermodulardam.main.domain.GetPostById
import ejercicios.dam.intermodulardam.main.domain.GetUserUseCase
import ejercicios.dam.intermodulardam.main.domain.entity.Publication
import ejercicios.dam.intermodulardam.main.domain.entity.User
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentViewModel @Inject constructor(
    private val GetAllCommentsUseCase: GetAllCommentsUseCase,
    private val createCommentUseCase: CreateCommentUseCase,
    private val deleteCommentUseCase: DeleteCommentUseCase,
    private val GetPostByIdUseCase : GetPostById,
    private val GetUserUseCase : GetUserUseCase
) : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val _route = MutableLiveData<Publication>()
    val route: LiveData<Publication> = _route

    private val _comments = MutableLiveData<List<Comment>>()
    val comments: LiveData<List<Comment>> = _comments

    fun onInit(id:String) {
        viewModelScope.launch {
            _user.value = GetUserUseCase()
            _route.value = GetPostByIdUseCase(id)
            if(_route.value!!.id.isNotEmpty()){
                _comments.value = GetAllCommentsUseCase(_route.value!!.id)
            }
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
            val comment = CommentDTO(_message.value!!, userID, postID)
            val createdOk = createCommentUseCase(comment)
            if(!createdOk) {
                Toast.makeText(context, "An error has ocurred creating the comment", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun onDeleteComment(context:Context, id:String) {
        viewModelScope.launch {
            val result = deleteCommentUseCase.invoke(id)
            if(result) {
                Toast.makeText(context, "Comentario Borrado con Éxito", Toast.LENGTH_LONG).show()
                _comments.value = GetAllCommentsUseCase(_route.value!!.id)
            } else {
                Toast.makeText(context, "Ha habido un problema al borrar el comentario", Toast.LENGTH_LONG).show()
            }
        }
    }
}
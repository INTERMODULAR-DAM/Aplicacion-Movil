package ejercicios.dam.intermodulardam.comments.ui

import android.content.Context
import android.util.Log
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
import ejercicios.dam.intermodulardam.main.domain.GetUserByIdUseCase
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
    private val GetUserUseCase : GetUserUseCase,
    private val GetUserByIdUseCase : GetUserByIdUseCase
) : ViewModel() {

    private val _canSeeComments = MutableLiveData<Boolean>()
    val canSeeComments = _canSeeComments

    private val _buttonValue = MutableLiveData<String>()
    val buttonValue = _buttonValue

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val _route = MutableLiveData<Publication>()
    val route: LiveData<Publication> = _route

    private val _comments = MutableLiveData<List<Comment>>()
    val comments: LiveData<List<Comment>> = _comments

    private val _commentCreators = MutableLiveData<MutableList<User>>()
    val commentCreators = _commentCreators

    private val _message = MutableLiveData<String>()
    val message:LiveData<String> = _message

    private val _isButtonEnabled = MutableLiveData<Boolean>()
    val isButtonEnabled:LiveData<Boolean> = _isButtonEnabled

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading = _isLoading

    fun onInit(id:String) {
        _canSeeComments.value = false
        _buttonValue.value = "Comments"
        viewModelScope.launch {
            _user.value = GetUserUseCase()
            _route.value = GetPostByIdUseCase(id)
            if(_route.value!!.id.isNotEmpty()){
                _comments.value = GetAllCommentsUseCase(_route.value!!.id)
                for(i in 0 until comments.value!!.size){
                    val user = GetUserByIdUseCase(_comments.value!![i].user)
                    _commentCreators.value = (_commentCreators.value?.plus(user)?: listOf(user)) as MutableList<User>
                }
            }
        }
    }

    private suspend fun GetAllCreators() {
        for(i in 0 until comments.value!!.size){
            val user = GetUserByIdUseCase(_comments.value!![i].user)
            _commentCreators.value = (_commentCreators.value?.plus(user)?: listOf(user)) as MutableList<User>
        }
    }


    fun onCommentChanged(message:String) {
        _message.value = message
        _isButtonEnabled.value = validateMessage(message)
    }

    private fun validateMessage(message:String):Boolean {
        return message.isNotEmpty()
    }

    fun onCreateComment(context:Context) {
        viewModelScope.launch {
            val comment = CommentDTO(_message.value!!, _user.value!!.id, _route.value!!.id)
            val createdOk = createCommentUseCase(comment)
            if(!createdOk) {
                Toast.makeText(context, "An error has ocurred creating the comment", Toast.LENGTH_LONG).show()
            }else{
                loadComments()
                _message.value = ""
            }
        }
    }

    fun onDeleteComment(context:Context, id:String) {
        viewModelScope.launch {
            val result = deleteCommentUseCase.invoke(id)
            if(result) {
                Toast.makeText(context, "The comment has been deleted correctly", Toast.LENGTH_LONG).show()
                loadComments()
            } else {
                Toast.makeText(context, "An error has ocurred deleting the comment", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun seeCommentsView() {
        _canSeeComments.value = !_canSeeComments.value!!
        if(_canSeeComments.value!!)
            _buttonValue.value = "View route"
        else
            _buttonValue.value = "View comments"
        loadComments()
    }

    fun loadComments(){
        viewModelScope.launch{
            _isLoading.value = true
            _comments.value = GetAllCommentsUseCase(_route.value!!.id)
            _commentCreators.value = mutableListOf()
            for(i in 0 until comments.value!!.size){
                val user = GetUserByIdUseCase(_comments.value!![i].user)
                Log.d("YEPA", user.nick)
                _commentCreators.value = (_commentCreators.value?.plus(user)?: listOf(user)) as MutableList<User>
            }
            _isLoading.value = false
        }
    }
}
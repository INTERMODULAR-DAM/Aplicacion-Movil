package ejercicios.dam.intermodulardam.comments.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ejercicios.dam.intermodulardam.comments.domain.entity.Comentarios
import ejercicios.dam.intermodulardam.comments.domain.usecase.ComentariosUseCase
import ejercicios.dam.intermodulardam.main.domain.entity.Publication
import ejercicios.dam.intermodulardam.main.domain.entity.User
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ComentariosViewModel @Inject constructor(private val comentariosUseCase: ComentariosUseCase):ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val _route = MutableLiveData<Publication>()
    val route: LiveData<Publication> = _route

    private val _comments = MutableLiveData<List<Comentarios>>()
    val comments:LiveData<List<Comentarios>> = _comments

    init {
        viewModelScope.launch {
            _comments.value = comentariosUseCase.invoke(_route.value!!)
        }
    }
}
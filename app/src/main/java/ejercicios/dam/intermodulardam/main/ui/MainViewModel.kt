package ejercicios.dam.intermodulardam.main.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ejercicios.dam.intermodulardam.main.data.MainRepository
import ejercicios.dam.intermodulardam.main.domain.entity.Publication
import ejercicios.dam.intermodulardam.main.domain.entity.User
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user:LiveData<User> = _user

    private val _routes = MutableLiveData<List<Publication>>()
    val routes:LiveData<List<Publication>> = _routes

    fun onInit() {
        viewModelScope.launch {
            _user.value = repository.getUser()
            if(_user.value!!.admin) {
                _routes.value = repository.getAllPosts()
            } else {
                _routes.value = repository.getAllPublicPosts()
            }
        }
    }

}
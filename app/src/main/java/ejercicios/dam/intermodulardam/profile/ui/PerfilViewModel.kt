package ejercicios.dam.intermodulardam.profile.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ejercicios.dam.intermodulardam.main.data.MainRepository
import ejercicios.dam.intermodulardam.main.domain.entity.Publication
import ejercicios.dam.intermodulardam.main.domain.entity.User
import ejercicios.dam.intermodulardam.profile.data.ProfileRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PerfilViewModel @Inject constructor(
    private val repository: ProfileRepository,
    private val mainRepository: MainRepository) : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user:LiveData<User> = _user

    private val _posts = MutableLiveData<List<Publication>>()
    val posts: LiveData<List<Publication>> = _posts

    fun onInit(userID:String) {
        viewModelScope.launch {
            _user.value = mainRepository.getUser(userID)
            _posts.value = repository.getOwnPosts()
        }
    }
}
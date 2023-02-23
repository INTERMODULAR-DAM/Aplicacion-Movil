package ejercicios.dam.intermodulardam.map

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import ejercicios.dam.intermodulardam.main.domain.CreateRouteUseCase
import ejercicios.dam.intermodulardam.main.data.MainRepository
import ejercicios.dam.intermodulardam.main.domain.entity.CreatePublication
import ejercicios.dam.intermodulardam.main.domain.entity.Publication
import ejercicios.dam.intermodulardam.main.domain.entity.User
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MapaViewModel @Inject constructor(private val createRouteUseCase: CreateRouteUseCase, private val repository: MainRepository) : ViewModel() {

     lateinit var fusedLocationClient: FusedLocationProviderClient

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

     private val _name = MutableLiveData<String>()
     val name:LiveData<String> = _name

     private val _category = MutableLiveData<String>()
     val category:LiveData<String> = _category

     private val _distance = MutableLiveData<String>()
     val distance:LiveData<String> = _distance

     private val _difficulty = MutableLiveData<String>()
     val difficulty:LiveData<String> = _difficulty

     private val _track = MutableLiveData<MutableList<LatLng>>()
     val track:LiveData<MutableList<LatLng>> = _track

     private val _duration = MutableLiveData<String>()
     val duration:LiveData<String> = _duration

     private val _description = MutableLiveData<String>()
     val description:LiveData<String> = _description

     private val _isPrivate = MutableLiveData<Boolean>()
     val isPrivate:LiveData<Boolean> = _isPrivate

     private val _isButtonEnabled = MutableLiveData<Boolean>()
     val isButtonEnabled:LiveData<Boolean> = _isButtonEnabled

     fun onRouteChanged(
          name:String,
          category:String,
          distance:String,
          difficulty:String,
          track:MutableList<LatLng>,
          duration: String,
          description: String,
          isPrivate:Boolean
     ) {
          _name.value = name
          _category.value = category
          _distance.value = distance
          _difficulty.value = difficulty
          _track.value = track
          _duration.value = duration
          _description.value = description
          _isPrivate.value = isPrivate
          _isButtonEnabled.value =
               enableCreateButton(name, category, distance, difficulty, track, duration, description, isPrivate)
     }

     private fun enableCreateButton(
          name:String,
          category: String,
          distance: String,
          difficulty: String,
          track: MutableList<LatLng>,
          duration: String,
          description: String,
          isPrivate: Boolean
     ):Boolean {
          return name.isNotEmpty() &&
                  category.isNotEmpty() &&
                  distance.isNotEmpty() &&
                  difficulty.isNotEmpty() &&
                  track.isNotEmpty() &&
                  duration.isNotEmpty() &&
                  description.isNotEmpty() &&
                  (isPrivate.or(!isPrivate))
     }

     fun onCreateButtonClick(id:String, context: Context, navController:NavHostController) {
          viewModelScope.launch {
               val publication = CreatePublication(
                    name.value!!,
                    category.value!!,
                    distance.value!!,
                    difficulty.value!!,
                    track.value!!,
                    duration.value!!,
                    description.value!!,
                    isPrivate.value!!,
                    id
               )
               val response = createRouteUseCase(publication)
               if(response) {
                    navController.navigate("main")
               } else {
                    Toast.makeText(context, "An error has ocurred creating the route", Toast.LENGTH_SHORT).show()
               }
          }
     }
}
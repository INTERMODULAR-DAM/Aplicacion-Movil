package ejercicios.dam.intermodulardam.map

import android.content.Context
import android.util.Log
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
import ejercicios.dam.intermodulardam.main.domain.entity.LatitudeLongitude
import ejercicios.dam.intermodulardam.main.domain.entity.Publication
import ejercicios.dam.intermodulardam.main.domain.entity.User
import kotlinx.coroutines.launch
import java.lang.Math.*
import java.text.DecimalFormat
import javax.inject.Inject


@HiltViewModel
class MapViewModel @Inject constructor(private val createRouteUseCase: CreateRouteUseCase, private val repository: MainRepository) : ViewModel() {

     lateinit var fusedLocationClient: FusedLocationProviderClient

     private val _user = MutableLiveData<User>()
     val user:LiveData<User> = _user

     private val _routes = MutableLiveData<List<Publication>>()
     val routes:LiveData<List<Publication>> = _routes

     fun onInit() {
          viewModelScope.launch {
               _user.value = repository.getUser()
               Log.d("USER", _user.value!!.toString())
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

     private var distanceKM : Double = 0.0

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
          difficulty:String,
          track:MutableList<LatLng>,
          duration: String,
          description: String,
          isPrivate:Boolean
     ) {
          _name.value = name
          _category.value = category
          _difficulty.value = difficulty
          _track.value = track
          _duration.value = duration
          _description.value = description
          _isPrivate.value = isPrivate
          _isButtonEnabled.value =
               enableCreateButton(name, category, difficulty, track, duration, description, isPrivate)
     }

     private fun enableCreateButton(
          name:String,
          category: String,
          difficulty: String,
          track: MutableList<LatLng>,
          duration: String,
          description: String,
          isPrivate: Boolean
     ):Boolean {
          return name.isNotEmpty() &&
                  category.isNotEmpty() &&
                  difficulty.isNotEmpty() &&
                  track.size > 1 &&
                  duration.isNotEmpty() &&
                  description.isNotEmpty() &&
                  (isPrivate.or(!isPrivate))
     }

     fun onCreateButtonClick(id:String, context: Context, navController:NavHostController) {
          viewModelScope.launch {
               val latlngList: MutableList<LatitudeLongitude> = mutableListOf()
               _track.value!!.forEach { LatLng ->
                    latlngList.add(LatitudeLongitude(LatLng.latitude, LatLng.longitude))
               }

               for(i in 0 until _track.value!!.size){
                   if(i + 1 >= _track.value!!.size){
                        break
                   }
                    getDistanceKm(_track.value!![i], _track.value!![i+1])
               }

               val publication = CreatePublication(
                    name.value!!,
                    category.value!!,
                    distance.value!!,
                    difficulty.value!!,
                    latlngList,
                    duration.value!!,
                    description.value!!,
                    isPrivate.value!!,
                    _user.value!!.id
               )
               val response = createRouteUseCase(publication)
               if (response) {
                    cleanFields()
                    navController.navigate("main")
               } else {
                    Toast.makeText(
                         context,
                         "An error has ocurred creating the route",
                         Toast.LENGTH_SHORT
                    ).show()
               }
          }
     }

     private fun cleanFields() {
          _name.value = ""
          _track.value = mutableListOf()
          _distance.value = ""
          _category.value = ""
          _description.value = ""
          _difficulty.value = ""
          _duration.value = ""
     }

     fun getDistanceKm(coordenada1: LatLng, coordenada2: LatLng){

          val decimalFormat = DecimalFormat("#.00")
          val earthRadius = 6372.8
          val distanceLatitude = toRadians(coordenada1.latitude - coordenada2.latitude)
          val distanceLength = toRadians(coordenada1.longitude - coordenada2.longitude)
          val latitude1 = toRadians(coordenada1.latitude)
          val latitude2 = toRadians(coordenada2.latitude)

          val valorTotal = 2 * earthRadius * asin(
               sqrt(
                    pow(sin(distanceLatitude / 2), 2.0) +
                            pow(sin(distanceLength / 2), 2.0) *
                            cos(latitude1) *
                            cos(latitude2)
               )
          )

          distanceKM = distanceKM.plus(valorTotal)
          _distance.value = decimalFormat.format(distanceKM).toString() + "km"
     }

}
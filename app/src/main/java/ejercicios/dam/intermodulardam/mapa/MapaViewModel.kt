package ejercicios.dam.intermodulardam.mapa

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import ejercicios.dam.intermodulardam.mapa.domain.LocationState
import javax.inject.Inject


@HiltViewModel
class MapaViewModel @Inject constructor() : ViewModel() {

     lateinit var fusedLocationClient: FusedLocationProviderClient
     var locationState by mutableStateOf<LocationState>(LocationState.NoPermission)

     private val _currentLocation = MutableLiveData<LatLng>()
     val currentLocation:LiveData<LatLng> = _currentLocation

     fun getCurrentLocation(context:Context) {
          locationState = LocationState.LocationLoading
          if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
               ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
               ) != PackageManager.PERMISSION_GRANTED
          ) {
               return
          }
          fusedLocationClient
               .getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
               .addOnSuccessListener { location ->
                    locationState = if (location == null && locationState !is LocationState.LocationAvailable) {
                         LocationState.Error
                    } else {
                         LocationState.LocationAvailable(LatLng(location.latitude, location.longitude))
                    }
                    if(location != null) {
                         _currentLocation.value = LatLng(location.latitude, location.longitude)
                         Log.i("LOCATION", "${_currentLocation.value}")
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
                  isPrivate || !isPrivate
     }

     fun onCreateButtonClick() {

     }
}
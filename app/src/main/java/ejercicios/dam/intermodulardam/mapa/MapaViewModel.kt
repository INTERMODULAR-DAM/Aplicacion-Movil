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


}
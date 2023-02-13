package ejercicios.dam.intermodulardam.mapa.domain

import com.google.android.gms.maps.model.LatLng

sealed class LocationState {
    object NoPermission: LocationState()
    object LocationLoading: LocationState()
    data class LocationAvailable(val location: LatLng): LocationState()
    object Error: LocationState()
}

package ejercicios.dam.intermodulardam.rutas.data.network

import android.util.Log
import ejercicios.dam.intermodulardam.login.data.datastore.UserPreferenceService
import ejercicios.dam.intermodulardam.main.domain.entity.Publication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RutasService @Inject constructor(
    private val rutasClient: RutasClient,
    private val userService: UserPreferenceService
) {
    suspend fun getAllRoutes():List<Publication> {
        return withContext(Dispatchers.IO) {
            val response = rutasClient.getAllRoutes(userService.getToken("authorization"))
            Log.i("RESPONSE", "${response.body()?.data!!}")
            response.body()?.data!!
        }
    }
}
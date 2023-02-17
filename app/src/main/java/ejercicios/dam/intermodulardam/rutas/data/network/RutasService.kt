package ejercicios.dam.intermodulardam.rutas.data.network

import android.util.Log
import ejercicios.dam.intermodulardam.login.data.datastore.UserPreferenceService
import ejercicios.dam.intermodulardam.main.domain.entity.CreatePublication
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
            response.body()?.data!!
        }
    }

    suspend fun getAllPublicRoutes():List<Publication> {
        return withContext(Dispatchers.IO) {
            val response = rutasClient.getAllPublicRoutes(userService.getToken("authorization"))
            response.body()?.data!!
        }
    }

    suspend fun createRoute(publication: CreatePublication):Boolean {
        return withContext(Dispatchers.IO) {
            val response = rutasClient.createRoute(userService.getToken("authorization"), publication)
            response.body()?.data!!.isNotEmpty()
        }
    }
}
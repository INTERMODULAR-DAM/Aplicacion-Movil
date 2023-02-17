package ejercicios.dam.intermodulardam.profile.data

import ejercicios.dam.intermodulardam.main.domain.entity.Publication
import ejercicios.dam.intermodulardam.profile.data.network.ProfileService
import javax.inject.Inject

class ProfileRepository @Inject constructor(private val api: ProfileService) {
    suspend fun getOwnPosts():List<Publication> {
        return api.getOwnPosts()
    }
}
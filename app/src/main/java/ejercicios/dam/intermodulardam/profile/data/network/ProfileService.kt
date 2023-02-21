package ejercicios.dam.intermodulardam.profile.data.network

import ejercicios.dam.intermodulardam.login.data.datastore.UserPreferenceService
import ejercicios.dam.intermodulardam.main.domain.entity.Publication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProfileService @Inject constructor(
    private val client: ProfileClient,
    private val userService: UserPreferenceService
) {
    suspend fun getOwnPosts(): List<Publication> {
        return withContext(Dispatchers.IO) {
            val response = client.getOwnPosts(userService.getToken("authorization"))
            if(response.code() != 200) {
                listOf<Publication>()
            }
            response.body()?.data!!
        }
    }
}

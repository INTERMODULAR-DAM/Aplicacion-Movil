package ejercicios.dam.intermodulardam.profile.data.network

import android.util.Log
import ejercicios.dam.intermodulardam.login.data.database.dao.UserDAO
import ejercicios.dam.intermodulardam.login.data.database.entity.toDataBase
import ejercicios.dam.intermodulardam.login.data.datastore.UserPreferenceService
import ejercicios.dam.intermodulardam.login.data.network.dto.UserDTO
import ejercicios.dam.intermodulardam.main.domain.entity.Publication
import ejercicios.dam.intermodulardam.main.domain.entity.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProfileService @Inject constructor(
    private val client: ProfileClient,
    private val userService: UserPreferenceService,
    private val userDAO: UserDAO
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

    suspend infix fun updateUser(user: User):Boolean {
        val userDTO = UserDTO(
            user.id,
            user.email,
            user.name,
            user.lastname,
            user.date,
            user.nick,
            user.admin,
            user.pfp_path,
            user.phone_number,
            listOf()
        )
        return withContext(Dispatchers.IO) {
            val response = client.updateUser(userService.getToken("authorization"), userDTO)
            Log.i("RESPONSE", "$response")
            if(response.code() == 200) {
                userDAO.updateUser(userDTO.toDataBase(userService.getToken("authorization")))
                true
            } else {
                false
            }
        }
    }
}

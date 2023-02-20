package ejercicios.dam.intermodulardam.login.data.network

import android.util.Log
import ejercicios.dam.intermodulardam.login.data.datastore.UserPreferenceService
import ejercicios.dam.intermodulardam.login.data.network.dto.UserDTO
import ejercicios.dam.intermodulardam.login.domain.entity.RecoverEmail
import ejercicios.dam.intermodulardam.login.domain.entity.UserModel
import ejercicios.dam.intermodulardam.main.domain.entity.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginService @Inject constructor(
    private val loginClient: LoginClient,
    private val userService: UserPreferenceService
) {
    suspend fun doLogin(email: String, password: String): String {
        val user = UserModel(email, password)
        return withContext(Dispatchers.IO) {
            val response = loginClient.doLogin(user)
            response.body()?.data!!.ifEmpty {
                ""
            }
        }
    }

    suspend fun getLoginUser(): UserDTO {
        return withContext(Dispatchers.IO) {
            val response = loginClient.getLoginUser(userService.getToken("authorization"))
            val user:UserDTO = UserDTO(
                response.body()?.data!!._id,
                response.body()?.data!!.email,
                response.body()?.data!!.name,
                response.body()?.data!!.lastname,
                response.body()?.data!!.date,
                response.body()?.data!!.nick,
                response.body()?.data!!.admin,
                response.body()?.data!!.pfp_path,
                response.body()?.data!!.phone_number,
                response.body()?.data!!.following
            )
            user
        }
    }

    suspend fun recoverPassword(email:String):Boolean {
        val recoverEmail = RecoverEmail(email)
        return withContext(Dispatchers.IO) {
            val response = loginClient.recoverPassword(recoverEmail)
            response.body()?.status == 200
        }
    }
}

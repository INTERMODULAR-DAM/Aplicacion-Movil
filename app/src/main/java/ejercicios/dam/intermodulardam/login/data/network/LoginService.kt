package ejercicios.dam.intermodulardam.login.data.network

import android.util.Log
import ejercicios.dam.intermodulardam.login.data.datastore.UserPreferenceService
import ejercicios.dam.intermodulardam.login.data.network.dto.UserDTO
import ejercicios.dam.intermodulardam.login.domain.entity.UserModel
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

    suspend fun getLoginUser(): UserDTO? {
        return withContext(Dispatchers.IO) {
            val response = loginClient.getLoginUser(userService.getToken("authorization"))
            Log.i("USER", "${response.body()?.data!!}")
            response.body()?.data!!
        }
    }
}

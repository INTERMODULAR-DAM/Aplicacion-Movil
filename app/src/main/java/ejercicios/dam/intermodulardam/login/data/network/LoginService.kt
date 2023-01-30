package ejercicios.dam.intermodulardam.login.data.network

import android.util.Log
import ejercicios.dam.intermodulardam.login.data.dto.UserDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginService @Inject constructor(private val loginClient: LoginClient) {
    suspend fun doLogin(email:String, password:String) : Boolean {
        return withContext(Dispatchers.IO) {
            val user:UserDTO = UserDTO(email, password)
            val response = loginClient.doLogin(user)
            Log.i("RESPUESTA", "response: ${response.body()}")
            response.body()?.data!!.isNotEmpty()
        }
    }
}

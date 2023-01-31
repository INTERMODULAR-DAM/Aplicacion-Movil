package ejercicios.dam.intermodulardam.login.data.network

import android.util.Log
import androidx.datastore.dataStore
import ejercicios.dam.intermodulardam.login.data.dto.UserDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginService @Inject constructor(private val loginClient: LoginClient) {
    suspend fun doLogin(email:String, password:String) : String {
        return withContext(Dispatchers.IO) {
            val user:UserDTO = UserDTO(email, password)
            val response = loginClient.doLogin(user)
            Log.i("TOKEN", "token: ${response.body()?.data!!}")
            if(response.body()?.data!!.isNotEmpty()) response.body()?.data!!
            ""
        }
    }

    suspend fun getLoginUser(email:String, password:String) : Boolean {
        val response = loginClient.getLoginUser(email)
        Log.i("USUARIO", "usuario: ${response.body()?.data!!}")
        return response.body()?.data!!.isNotEmpty()
    }
}

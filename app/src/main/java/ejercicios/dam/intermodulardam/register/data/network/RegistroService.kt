package ejercicios.dam.intermodulardam.register.data.network

import ejercicios.dam.intermodulardam.register.data.dto.UserRegistroDTO
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers

class RegistroService @Inject constructor(private val registroClient: RegistroClient) {
    suspend fun doRegister(user: UserRegistroDTO): Boolean {
        return withContext(Dispatchers.IO) {
            val response = registroClient.register(user)
            response.body()?.data!!.isNotEmpty()
        }
    }
}
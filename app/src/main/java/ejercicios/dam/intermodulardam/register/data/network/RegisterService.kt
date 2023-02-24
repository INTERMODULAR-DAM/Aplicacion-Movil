package ejercicios.dam.intermodulardam.register.data.network


import ejercicios.dam.intermodulardam.register.data.dto.UserRegistroDTO
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers

class RegisterService @Inject constructor(
    private val registroClient: RegistroClient,
) {
    suspend fun doRegister(user: UserRegistroDTO): String {
        return withContext(Dispatchers.IO) {
            val response = registroClient.register(user)
            if(response.code() != 200) {
                ""
            } else {
                response.body()?.data!!
            }
        }
    }
}

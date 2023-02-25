package ejercicios.dam.intermodulardam.register.data.network


import ejercicios.dam.intermodulardam.register.data.dto.UserRegisterDTO
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers

class RegisterService @Inject constructor(
    private val registroClient: RegisterClient,
) {
    suspend fun doRegister(user: UserRegisterDTO): String {
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

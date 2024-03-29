package ejercicios.dam.intermodulardam.register.data.network

import ejercicios.dam.intermodulardam.register.data.dto.UserRegisterDTO
import ejercicios.dam.intermodulardam.register.data.network.response.DefaultResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterClient {
    @POST("/api/v1/users/signUp")
    suspend fun register(@Body user:UserRegisterDTO):Response<DefaultResponse>
}
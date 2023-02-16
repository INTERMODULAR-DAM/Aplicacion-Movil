package ejercicios.dam.intermodulardam.register.data.network

import ejercicios.dam.intermodulardam.register.data.dto.UserRegistroDTO
import ejercicios.dam.intermodulardam.register.data.network.response.DefaultResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegistroClient {
    @POST("/api/v1/users/signUp/")
    suspend fun register(@Body user:UserRegistroDTO):Response<DefaultResponse>
}
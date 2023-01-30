package ejercicios.dam.intermodulardam.registro.data.network

import ejercicios.dam.intermodulardam.registro.data.dto.UserRegistroDTO
import ejercicios.dam.intermodulardam.registro.data.network.response.DefaultResponse
import ejercicios.dam.intermodulardam.registro.data.network.response.RegistroResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface RegistroClient {
    @POST("/api/v1/users/signUp/")
    suspend fun register(@Body user:UserRegistroDTO):Response<DefaultResponse>
}
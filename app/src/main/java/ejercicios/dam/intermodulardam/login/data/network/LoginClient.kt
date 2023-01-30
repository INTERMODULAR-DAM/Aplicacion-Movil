package ejercicios.dam.intermodulardam.login.data.network

import ejercicios.dam.intermodulardam.login.data.dto.UserDTO
import ejercicios.dam.intermodulardam.login.data.network.response.DefaultResponse
import ejercicios.dam.intermodulardam.login.data.network.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LoginClient {
    @POST("/api/v1/users/signIn")
    suspend fun doLogin(@Body() user:UserDTO):Response<DefaultResponse>

    @GET("/api/v1/users/")
    suspend fun getLoginUser():Response<LoginResponse>
}
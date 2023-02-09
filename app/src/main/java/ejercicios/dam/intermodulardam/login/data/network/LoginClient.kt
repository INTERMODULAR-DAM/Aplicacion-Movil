package ejercicios.dam.intermodulardam.login.data.network

import ejercicios.dam.intermodulardam.login.data.network.response.DefaultResponse
import ejercicios.dam.intermodulardam.login.domain.entity.UserModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface LoginClient {

    @POST("/api/v1/users/signIn")
    suspend fun doLogin(@Body user:UserModel):Response<DefaultResponse>

    @GET("/api/v1/users/")
    suspend fun getLoginUser():Response<DefaultResponse>
}
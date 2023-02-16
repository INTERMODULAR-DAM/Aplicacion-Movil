package ejercicios.dam.intermodulardam.login.data.network

import ejercicios.dam.intermodulardam.login.data.network.response.DefaultResponse
import ejercicios.dam.intermodulardam.login.data.network.response.LoginResponse
import ejercicios.dam.intermodulardam.login.domain.entity.RecoverEmail
import ejercicios.dam.intermodulardam.login.domain.entity.UserModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST


interface LoginClient {

    @POST("/api/v1/users/signIn")
    suspend fun doLogin(@Body user:UserModel):Response<DefaultResponse>

    @POST("/api/v1/users/forgotPassword")
    suspend fun recoverPassword(@Body email:RecoverEmail):Response<DefaultResponse>

    @GET("/api/v1/users")
    suspend fun getLoginUser(@Header("authorization") token: String):Response<LoginResponse>
}
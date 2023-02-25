package ejercicios.dam.intermodulardam.profile.data.network

import ejercicios.dam.intermodulardam.login.data.network.dto.UserDTO
import ejercicios.dam.intermodulardam.login.data.network.response.DefaultResponse
import ejercicios.dam.intermodulardam.main.data.network.response.RoutesResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH

interface ProfileClient {
    @GET("/api/v1/posts/allOwnPosts")
    suspend fun getOwnPosts(@Header("authorization") token:String): Response<RoutesResponse>

    @PATCH("/api/v1/users")
    suspend fun updateUser(@Header("authorization") token:String, @Body user:UserDTO): Response<DefaultResponse>
}
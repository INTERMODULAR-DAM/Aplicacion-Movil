package ejercicios.dam.intermodulardam.profile.data.network

import ejercicios.dam.intermodulardam.main.data.network.response.RoutesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface ProfileClient {
    @GET("/api/v1/posts/allOwnPosts")
    suspend fun getOwnPosts(@Header("authorization") token:String): Response<RoutesResponse>
}
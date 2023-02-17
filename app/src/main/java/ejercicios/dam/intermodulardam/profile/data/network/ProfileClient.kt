package ejercicios.dam.intermodulardam.profile.data.network

import ejercicios.dam.intermodulardam.rutas.data.network.response.RutasResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface ProfileClient {

    @GET("/api/v1/posts/allOwnPosts")
    suspend fun getOwnPosts(@Header("authorization") token:String): Response<RutasResponse>
}
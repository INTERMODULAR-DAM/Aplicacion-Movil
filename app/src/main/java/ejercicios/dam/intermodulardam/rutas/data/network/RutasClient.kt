package ejercicios.dam.intermodulardam.rutas.data.network

import ejercicios.dam.intermodulardam.rutas.data.network.response.RutasResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface RutasClient {

    @GET("/api/v1/posts/getPublicPosts")
    suspend fun getAllRoutes(@Header("authorization") token: String):Response<RutasResponse>
}
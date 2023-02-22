package ejercicios.dam.intermodulardam.rutas.data.network

import ejercicios.dam.intermodulardam.main.domain.entity.CreatePublication
import ejercicios.dam.intermodulardam.main.domain.entity.User
import ejercicios.dam.intermodulardam.rutas.data.network.response.CrearRutasResponse
import ejercicios.dam.intermodulardam.rutas.data.network.response.RutasResponse
import ejercicios.dam.intermodulardam.rutas.data.network.response.SinglePostResponse
import retrofit2.Response
import retrofit2.http.*

interface RutasClient {

    @GET("/api/v1/posts/")
    suspend fun getPostByID(@Header("authorization") token:String, @Header("_id") id:String):Response<SinglePostResponse>

    @GET("/api/v1/posts/all")
    suspend fun getAllRoutes(@Header("authorization") token: String):Response<RutasResponse>

    @GET("/api/v1/posts/getPublicPosts")
    suspend fun getAllPublicRoutes(@Header("authorization") token: String):Response<RutasResponse>

    @POST("/api/v1/posts/")
    suspend fun createRoute(@Header("authorization") token:String, @Body publication: CreatePublication):Response<CrearRutasResponse>
}
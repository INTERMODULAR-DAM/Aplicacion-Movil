package ejercicios.dam.intermodulardam.main.data.network

import ejercicios.dam.intermodulardam.main.domain.entity.CreatePublication
import ejercicios.dam.intermodulardam.main.data.network.response.CreateRouteResponse
import ejercicios.dam.intermodulardam.main.data.network.response.RoutesResponse
import ejercicios.dam.intermodulardam.main.data.network.response.SinglePostResponse
import ejercicios.dam.intermodulardam.main.data.network.response.UserCreator
import retrofit2.Response
import retrofit2.http.*

interface RouteClient {

    @GET("/api/v1/posts/")
    suspend fun getPostByID(@Header("authorization") token:String, @Header("_id") id:String):Response<SinglePostResponse>

    @GET("/api/v1/posts/all")
    suspend fun getAllRoutes(@Header("authorization") token: String):Response<RoutesResponse>

    @GET("/api/v1/posts/getPublicPosts")
    suspend fun getAllPublicRoutes(@Header("authorization") token: String):Response<RoutesResponse>

    @POST("/api/v1/posts/")
    suspend fun createRoute(@Header("authorization") token:String, @Body publication: CreatePublication):Response<CreateRouteResponse>

    @GET("/api/v1/users/")
    suspend fun getUserById(@Header("authorization") token: String, @Header("_id") _id: String) : Response<UserCreator>
}
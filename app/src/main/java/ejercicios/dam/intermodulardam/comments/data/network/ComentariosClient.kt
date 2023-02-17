package ejercicios.dam.intermodulardam.comments.data.network

import ejercicios.dam.intermodulardam.comments.data.network.response.ComentariosResponse
import ejercicios.dam.intermodulardam.comments.domain.entity.Comentarios
import ejercicios.dam.intermodulardam.login.data.network.response.DefaultResponse
import ejercicios.dam.intermodulardam.main.domain.entity.Publication
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query


interface ComentariosClient {

    @GET("/api/v1/comments/getComments")
    suspend fun getComments(
        @Header("authorization") token:String,
        @Query("_id") id:String): Response<ComentariosResponse>

    @POST("/api/v1/comments")
    suspend fun createComment(
        @Header("authorization") token:String,
        @Body comentario:Comentarios):Response<DefaultResponse>

}
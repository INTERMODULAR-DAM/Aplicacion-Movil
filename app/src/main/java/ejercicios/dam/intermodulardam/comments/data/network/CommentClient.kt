package ejercicios.dam.intermodulardam.comments.data.network

import ejercicios.dam.intermodulardam.comments.data.dto.CommentDTO
import ejercicios.dam.intermodulardam.comments.data.network.response.CommentResponse
import ejercicios.dam.intermodulardam.comments.data.network.response.DeleteCommentResponse
import ejercicios.dam.intermodulardam.comments.domain.entity.Comment
import ejercicios.dam.intermodulardam.login.data.network.response.DefaultResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query


interface CommentClient {

    @GET("/api/v1/comments/getComments")
    suspend fun getComments(
        @Header("authorization") token:String,
        @Header("_id") id:String): Response<CommentResponse>

    @POST("/api/v1/comments")
    suspend fun createComment(
        @Header("authorization") token:String,
        @Body comentario:CommentDTO):Response<DefaultResponse>

    @DELETE("api/v1/comments")
    suspend fun deleteComment(
        @Header("authorization") token:String,
        @Header("_id") id:String): Response<DeleteCommentResponse>
}
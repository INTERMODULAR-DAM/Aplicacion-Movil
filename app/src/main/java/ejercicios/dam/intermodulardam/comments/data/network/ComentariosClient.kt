package ejercicios.dam.intermodulardam.comments.data.network

import ejercicios.dam.intermodulardam.comments.data.network.response.ComentariosResponse
import ejercicios.dam.intermodulardam.main.domain.entity.Publication
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET


interface ComentariosClient {

    @GET("/api/v1/comments/getComments")
    suspend fun getComments(@Body publication: Publication): Response<ComentariosResponse>
}
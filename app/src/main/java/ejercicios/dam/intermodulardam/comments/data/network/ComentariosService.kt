package ejercicios.dam.intermodulardam.comments.data.network

import ejercicios.dam.intermodulardam.comments.data.dto.ComentariosDTO
import ejercicios.dam.intermodulardam.comments.domain.entity.Comentarios
import ejercicios.dam.intermodulardam.login.data.datastore.UserPreferenceService
import ejercicios.dam.intermodulardam.main.domain.entity.Publication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ComentariosService @Inject constructor(
    private val client: ComentariosClient,
    private val userService: UserPreferenceService
) {
    suspend fun getComments(publication: Publication): List<Comentarios> {
        return withContext(Dispatchers.IO) {
            val response = client.getComments(userService.getToken("authorization"), publication.id)
            if(response.code() != 200) {
                listOf()
            } else {
                response.body()?.data!!
            }
        }
    }

    suspend fun createComment(comentario:ComentariosDTO):Boolean {
        return withContext(Dispatchers.IO) {
            val response = client.createComment(userService.getToken("authorization"), comentario)
            response.body()?.data!!.isEmpty()
        }
    }

    suspend fun deleteComment(id:String):Boolean {
        return withContext(Dispatchers.IO) {
            val response = client.deleteComment(userService.getToken("authorization"), id)
            response.code() == 200
        }
    }
}
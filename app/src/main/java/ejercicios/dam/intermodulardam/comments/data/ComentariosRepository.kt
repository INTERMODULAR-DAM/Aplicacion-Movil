package ejercicios.dam.intermodulardam.comments.data

import ejercicios.dam.intermodulardam.comments.data.network.ComentariosService
import ejercicios.dam.intermodulardam.comments.domain.entity.Comentarios
import ejercicios.dam.intermodulardam.main.domain.entity.Publication
import javax.inject.Inject

class ComentariosRepository @Inject constructor(private val api: ComentariosService) {
    suspend fun getComments(publication: Publication):List<Comentarios> {
        return api.getComments(publication)
    }

    suspend fun createComment(comentario:Comentarios):Boolean {
        return api.createComment(comentario)
    }

    suspend fun deleteComment(id:String):Boolean {
        return api.deleteComment(id)
    }
}
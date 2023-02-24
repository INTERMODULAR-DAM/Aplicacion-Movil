package ejercicios.dam.intermodulardam.comments.data

import ejercicios.dam.intermodulardam.comments.data.dto.CommentDTO
import ejercicios.dam.intermodulardam.comments.data.network.CommentService
import ejercicios.dam.intermodulardam.comments.domain.entity.Comment
import ejercicios.dam.intermodulardam.main.domain.entity.Publication
import javax.inject.Inject

class ComentariosRepository @Inject constructor(private val api: CommentService) {
    suspend fun getComments(publication: Publication):List<Comment> {
        return api.getComments(publication)
    }

    suspend fun createComment(comentario: CommentDTO):Boolean {
        return api.createComment(comentario)
    }

    suspend fun deleteComment(id:String):Boolean {
        return api.deleteComment(id)
    }
}
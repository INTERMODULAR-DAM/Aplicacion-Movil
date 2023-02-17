package ejercicios.dam.intermodulardam.comments.domain.usecase

import ejercicios.dam.intermodulardam.comments.data.ComentariosRepository
import ejercicios.dam.intermodulardam.comments.domain.entity.Comentarios
import javax.inject.Inject

class CreateCommentUseCase @Inject constructor(private val repository: ComentariosRepository) {
    suspend operator fun invoke(comentario:Comentarios):Boolean {
        return repository.createComment(comentario)
    }
}
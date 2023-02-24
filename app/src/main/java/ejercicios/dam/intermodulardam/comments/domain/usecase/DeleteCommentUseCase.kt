package ejercicios.dam.intermodulardam.comments.domain.usecase

import ejercicios.dam.intermodulardam.comments.data.ComentariosRepository
import javax.inject.Inject

class DeleteCommentUseCase @Inject constructor(private val repository: ComentariosRepository) {
    suspend operator fun invoke(id:String):Boolean {
        return repository.deleteComment(id)
    }
}
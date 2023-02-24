package ejercicios.dam.intermodulardam.comments.domain.usecase

import ejercicios.dam.intermodulardam.comments.data.ComentariosRepository
import ejercicios.dam.intermodulardam.comments.data.dto.CommentDTO
import javax.inject.Inject

class CreateCommentUseCase @Inject constructor(private val repository: ComentariosRepository) {
    suspend operator fun invoke(comment: CommentDTO):Boolean {
        return repository.createComment(comment)
    }
}
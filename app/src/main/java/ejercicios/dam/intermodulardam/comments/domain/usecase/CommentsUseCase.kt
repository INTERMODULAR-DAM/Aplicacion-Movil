package ejercicios.dam.intermodulardam.comments.domain.usecase

import ejercicios.dam.intermodulardam.comments.data.ComentariosRepository
import ejercicios.dam.intermodulardam.comments.domain.entity.Comment
import ejercicios.dam.intermodulardam.main.domain.entity.Publication
import javax.inject.Inject

class CommentsUseCase @Inject constructor(private val repository: ComentariosRepository) {
    suspend operator fun invoke(publication: Publication):List<Comment> {
        return repository.getComments(publication)
    }
}
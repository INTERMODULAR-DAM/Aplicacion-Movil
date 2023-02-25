package ejercicios.dam.intermodulardam.comments.domain.usecase

import ejercicios.dam.intermodulardam.comments.data.ComentariosRepository
import ejercicios.dam.intermodulardam.comments.domain.entity.Comment
import ejercicios.dam.intermodulardam.main.domain.entity.Publication
import javax.inject.Inject

class GetAllCommentsUseCase @Inject constructor(private val repository: ComentariosRepository) {
    suspend operator fun invoke(id : String):MutableList<Comment> {
        return repository.getComments(id)
    }
}
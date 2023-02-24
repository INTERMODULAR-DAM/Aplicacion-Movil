package ejercicios.dam.intermodulardam.main.domain

import ejercicios.dam.intermodulardam.main.data.MainRepository
import ejercicios.dam.intermodulardam.main.domain.entity.Publication
import javax.inject.Inject

class GetPostById @Inject constructor(private val repository: MainRepository) {
    suspend operator fun invoke(id: String): Publication {
        return repository.getPostByID(id)
    }
}
package ejercicios.dam.intermodulardam.main.domain

import ejercicios.dam.intermodulardam.main.data.MainRepository
import ejercicios.dam.intermodulardam.main.domain.entity.Publication
import javax.inject.Inject

class GetAllPosts @Inject constructor(private val repository: MainRepository){
    suspend operator fun invoke() : List<Publication>{
        return repository.getAllPosts()
    }
}
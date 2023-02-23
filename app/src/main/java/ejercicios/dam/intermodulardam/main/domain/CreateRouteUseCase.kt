package ejercicios.dam.intermodulardam.main.domain

import ejercicios.dam.intermodulardam.main.data.MainRepository
import ejercicios.dam.intermodulardam.main.domain.entity.CreatePublication
import javax.inject.Inject

class CreateRouteUseCase @Inject constructor(private val repository: MainRepository) {
    suspend operator fun invoke(publication: CreatePublication):Boolean {
        return repository.createRoute(publication)
    }
}





package ejercicios.dam.intermodulardam.main.domain

import ejercicios.dam.intermodulardam.main.data.MainRepository
import ejercicios.dam.intermodulardam.main.domain.entity.User
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val repository: MainRepository) {
        suspend operator fun invoke() : User{
            return repository.getUser()
        }
}
package ejercicios.dam.intermodulardam.main.domain

import ejercicios.dam.intermodulardam.login.data.network.dto.UserDTO
import ejercicios.dam.intermodulardam.main.data.MainRepository
import ejercicios.dam.intermodulardam.main.domain.entity.User
import javax.inject.Inject


class GetUserByIdUseCase @Inject constructor(private val repository : MainRepository) {
    suspend operator fun invoke(user : String) : User {
        return repository.getUserById(user)
    }
}
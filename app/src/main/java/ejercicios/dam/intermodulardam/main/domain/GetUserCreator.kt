package ejercicios.dam.intermodulardam.main.domain

import ejercicios.dam.intermodulardam.login.data.network.dto.UserDTO
import ejercicios.dam.intermodulardam.main.data.MainRepository
import javax.inject.Inject


class GetUserCreator @Inject constructor(private val repository : MainRepository) {
    suspend operator fun invoke(user : String) : UserDTO {
        return repository.getUserById(user)
    }
}
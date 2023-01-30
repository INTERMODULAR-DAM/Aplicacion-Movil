package ejercicios.dam.intermodulardam.registro.domain.usecase

import ejercicios.dam.intermodulardam.registro.data.RegistroRepository
import ejercicios.dam.intermodulardam.registro.domain.entity.UserRegistroModel
import javax.inject.Inject

class RegistroUseCase @Inject constructor(private val repository: RegistroRepository) {
    suspend operator fun invoke(user: UserRegistroModel) : Boolean {
        return repository.doRegister(user.email, user.name, user.surname, user.nick, user.phone, user.password)
    }
}
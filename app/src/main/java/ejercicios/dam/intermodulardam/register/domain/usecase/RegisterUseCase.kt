package ejercicios.dam.intermodulardam.register.domain.usecase

import ejercicios.dam.intermodulardam.register.data.RegisterRepository
import ejercicios.dam.intermodulardam.register.domain.entity.UserRegisterModel
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val repository: RegisterRepository) {
    suspend operator fun invoke(user: UserRegisterModel) : Boolean {
        return repository.doRegister(user.email, user.name, user.surname, user.nick, user.phone, user.password)
    }
}
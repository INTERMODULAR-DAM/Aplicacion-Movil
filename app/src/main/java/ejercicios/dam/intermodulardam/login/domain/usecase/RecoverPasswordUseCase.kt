package ejercicios.dam.intermodulardam.login.domain.usecase

import ejercicios.dam.intermodulardam.login.data.LoginRepository
import javax.inject.Inject


class RecoverPasswordUseCase @Inject constructor(private val repository: LoginRepository) {
    suspend operator fun invoke(email:String):Boolean {
        return repository.recoverPassword(email)
    }
}
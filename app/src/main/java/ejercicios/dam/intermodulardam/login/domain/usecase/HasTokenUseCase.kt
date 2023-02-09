package ejercicios.dam.intermodulardam.login.domain.usecase

import ejercicios.dam.intermodulardam.login.data.LoginRepository
import javax.inject.Inject

class HasTokenUseCase @Inject constructor(private val repository: LoginRepository) {
    suspend operator fun invoke() : Boolean = repository.getConnectionToken()
}
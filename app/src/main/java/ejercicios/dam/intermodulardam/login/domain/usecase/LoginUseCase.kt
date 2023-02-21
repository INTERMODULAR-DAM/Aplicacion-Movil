package ejercicios.dam.intermodulardam.login.domain.usecase

import ejercicios.dam.intermodulardam.login.data.LoginRepository
import ejercicios.dam.intermodulardam.login.domain.entity.UserModel
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repository: LoginRepository) {
    suspend operator fun invoke(email:String, password:String) : String {
        return repository.doLogin(email, password)
    }

}

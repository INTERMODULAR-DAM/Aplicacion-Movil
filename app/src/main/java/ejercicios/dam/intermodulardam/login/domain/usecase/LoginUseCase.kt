package ejercicios.dam.intermodulardam.login.domain.usecase

import ejercicios.dam.intermodulardam.login.data.LoginRepository
import ejercicios.dam.intermodulardam.login.domain.entity.UserModel
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repository: LoginRepository) {
    suspend operator fun invoke(email:String, password:String) : Boolean {
        return repository.doLogin(email, password)
    }

    suspend operator fun invoke(user:UserModel) : Boolean {
        return repository.getUserLogin(user.id, user.password)
    }
}
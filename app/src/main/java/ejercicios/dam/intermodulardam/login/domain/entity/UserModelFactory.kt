package ejercicios.dam.intermodulardam.login.domain.entity


import javax.inject.Inject

class UserModelFactory @Inject constructor() {
    operator fun invoke(
        id:String,
        password: String,
    ) : UserModel {
        return UserModel(
            id = id,
            password = password,
        )
    }
}
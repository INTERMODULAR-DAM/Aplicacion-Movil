package ejercicios.dam.intermodulardam.login.data.dto

import javax.inject.Inject

class UserDTOFactory @Inject constructor() {
    operator fun invoke(
        id:String,
        password: String,
    ) : UserDTO {
        return UserDTO(
            id = id,
            password = password,
        )
    }
}
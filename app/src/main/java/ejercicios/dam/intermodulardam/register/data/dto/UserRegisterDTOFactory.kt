package ejercicios.dam.intermodulardam.register.data.dto

import javax.inject.Inject

class UserRegisterDTOFactory @Inject constructor() {
    operator fun invoke(
        email:String,
        name:String,
        lastname:String,
        nick:String,
        password: String,
        phone_number:String,
    ) : UserRegisterDTO {
        return UserRegisterDTO(
            email = email,
            name = name,
            lastname = lastname,
            nick = nick,
            password = password,
            phone_number = phone_number,
        )
    }
}
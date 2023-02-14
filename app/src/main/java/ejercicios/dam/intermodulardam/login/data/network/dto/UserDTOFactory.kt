package ejercicios.dam.intermodulardam.login.data.network.dto

import javax.inject.Inject

class UserDTOFactory @Inject constructor() {
    operator fun invoke(
        _id:String,
        email:String,
        name:String,
        lastname:String,
        date: String,
        nick:String,
        admin:Boolean,
        pfp_path:String,
        phone_number:String,
    ) : UserDTO {
        return UserDTO(
            _id = _id,
            email = email,
            name = name,
            lastname = lastname,
            date = date,
            nick = nick,
            admin = admin,
            pfp_path = pfp_path,
            phone_number = phone_number
        )
    }
}
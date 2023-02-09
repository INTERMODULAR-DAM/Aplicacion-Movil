package ejercicios.dam.intermodulardam.login.data.network.dto

import javax.inject.Inject

class UserDTOFactory @Inject constructor() {
    operator fun invoke(
        id: String,
        token:String,
        email:String,
        name:String,
        surname:String,
        date: String,
        nick:String,
        admin:Boolean,
        web:String,
        pfp_path:String,
        phone:String,
    ) : UserDTO {
        return UserDTO(
            id = id,
            token = token,
            email = email,
            name = name,
            surname = surname,
            date = date,
            nick = nick,
            admin = admin,
            web = web,
            pfp_path = pfp_path,
            phone = phone
        )
    }
}
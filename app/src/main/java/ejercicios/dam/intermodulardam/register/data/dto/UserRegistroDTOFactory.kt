package ejercicios.dam.intermodulardam.register.data.dto

import java.util.Date
import javax.inject.Inject

class UserRegistroDTOFactory @Inject constructor() {
    operator fun invoke(
        email:String,
        name:String,
        lastname:String,
        date:Date,
        nick:String,
        password: String,
        admin:Boolean,
        web:String,
        pfp_path:String,
        phone_number:String,
        following:List<UserRegistroDTO>
    ) : UserRegistroDTO {
        return UserRegistroDTO(
            email = email,
            name = name,
            lastname = lastname,
            date = date,
            nick = nick,
            password = password,
            admin = admin,
            web = web,
            pfp_path = pfp_path,
            phone_number = phone_number,
            following = following
        )
    }
}
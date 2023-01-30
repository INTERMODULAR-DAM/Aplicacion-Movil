package ejercicios.dam.intermodulardam.registro.data

import ejercicios.dam.intermodulardam.registro.data.dto.UserRegistroDTO
import ejercicios.dam.intermodulardam.registro.data.network.RegistroService
import java.util.*
import javax.inject.Inject


class RegistroRepository @Inject constructor(private val api: RegistroService) {
    suspend fun doRegister(id:String, name:String, surname:String, nick:String, phone:String, password:String) : Boolean {
        val user:UserRegistroDTO = UserRegistroDTO(id, name, surname, Date(), nick, password, false, "", "", phone, listOf())
        return api.doRegister(user)
    }
}
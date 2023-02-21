package ejercicios.dam.intermodulardam.register.data

import android.util.Log
import ejercicios.dam.intermodulardam.login.data.database.dao.UserDAO
import ejercicios.dam.intermodulardam.login.data.database.entity.toDataBase
import ejercicios.dam.intermodulardam.login.data.datastore.UserPreferenceService
import ejercicios.dam.intermodulardam.login.data.network.LoginService
import ejercicios.dam.intermodulardam.login.data.network.dto.UserDTO
import ejercicios.dam.intermodulardam.register.data.dto.UserRegistroDTO
import ejercicios.dam.intermodulardam.register.data.network.RegistroService
import java.util.*
import javax.inject.Inject


class RegistroRepository @Inject constructor(
    private val api: RegistroService,
    private val userService: UserPreferenceService,
    private val userDAO: UserDAO,
    private val loginApi:LoginService
) {
    suspend fun doRegister(id:String, name:String, surname:String, nick:String, phone:String, password:String) : Boolean {
        userDAO.deleteAllUsers()

        val user = UserRegistroDTO(id, name, surname, nick, password, phone)
        val response = api.doRegister(user)
        if(response.isNotEmpty()) {
            userService.addToken("authorization", "bearer $response")
            val user: UserDTO? = loginApi.getLoginUser()
            if(user != null) {
                userDAO.insertAll(
                    listOf(user.toDataBase(userService.getToken("authorization")))
                )
                return true
            }
            return false
        }
        return false
    }
}
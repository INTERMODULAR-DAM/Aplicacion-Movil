package ejercicios.dam.intermodulardam.login.data

import ejercicios.dam.intermodulardam.login.data.database.dao.UserDAO
import ejercicios.dam.intermodulardam.login.data.database.entity.toDataBase
import ejercicios.dam.intermodulardam.login.data.datastore.UserPreferenceService
import ejercicios.dam.intermodulardam.login.data.network.LoginService
import ejercicios.dam.intermodulardam.login.data.network.dto.UserDTO
import javax.inject.Inject

class LoginRepository @Inject
    constructor(private val api: LoginService,
                private val userPreference: UserPreferenceService,
                private val userDAO: UserDAO) {

    suspend fun doLogin(email:String, password:String) : Boolean {
        val connectionOk = api.doLogin(email, password)
        if(connectionOk.isNotEmpty()) {
            userPreference.addToken("authorization", "bearer $connectionOk")
            val user: UserDTO? = api.getLoginUser()
            if(user != null) {
                userDAO.insertAll(
                    listOf(user.toDataBase(userPreference.getToken("authorization")))
                )
                return true
            }
            return false

        }
        return false
    }

    suspend fun getConnectionToken():Boolean = userPreference.getToken("authorization").isNotEmpty()
    suspend fun isUserConnected(): Boolean = userDAO.getAllUsers().count() > 1

}
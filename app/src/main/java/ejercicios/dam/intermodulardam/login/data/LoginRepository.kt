package ejercicios.dam.intermodulardam.login.data

import android.util.Log
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

    suspend fun doLogin(email:String, password:String) : String {
        userDAO.deleteAllUsers()

        val connectionOk = api.doLogin(email, password)
        if(connectionOk.isNotEmpty()) {
            userPreference.addToken("authorization", "Bearer $connectionOk")
            val user: UserDTO = api.getLoginUser()
            userDAO.insertAll(
                listOf(user.toDataBase(userPreference.getToken("authorization")))
            )
            return user._id
        }
        return ""
    }

    suspend fun getConnectionToken():Boolean = userPreference.getToken("authorization").isNotEmpty()
    suspend fun isUserConnected(): Boolean = userDAO.getAllUsers().count() > 1


    suspend fun recoverPassword(email:String):Boolean = api.recoverPassword(email)

}
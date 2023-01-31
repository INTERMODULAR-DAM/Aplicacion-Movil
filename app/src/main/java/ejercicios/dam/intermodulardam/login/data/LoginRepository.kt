package ejercicios.dam.intermodulardam.login.data

import android.util.Log
import ejercicios.dam.intermodulardam.login.data.db.UserPreferenceService
import ejercicios.dam.intermodulardam.login.data.network.LoginService
import javax.inject.Inject


class LoginRepository @Inject
    constructor(private val api: LoginService, private val db: UserPreferenceService) {
    suspend fun doLogin(email:String, password:String) : Boolean {
        val connectionOk =  api.doLogin(email, password)
        if(connectionOk.isNotEmpty()) {
            db.addToken("authorization", connectionOk)
            return true
        }
        return false
    }

    suspend fun getUserLogin(email:String, password:String) : Boolean {
        return api.getLoginUser(email, password)
    }
}
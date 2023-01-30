package ejercicios.dam.intermodulardam.login.data

import ejercicios.dam.intermodulardam.login.data.network.LoginService
import javax.inject.Inject


class LoginRepository @Inject
    constructor(private val api: LoginService) {
    suspend fun doLogin(email:String, password:String) : Boolean {
        return api.doLogin(email, password)
    }

    suspend fun getUserLogin(email:String, password:String) : Boolean {
        return api.getLoginUser(email, password)
    }
}
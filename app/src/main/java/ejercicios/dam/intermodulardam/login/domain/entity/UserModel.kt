package ejercicios.dam.intermodulardam.login.domain.entity

import com.google.gson.annotations.SerializedName

data class UserModel(
    @SerializedName("id") val id:String,
    @SerializedName("password") val password:String,
)

data class RecoverEmail(
    @SerializedName("email") val email:String
)

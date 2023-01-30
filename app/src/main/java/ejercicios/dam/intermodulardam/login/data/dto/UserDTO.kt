package ejercicios.dam.intermodulardam.login.data.dto

import com.google.gson.annotations.SerializedName

data class UserDTO(
    @SerializedName("id") val id:String,
    @SerializedName("password") val password:String,
)

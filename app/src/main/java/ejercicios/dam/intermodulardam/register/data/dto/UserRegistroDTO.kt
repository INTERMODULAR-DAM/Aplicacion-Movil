package ejercicios.dam.intermodulardam.register.data.dto

import com.google.gson.annotations.SerializedName
import java.util.*

data class UserRegistroDTO(
    @SerializedName("email") val email:String,
    @SerializedName("name") val name:String,
    @SerializedName("lastname") val lastname:String,
    @SerializedName("nick") val nick:String,
    @SerializedName("password") val password:String,
    @SerializedName("phone_number") val phone_number:String,
)

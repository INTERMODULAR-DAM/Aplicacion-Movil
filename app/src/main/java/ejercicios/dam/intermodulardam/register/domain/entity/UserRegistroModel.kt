package ejercicios.dam.intermodulardam.register.domain.entity

import com.google.gson.annotations.SerializedName

data class UserRegistroModel(
    @SerializedName("email") val email:String,
    @SerializedName("name") val name:String,
    @SerializedName("surname") val surname:String,
    @SerializedName("nick") val nick:String,
    @SerializedName("password") val password:String,
    @SerializedName("phone") val phone:String,
)

package ejercicios.dam.intermodulardam.comments.data.dto

import com.google.gson.annotations.SerializedName

data class ComentariosDTO(
    @SerializedName("message") val message:String,
    @SerializedName("user") val user:String,
    @SerializedName("post") val post:String,
)

package ejercicios.dam.intermodulardam.comments.domain.entity

import com.google.gson.annotations.SerializedName

data class Comentarios(
    @SerializedName("message") val message:String,
    @SerializedName("user") val user:String,
    @SerializedName("post") val post:String,
)

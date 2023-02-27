package ejercicios.dam.intermodulardam.comments.domain.entity

import com.google.gson.annotations.SerializedName
import java.util.Date

data class Comment(
    @SerializedName("_id") val id:String,
    @SerializedName("message") val message:String,
    @SerializedName("user") val user:String,
    @SerializedName("post") val post:String,
    @SerializedName("date") val date : Date
)

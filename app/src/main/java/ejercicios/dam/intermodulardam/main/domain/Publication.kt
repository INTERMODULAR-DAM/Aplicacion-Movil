package ejercicios.dam.intermodulardam.main.domain

import com.google.gson.annotations.SerializedName
import java.util.Date

data class Publication(
    @SerializedName("_id") val id:String,
    @SerializedName("date") val date:Date,
    @SerializedName("name") val name:Date,
    @SerializedName("category") val category:String,
    @SerializedName("distancia") val distancia:Float,
    @SerializedName("duracion") val duracion:Float,
    @SerializedName("description") val description:String,
    @SerializedName("photos") val photos:List<String>,
    @SerializedName("privacity") val privacity:Boolean,
    @SerializedName("user") val user:String,
    @SerializedName("track") val track:List<String>
)

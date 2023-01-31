package ejercicios.dam.intermodulardam.rutas.data.network.response

import com.google.gson.annotations.SerializedName
import java.util.*

data class RutasResponse(
    @SerializedName("_id") val id:String,
    @SerializedName("date") val date: Date,
    @SerializedName("name") val name: Date,
    @SerializedName("category") val category:String,
    @SerializedName("distancia") val distancia:Float,
    @SerializedName("duracion") val duracion:Float,
    @SerializedName("description") val description:String,
    @SerializedName("photos") val photos:List<String>,
    @SerializedName("privacity") val privacity:Boolean,
    @SerializedName("user") val user:String,
    @SerializedName("track") val track:List<String>
)

data class DefaultResponse(
    @SerializedName("status") val status:String,
    @SerializedName("data") val data:String,
)

package ejercicios.dam.intermodulardam.main.domain.entity

import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName
import java.util.Date
import java.util.ArrayList

data class Publication(
    @SerializedName("photos") val photos:Int,
    @SerializedName("_id") val id:String,
    @SerializedName("date") val date:Date,
    @SerializedName("name") val name:String,
    @SerializedName("category") val category:String,
    @SerializedName("distance") val distancia:String,
    @SerializedName("difficulty") val difficulty:String,
    @SerializedName("track") val track:ArrayList<LatLng>,
    @SerializedName("duration") val duracion:String,
    @SerializedName("description") val description:String,
    @SerializedName("privacity") val privacity:Boolean,
    @SerializedName("user") val user:String,
)

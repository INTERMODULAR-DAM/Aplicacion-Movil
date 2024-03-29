package ejercicios.dam.intermodulardam.main.domain.entity

import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName
import java.util.Date
import java.util.ArrayList

data class Publication(
    @SerializedName("photos") val photos:ArrayList<String>,
    @SerializedName("_id") val id:String,
    @SerializedName("date") val date:Date,
    @SerializedName("name") val name:String,
    @SerializedName("category") val category:String,
    @SerializedName("distance") val distance:String,
    @SerializedName("difficulty") val difficulty:String,
    @SerializedName("track") val track:List<LatitudeLongitude>,
    @SerializedName("duration") val duration:String,
    @SerializedName("description") val description:String,
    @SerializedName("privacity") val privacity:Boolean,
    @SerializedName("user") val user:String,
)

data class CreatePublication(
    @SerializedName("name") val name:String,
    @SerializedName("category") val category:String,
    @SerializedName("distance") val distance:String,
    @SerializedName("difficulty") val difficulty:String,
    @SerializedName("track") val track:List<LatitudeLongitude>,
    @SerializedName("duration") val duration:String,
    @SerializedName("description") val description:String,
    @SerializedName("privacity") val privacity:Boolean,
    @SerializedName("user") val user:String,
)

data class LatitudeLongitude(
    @SerializedName("lat") val lat:Double,
    @SerializedName("lng") val lng:Double
)

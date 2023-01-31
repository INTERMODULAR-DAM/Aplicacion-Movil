package ejercicios.dam.intermodulardam.main.domain

import com.google.gson.annotations.SerializedName
import java.util.*

data class User(
    @SerializedName("_id") val id:String,
    @SerializedName("email") val email:String,
    @SerializedName("name") val name:String,
    @SerializedName("lastname") val surname:String,
    @SerializedName("date") val date: Date,
    @SerializedName("nick") val nick:String,
    @SerializedName("password") val password:String,
    @SerializedName("admin") val admin:Boolean,
    @SerializedName("web") val web:String,
    @SerializedName("pfp_path") val pfp_path:String,
    @SerializedName("phone_number") val phone:String,
    @SerializedName("following") val following:List<String>
)

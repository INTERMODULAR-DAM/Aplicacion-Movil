package ejercicios.dam.intermodulardam.login.data.network.response

import com.google.gson.annotations.SerializedName
import java.util.Date

data class LoginResponse(
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

data class DefaultResponse(
    @SerializedName("status") val status:Int,
    @SerializedName("data") val data:String
)
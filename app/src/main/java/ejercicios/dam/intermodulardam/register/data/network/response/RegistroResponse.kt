package ejercicios.dam.intermodulardam.register.data.network.response

import com.google.gson.annotations.SerializedName
import ejercicios.dam.intermodulardam.register.data.dto.UserRegistroDTO
import java.util.*

data class RegistroResponse(
    @SerializedName("email") val email:String,
    @SerializedName("name") val name:String,
    @SerializedName("lastname") val lastname:String,
    @SerializedName("date") val date: Date,
    @SerializedName("nick") val nick:String,
    @SerializedName("password") val password:String,
    @SerializedName("admin") val admin:Boolean,
    @SerializedName("web") val web:String,
    @SerializedName("pfp_path") val pfp_path:String,
    @SerializedName("phone_number") val phone_number:String,
    @SerializedName("following") val following:List<UserRegistroDTO>
)

data class DefaultResponse(
    @SerializedName("status") val status:Int,
    @SerializedName("data") val data:String
)

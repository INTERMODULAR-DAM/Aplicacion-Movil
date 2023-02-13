package ejercicios.dam.intermodulardam.login.data.network.response

import com.google.gson.annotations.SerializedName
import ejercicios.dam.intermodulardam.login.data.network.dto.UserDTO
import java.util.Date

data class LoginResponse(
    @SerializedName("status") val status:Int,
    @SerializedName("data") val data:UserDTO
)

data class DefaultResponse(
    @SerializedName("status") val status:Int,
    @SerializedName("data") val data:String
)
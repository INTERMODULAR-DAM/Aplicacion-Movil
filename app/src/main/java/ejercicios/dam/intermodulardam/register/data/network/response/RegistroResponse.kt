package ejercicios.dam.intermodulardam.register.data.network.response

import com.google.gson.annotations.SerializedName
import ejercicios.dam.intermodulardam.register.data.dto.UserRegistroDTO
import java.util.*

data class DefaultResponse(
    @SerializedName("status") val status:Int,
    @SerializedName("data") val data:String
)

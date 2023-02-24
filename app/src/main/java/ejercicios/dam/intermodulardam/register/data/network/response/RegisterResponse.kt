package ejercicios.dam.intermodulardam.register.data.network.response

import com.google.gson.annotations.SerializedName

data class DefaultResponse(
    @SerializedName("status") val status:Int,
    @SerializedName("data") val data:String
)

package ejercicios.dam.intermodulardam.main.data.network.response

import com.google.gson.annotations.SerializedName
import ejercicios.dam.intermodulardam.login.data.network.dto.UserDTO
import ejercicios.dam.intermodulardam.main.domain.entity.Publication
import java.util.*

data class RutasResponse(
    @SerializedName("status") val status:String,
    @SerializedName("data") val data:ArrayList<Publication>,
)

data class SinglePostResponse(
    @SerializedName("status") val status:String,
    @SerializedName("data") val data:Publication,
)

data class CrearRutasResponse(
    @SerializedName("status") val status:String,
    @SerializedName("data") val data:String,
)

data class userCreator (
    @SerializedName("status") val status : String,
    @SerializedName("data") val data : UserDTO
)

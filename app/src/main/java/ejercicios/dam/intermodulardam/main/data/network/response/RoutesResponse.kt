package ejercicios.dam.intermodulardam.main.data.network.response

import com.google.gson.annotations.SerializedName
import ejercicios.dam.intermodulardam.login.data.network.dto.UserDTO
import ejercicios.dam.intermodulardam.main.domain.entity.Publication
import java.util.*

data class RoutesResponse(
    @SerializedName("status") val status:String,
    @SerializedName("data") val data:ArrayList<Publication>,
)

data class SinglePostResponse(
    @SerializedName("status") val status:String,
    @SerializedName("data") val data:Publication,
)

data class CreateRouteResponse(
    @SerializedName("status") val status:String,
    @SerializedName("data") val data:String,
)

data class UserCreator (
    @SerializedName("status") val status : String,
    @SerializedName("data") val data : UserDTO
)

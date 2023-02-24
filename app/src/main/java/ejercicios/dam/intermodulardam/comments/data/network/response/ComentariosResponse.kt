package ejercicios.dam.intermodulardam.comments.data.network.response

import com.google.gson.annotations.SerializedName
import ejercicios.dam.intermodulardam.comments.domain.entity.Comentarios

data class ComentariosResponse(
    @SerializedName("status") val status:Int,
    @SerializedName("data") val data:List<Comentarios>,
)

data class DeleteCommentResponse(
    @SerializedName("status") val status:Int,
    @SerializedName("data") val data:String
)

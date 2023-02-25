package ejercicios.dam.intermodulardam.comments.data.network.response

import com.google.gson.annotations.SerializedName
import ejercicios.dam.intermodulardam.comments.domain.entity.Comment

data class CommentResponse(
    @SerializedName("status") val status:Int,
    @SerializedName("data") val data:MutableList<Comment>
)

data class DeleteCommentResponse(
    @SerializedName("status") val status:Int,
    @SerializedName("data") val data:String,
)

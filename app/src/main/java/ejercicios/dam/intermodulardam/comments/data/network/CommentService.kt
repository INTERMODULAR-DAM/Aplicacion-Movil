package ejercicios.dam.intermodulardam.comments.data.network

import ejercicios.dam.intermodulardam.comments.data.dto.CommentDTO
import ejercicios.dam.intermodulardam.comments.domain.entity.Comment
import ejercicios.dam.intermodulardam.login.data.datastore.UserPreferenceService
import ejercicios.dam.intermodulardam.main.domain.entity.Publication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CommentService @Inject constructor(
    private val client: CommentClient,
    private val userService: UserPreferenceService
) {
    suspend fun getComments(id : String): MutableList<Comment> {
        return withContext(Dispatchers.IO) {
            val response = client.getComments(userService.getToken("authorization"), id)
            if(response.code() != 200) {
                mutableListOf()
            } else {
                response.body()?.data!!
            }
        }
    }
    suspend fun createComment(comment: CommentDTO):Boolean {
        return withContext(Dispatchers.IO) {
            val response = client.createComment(userService.getToken("authorization"), comment)
            response.body()?.data!!.isNotEmpty()
        }
    }

    suspend fun deleteComment(id:String):Boolean {
        return withContext(Dispatchers.IO) {
            val response = client.deleteComment(userService.getToken("authorization"), id)
            response.code() == 200
        }
    }
}
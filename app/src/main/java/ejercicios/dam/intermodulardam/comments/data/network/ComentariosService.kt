package ejercicios.dam.intermodulardam.comments.data.network

import ejercicios.dam.intermodulardam.comments.domain.entity.Comentarios
import ejercicios.dam.intermodulardam.main.domain.entity.Publication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ComentariosService @Inject constructor(private val client: ComentariosClient) {
    suspend fun getComments(publication: Publication):List<Comentarios> {
        return withContext(Dispatchers.IO) {
            val response = client.getComments(publication)
            response.body()?.data!!
        }
    }
}
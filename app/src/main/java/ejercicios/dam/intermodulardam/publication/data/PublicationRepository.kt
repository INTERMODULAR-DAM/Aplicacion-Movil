package ejercicios.dam.intermodulardam.publication.data

import ejercicios.dam.intermodulardam.main.domain.entity.Publication
import ejercicios.dam.intermodulardam.rutas.data.network.RutasService
import javax.inject.Inject

class PublicationRepository @Inject constructor(private val rutasService: RutasService) {
    suspend fun getPostByID(id:String): Publication {
        return rutasService.getPostbyID(id)
    }
}
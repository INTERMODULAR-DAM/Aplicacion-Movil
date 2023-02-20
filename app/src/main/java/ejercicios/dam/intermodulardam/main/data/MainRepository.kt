package ejercicios.dam.intermodulardam.main.data

import ejercicios.dam.intermodulardam.login.data.database.dao.UserDAO
import ejercicios.dam.intermodulardam.login.data.database.entity.UserDTO
import ejercicios.dam.intermodulardam.main.domain.entity.CreatePublication
import ejercicios.dam.intermodulardam.main.domain.entity.Publication
import ejercicios.dam.intermodulardam.main.domain.entity.User
import ejercicios.dam.intermodulardam.rutas.data.network.RutasService
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val userDAO: UserDAO,
    private val api: RutasService
) {
    suspend fun getUser(): User {
        val userDTO: UserDTO = userDAO.getAllUsers()[0]
        return User(
            userDTO._id,
            userDTO.email,
            userDTO.name,
            userDTO.lastname,
            userDTO.date,
            userDTO.nick,
            userDTO.admin,
            userDTO.pfp_path,
            userDTO.phone_number,
            userDTO.following
        )
    }

    suspend fun getAllPosts():List<Publication> {
        return api.getAllRoutes()
    }

    suspend fun getAllPublicPosts():List<Publication> {
        return api.getAllPublicRoutes()
    }

    suspend fun createRoute(publication: CreatePublication):Boolean {
        return api.createRoute(publication)
    }
}
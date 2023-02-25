package ejercicios.dam.intermodulardam.main.data

import ejercicios.dam.intermodulardam.login.data.database.dao.UserDAO
import ejercicios.dam.intermodulardam.main.domain.entity.CreatePublication
import ejercicios.dam.intermodulardam.main.domain.entity.Publication
import ejercicios.dam.intermodulardam.main.domain.entity.User
import ejercicios.dam.intermodulardam.main.data.network.RouteService
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val userDAO: UserDAO,
    private val api: RouteService
) {
    suspend fun getUser(): User {
        val userDTO = userDAO.getAllUsers()[0]
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

    suspend fun getPostByID(id:String): Publication {
        return api.getPostbyID(id)
    }

    suspend fun getUserById(user: String): User {
        return api.getUserById(user)
    }

}
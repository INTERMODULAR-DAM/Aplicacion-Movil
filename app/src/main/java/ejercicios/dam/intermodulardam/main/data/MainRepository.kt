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
        val userDTO = userDAO.getAllUsers()
        return User(
            userDTO[userDTO.size-1]._id,
            userDTO[userDTO.size-1].email,
            userDTO[userDTO.size-1].name,
            userDTO[userDTO.size-1].lastname,
            userDTO[userDTO.size-1].date,
            userDTO[userDTO.size-1].nick,
            userDTO[userDTO.size-1].admin,
            userDTO[userDTO.size-1].pfp_path,
            userDTO[userDTO.size-1].phone_number,
            userDTO[userDTO.size-1].following
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
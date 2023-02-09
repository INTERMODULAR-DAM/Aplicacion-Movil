package ejercicios.dam.intermodulardam.login.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ejercicios.dam.intermodulardam.login.data.database.dao.UserDAO
import ejercicios.dam.intermodulardam.login.data.database.entity.UserDTO

@Database(entities = [UserDTO::class], version = 1)
abstract class UserDataBase:RoomDatabase() {
    abstract fun getUserDAO(): UserDAO
}
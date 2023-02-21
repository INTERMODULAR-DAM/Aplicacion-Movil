package ejercicios.dam.intermodulardam.login.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dagger.Provides
import ejercicios.dam.intermodulardam.login.data.database.entity.UserDTO
import javax.inject.Singleton

@Dao
interface UserDAO{
    @Query("SELECT * FROM users ORDER BY name DESC")
    suspend fun getAllUsers(): List<UserDTO>

    @Query("SELECT * FROM users where _id = :id")
    suspend fun getUserByID(id:String):UserDTO

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users : List<UserDTO>)

    @Query("DELETE FROM users")
    suspend fun deleteAllUsers()
}
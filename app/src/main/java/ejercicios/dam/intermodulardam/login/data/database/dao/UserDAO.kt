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
    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<UserDTO>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users : List<UserDTO>)

    @Query("DELETE FROM users")
    suspend fun deleteAllUsers()
}
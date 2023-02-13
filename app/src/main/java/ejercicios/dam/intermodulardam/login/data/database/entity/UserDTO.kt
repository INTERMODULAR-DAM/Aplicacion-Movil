package ejercicios.dam.intermodulardam.login.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserDTO(
    @PrimaryKey()
    @ColumnInfo(name = "email") val email:String,
    @ColumnInfo(name = "name") val name:String,
    @ColumnInfo(name = "lastname") val surname:String,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "nick") val nick:String,
    @ColumnInfo(name = "admin") val admin:Boolean,
    @ColumnInfo(name = "web") val web:String,
    @ColumnInfo(name = "pfp_path") val pfp_path:String,
    @ColumnInfo(name ="phone_number") val phone:String,
)

fun ejercicios.dam.intermodulardam.login.data.network.dto.UserDTO.toDataBase(token:String) =
    UserDTO(
        email = email,
        name = name,
        surname = surname,
        date = date,
        nick = nick,
        admin = admin,
        web = web,
        pfp_path = pfp_path,
        phone = phone,
    )
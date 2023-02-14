package ejercicios.dam.intermodulardam.login.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserDTO(
    @PrimaryKey()
    @ColumnInfo(name = "_id") val _id:String,
    @ColumnInfo(name = "email") val email:String,
    @ColumnInfo(name = "name") val name:String,
    @ColumnInfo(name = "lastname") val lastname:String,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "nick") val nick:String,
    @ColumnInfo(name = "admin") val admin:Boolean,
    @ColumnInfo(name = "pfp_path") val pfp_path:String,
    @ColumnInfo(name = "phone_number") val phone_number:String,
)

fun ejercicios.dam.intermodulardam.login.data.network.dto.UserDTO.toDataBase(token:String) =
    UserDTO(
        _id = _id,
        email = email,
        name = name,
        lastname = lastname,
        date = date,
        nick = nick,
        admin = admin,
        pfp_path = pfp_path,
        phone_number = phone_number,
    )
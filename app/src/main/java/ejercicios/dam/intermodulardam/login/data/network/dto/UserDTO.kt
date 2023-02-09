package ejercicios.dam.intermodulardam.login.data.network.dto


data class UserDTO(
    val id: String,
    val token:String,
    val email:String,
    val name:String,
    val surname:String,
    val date: String,
    val nick:String,
    val admin:Boolean,
    val web:String,
    val pfp_path:String,
    val phone:String,
)

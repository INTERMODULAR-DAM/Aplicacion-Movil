package ejercicios.dam.intermodulardam.login.data.network.dto


data class UserDTO(
    val _id:String,
    val email:String,
    val name:String,
    val lastname:String,
    val date: String,
    val nick:String,
    val admin:Boolean,
    val pfp_path:String,
    val phone_number:String,
    val following:List<String>,
)

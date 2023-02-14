package ejercicios.dam.intermodulardam.main.domain.entity

data class User(
    val id:String,
    val email:String,
    val name:String,
    val lastname:String,
    val date: String,
    val nick:String,
    val admin:Boolean,
    val pfp_path:String,
    val phone_number:String,
)

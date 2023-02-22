package ejercicios.dam.intermodulardam.models

sealed class Routes(val route:String) {
    object Login:Routes("login")
    object Splash:Routes("splash")
    object Registro:Routes("registro")
    object Main:Routes("main")
    object Mapa:Routes("mapa")
    object CrearRuta:Routes("crearRuta")
    object Perfil:Routes("perfil/{id}") {
        fun createRoute(id:String) = "perfil/$id"
    }
    object Publicacion:Routes("publicacion/{id}") {
        fun createRoute(id:String) = "publicacion/$id"
    }

}

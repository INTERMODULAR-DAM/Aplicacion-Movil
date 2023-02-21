package ejercicios.dam.intermodulardam.models

sealed class Routes(val route:String) {
    object Login:Routes("login")
    object Splash:Routes("splash")
    object Registro:Routes("registro")
    object Main:Routes("main/{id}") {
        fun createRoute(id:String) = "main/$id"
    }
    object Mapa:Routes("mapa/{id}") {
        fun createRoute(id:String) = "mapa/$id"
    }
    object CrearRuta:Routes("crearRuta/{id}") {
        fun createRoute(id:String) = "crearRuta/$id"
    }
    object Perfil:Routes("perfil/{id}") {
        fun createRoute(id:String) = "perfil/$id"
    }
    object Publicacion:Routes("publicacion/{id}") {
        fun createRoute(id:String) = "perfil/$id"
    }

}

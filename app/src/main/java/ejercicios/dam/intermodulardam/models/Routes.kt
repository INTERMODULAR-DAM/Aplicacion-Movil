package ejercicios.dam.intermodulardam.models

sealed class Routes(val route:String) {
    object Login:Routes("login")
    object Splash:Routes("splash")
    object Registro:Routes("registro")
    object Main:Routes("main")
    object Mapa:Routes("mapa")
    object CrearRuta:Routes("crearRuta")

    //Pasar el ID de usuario para ver el perfil
    object Perfil:Routes("perfil/{id}") {
        fun createRoute(id:String) = "perfil/$id"
    }
    //Pasar el ID de la publicacion para visualizarla con detalle
    object Publicacion:Routes("publicacion/{id}") {
        fun createRoute(id:String) = "perfil/$id"
    }

    //Pasar ID del usuario para mostrar a quien sigue
    object Siguiendo:Routes("siguiendo/{id}") {
        fun createRoute(id:String) = "siguiendo/$id"
    }
}

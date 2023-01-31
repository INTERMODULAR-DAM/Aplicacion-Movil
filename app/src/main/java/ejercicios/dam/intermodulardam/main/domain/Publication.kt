package ejercicios.dam.intermodulardam.main.domain

import com.google.gson.annotations.SerializedName

data class Publication(
    @SerializedName("name") val name:String
)

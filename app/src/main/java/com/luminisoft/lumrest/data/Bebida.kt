package com.luminisoft.lumrest.data


data class Bebida(
    var id:          String? = null,
    var nombre:      String="",
    var descripcion: String="",
    var mililitros:      Int=0
){
    constructor():this(null,"","",0)
}

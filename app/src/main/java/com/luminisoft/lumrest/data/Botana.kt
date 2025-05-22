package com.luminisoft.lumrest.data

class Botana (
    var id          : String?  = null,
    var nombre      : String   = "",
    var descripcion : String   = "",
    var piezas      : Int      = 0
){
    constructor(): this(null,"","",0)
    fun toAlimento(): Alimento {
        return Alimento(nombre = nombre, descripcion = descripcion, piezas = piezas)
    }
}



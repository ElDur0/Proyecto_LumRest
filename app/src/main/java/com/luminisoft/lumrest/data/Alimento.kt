package com.luminisoft.lumrest.data

data class Alimento(
    var id: String? = null,
    var nombre: String = "",
    var descripcion: String = "",
    var piezas: Int = 0
) {
    constructor() : this(null, "", "", 0)
}

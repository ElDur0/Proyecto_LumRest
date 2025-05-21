package com.luminisoft.lumrest.data

data class Pedido(
    var id: String?         = null,
    var mesa: String        = "",
    var descripcion: String = "",
    var estado: String      = "Pendiente"
) {

    constructor() : this(null, "", "", "Pendiente")
}

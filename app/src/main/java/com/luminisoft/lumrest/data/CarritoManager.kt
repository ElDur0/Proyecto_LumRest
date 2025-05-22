package com.luminisoft.lumrest.data

object CarritoManager {

    private val carrito = mutableListOf<Alimento>()

    fun agregarAlimento(alimento: Alimento) {
        carrito.add(alimento)
    }

    fun agregarBebida(bebida: Bebida) {
        // Convertimos la bebida a un objeto tipo Alimento para almacenarla
        val bebidaComoAlimento = Alimento(
            id = bebida.id,
            nombre = bebida.nombre,
            descripcion = bebida.descripcion,
            piezas = bebida.mililitros
        )
        carrito.add(bebidaComoAlimento)
    }

    fun eliminarAlimento(alimento: Alimento) {
        carrito.remove(alimento)
    }

    fun limpiar() {
        carrito.clear()
    }

    fun obtenerCarrito(): List<Alimento> {
        return carrito.toList()
    }
}

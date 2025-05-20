package com.luminisoft.lumrest.data

object CarritoManager {
    val carrito = mutableListOf<Alimento>()

    fun agregarAlimento(alimento: Alimento) {
        carrito.add(alimento)
    }

    fun eliminarAlimento(alimento: Alimento) {
        carrito.remove(alimento)
    }

    fun limpiar() {
        carrito.clear()
    }

    fun obtenerCarrito(): List<Alimento> {
        return carrito
    }
}

package com.luminisoft.lumrest.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pedidos")
data class Pedido(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val mesa: String,
    val descripcion: String,
    var estado: String = "Pendiente"
)
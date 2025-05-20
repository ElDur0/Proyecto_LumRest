package com.luminisoft.lumrest.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Bebida(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var nombre: String,
    var descripcion: String,
    var piezas: Int
)

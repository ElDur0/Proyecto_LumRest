

package com.luminisoft.lumrest.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alimentos")
data class Alimento(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var nombre: String,
    var descripcion: String,
    var piezas: Int
)

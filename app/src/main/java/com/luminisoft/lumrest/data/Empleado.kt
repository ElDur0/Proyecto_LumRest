package com.luminisoft.lumrest.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "empleados")
data class Empleado(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val puesto: String,
    val horario: String
)

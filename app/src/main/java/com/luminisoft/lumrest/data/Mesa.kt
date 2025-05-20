package com.luminisoft.lumrest.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mesas")
data class Mesa(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val descripcion: String
)
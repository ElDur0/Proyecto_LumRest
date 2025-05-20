package com.luminisoft.lumrest.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Empleado::class, Mesa::class], // Aquí van todas las entidades (tablas)
    version = 2
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun empleadoDao(): EmpleadoDao
    abstract fun mesaDao(): MesaDao
}

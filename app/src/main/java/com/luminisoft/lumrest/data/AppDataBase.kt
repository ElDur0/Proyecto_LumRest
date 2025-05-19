package com.luminisoft.lumrest.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Empleado::class], // Aquí van todas las entidades (tablas)
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun empleadoDao(): EmpleadoDao
}

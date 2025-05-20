package com.luminisoft.lumrest.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Empleado::class, Mesa::class, Pedido::class], // Aquí van todas las entidades (tablas)
    version = 3
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun empleadoDao() : EmpleadoDao
    abstract fun mesaDao()     : MesaDao
    abstract fun pedidoDao()   : PedidoDao
}

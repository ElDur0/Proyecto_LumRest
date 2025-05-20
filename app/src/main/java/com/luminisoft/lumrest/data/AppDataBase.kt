package com.luminisoft.lumrest.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Empleado::class, Mesa::class, Pedido::class, Alimento::class],
    version = 4
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun empleadoDao() : EmpleadoDao
    abstract fun mesaDao()     : MesaDao
    abstract fun pedidoDao()   : PedidoDao
    abstract fun alimentoDao() : AlimentoDao
}

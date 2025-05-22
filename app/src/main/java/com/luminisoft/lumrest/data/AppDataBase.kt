package com.luminisoft.lumrest.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Empleado::class, Mesa::class],
    version = 5
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun empleadoDao() : EmpleadoDao
    abstract fun mesaDao()     : MesaDao
    //abstract fun pedidoDao()   : PedidoDao
    //abstract fun alimentoDao() : AlimentoDao
    //abstract fun bebidaDao()   : BebidaDao
}

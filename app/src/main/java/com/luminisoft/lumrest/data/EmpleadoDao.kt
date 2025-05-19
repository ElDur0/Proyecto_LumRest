package com.luminisoft.lumrest.data

import androidx.room.*

@Dao
interface EmpleadoDao {
    @Query("SELECT * FROM empleados")
    fun getAll(): List<Empleado>

    @Insert
    fun insert(empleado: Empleado)

    @Update
    fun update(empleado: Empleado)

    @Delete
    fun delete(empleado: Empleado)
}

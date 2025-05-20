package com.luminisoft.lumrest.data

import androidx.room.*

@Dao
interface BebidaDao {
    @Query("SELECT * FROM Bebida")
    fun getAll(): List<Bebida>

    @Insert
    fun insert(bebida: Bebida)

    @Update
    fun update(bebida: Bebida)

    @Delete
    fun delete(bebida: Bebida)
}

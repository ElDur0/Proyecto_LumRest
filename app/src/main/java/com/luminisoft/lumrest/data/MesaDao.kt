package com.luminisoft.lumrest.data

import androidx.room.*

@Dao
interface MesaDao {
    @Query("SELECT * FROM mesas")
    fun getAll(): List<Mesa>

    @Insert
    fun insert(mesa: Mesa)

    @Update
    fun update(mesa: Mesa)

    @Delete
    fun delete(mesa: Mesa)
}
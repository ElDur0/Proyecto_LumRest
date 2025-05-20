package com.luminisoft.lumrest.data

import androidx.room.*

@Dao
interface AlimentoDao {
    @Query("SELECT * FROM alimentos")
    fun getAll(): List<Alimento>

    @Insert
    fun insert(alimento: Alimento)

    @Update
    fun update(alimento: Alimento)

    @Delete
    fun delete(alimento: Alimento)
}

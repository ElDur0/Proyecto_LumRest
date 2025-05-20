package com.luminisoft.lumrest.data

import androidx.room.*

@Dao
interface PedidoDao {
    @Query("SELECT * FROM pedidos")
    fun getAll(): List<Pedido>

    @Insert
    fun insert(pedido: Pedido)

    @Update
    fun update(pedido: Pedido)

    @Delete
    fun delete(pedido: Pedido)
}

package com.luminisoft.lumrest

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.luminisoft.lumrest.data.AppDatabase
import com.luminisoft.lumrest.data.CarritoManager
import com.luminisoft.lumrest.data.Pedido
import com.luminisoft.lumrest.data.Alimento

class CarritoActivity : AppCompatActivity() {

    private lateinit var recyclerView:  RecyclerView
    private lateinit var tvTotal:       TextView
    private lateinit var btnConfirmar:  Button
    private lateinit var adapter:       AlimentoCarritoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrito)

        recyclerView        = findViewById(R.id.recyclerCarrito)
        tvTotal             = findViewById(R.id.tvTotalItems)
        btnConfirmar        = findViewById(R.id.btnConfirmarPedido)

        val listaCarrito    = CarritoManager.obtenerCarrito().toMutableList()

        adapter = AlimentoCarritoAdapter(listaCarrito) {
            tvTotal.text = "Total de productos: ${CarritoManager.obtenerCarrito().size}"
        }

        recyclerView.layoutManager  = LinearLayoutManager(this)
        recyclerView.adapter        = adapter
        tvTotal.text                = "Total de productos: ${listaCarrito.size}"

        btnConfirmar.setOnClickListener {
            if (listaCarrito.isEmpty()) {
                Toast.makeText(this, "El carrito está vacío", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val descripcionUnica = listaCarrito.joinToString(separator = "\n") {
                "- ${it.nombre} (${it.piezas} pzas): ${it.descripcion}"
            }

            val pedido = Pedido(
                mesa = "Mesa virtual",
                descripcion = descripcionUnica,
                estado = "Pendiente"
            )

            val db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java,
                "empleados-db"
            ).fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()

            db.pedidoDao().insert(pedido)

            CarritoManager.limpiar()
            Toast.makeText(this, "Pedido enviado a cocina", Toast.LENGTH_LONG).show()
            finish()
        }
    }
}

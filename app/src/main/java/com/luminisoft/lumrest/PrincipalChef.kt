package com.luminisoft.lumrest

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.luminisoft.lumrest.data.AppDatabase
import com.luminisoft.lumrest.data.Pedido

class PrincipalChef : AppCompatActivity() {

    private lateinit var pedidoDao: com.luminisoft.lumrest.data.PedidoDao
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PedidoAdapter

    private fun cargarPedidos() {
        val pedidos = pedidoDao.getAll()
        adapter = PedidoAdapter(pedidos, pedidoDao) {
            cargarPedidos() // se actualiza al cambiar estado
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal_chef)

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "empleados-db"
        ).fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()

        pedidoDao = db.pedidoDao()
        recyclerView = findViewById(R.id.recyclerPedidos)

        val btnAgregarPedido = findViewById<ImageView>(R.id.btnAgregarPedido)
        btnAgregarPedido.setOnClickListener {
            mostrarDialogNuevoPedido()
        }

        cargarPedidos()
    }

    private fun mostrarDialogNuevoPedido() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_agregar_pedido, null)
        val dialog = AlertDialog.Builder(this)
            .setTitle("Nuevo Pedido")
            .setView(dialogView)
            .create()

        val etMesa = dialogView.findViewById<EditText>(R.id.etMesa)
        val etDescripcion = dialogView.findViewById<EditText>(R.id.etDescripcion)
        val btnGuardar = dialogView.findViewById<Button>(R.id.btnGuardarPedido)

        btnGuardar.setOnClickListener {
            val mesa = etMesa.text.toString().trim()
            val descripcion = etDescripcion.text.toString().trim()

            if (mesa.isEmpty() || descripcion.isEmpty()) {
                Toast.makeText(this, "Llena todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                val nuevoPedido = Pedido(mesa = mesa, descripcion = descripcion)
                pedidoDao.insert(nuevoPedido)
                Toast.makeText(this, "Pedido agregado", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
                cargarPedidos()
            }
        }

        dialog.show()
    }
}

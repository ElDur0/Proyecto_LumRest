package com.luminisoft.lumrest

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.luminisoft.lumrest.data.CarritoManager

class CarritoActivity : AppCompatActivity() {

    private lateinit var recyclerView    : RecyclerView
    private lateinit var adapter         : AlimentoCarritoAdapter
    private lateinit var btnEnviarPedido : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrito)

        recyclerView               = findViewById(R.id.recyclerCarrito)
        btnEnviarPedido            = findViewById(R.id.btnConfirmarPedido)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter                    = AlimentoCarritoAdapter(CarritoManager.obtenerCarrito().toMutableList()) { alimento ->
            CarritoManager.eliminarAlimento(alimento)
                actualizarVista()
        }
        recyclerView.adapter       = adapter

        btnEnviarPedido.setOnClickListener {
            enviarPedidoAFirebase()
        }
    }

    private fun actualizarVista() {
        adapter             = AlimentoCarritoAdapter(CarritoManager.obtenerCarrito().toMutableList()) { alimento ->
            CarritoManager.eliminarAlimento(alimento)
            actualizarVista()
        }
        recyclerView.adapter = adapter
    }

    private fun enviarPedidoAFirebase() {
        val carrito     = CarritoManager.obtenerCarrito()
        if (carrito.isEmpty()) {
            Toast.makeText(this, "El carrito está vacío", Toast.LENGTH_SHORT).show()
            return
        }

        val descripcion = carrito.joinToString(separator = "\n") {
            "- ${it.nombre} (${it.piezas} pzas): ${it.descripcion}"
        }

        val pedido = hashMapOf(
            "mesa"        to "Mesa virtual",
            "descripcion" to descripcion,
            "estado"      to "Pendiente",
            "timestamp"   to FieldValue.serverTimestamp()
        )

        Firebase.firestore.collection("pedidos")
            .add(pedido)
            .addOnSuccessListener {
                Toast.makeText(this, "Pedido enviado a cocina", Toast.LENGTH_SHORT).show()
                CarritoManager.limpiar()
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al enviar el pedido", Toast.LENGTH_SHORT).show()
            }
    }
}

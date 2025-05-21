package com.luminisoft.lumrest

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.luminisoft.lumrest.data.Pedido

class PrincipalChef : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PedidoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal_chef)

        recyclerView = findViewById(R.id.recyclerPedidos)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val btnAgregarPedido = findViewById<ImageView>(R.id.btnAgregarPedido)
        btnAgregarPedido.setOnClickListener {
            mostrarDialogNuevoPedido()
        }

        cargarPedidosDesdeFirestore()
    }

    private fun cargarPedidosDesdeFirestore() {
        val db = Firebase.firestore

        db.collection("pedidos")
            .orderBy("timestamp")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Toast.makeText(this, "Error al cargar pedidos", Toast.LENGTH_SHORT).show()
                    return@addSnapshotListener
                }

                if (snapshot != null && !snapshot.isEmpty) {
                    val listaPedidos = snapshot.documents.mapNotNull { doc ->
                        val mesa = doc.getString("mesa") ?: return@mapNotNull null
                        val descripcion = doc.getString("descripcion") ?: ""
                        val estado = doc.getString("estado") ?: "Pendiente"
                        val id = doc.id

                        Pedido(id = id, mesa = mesa, descripcion = descripcion, estado = estado)
                    }

                    adapter = PedidoAdapter(listaPedidos) { pedido, nuevoEstado ->
                        actualizarEstadoPedido(pedido.id!!, nuevoEstado)
                    }

                    recyclerView.adapter = adapter
                }
            }
    }

    private fun actualizarEstadoPedido(documentId: String, nuevoEstado: String) {
        Firebase.firestore.collection("pedidos")
            .document(documentId)
            .update("estado", nuevoEstado)
            .addOnSuccessListener {
                Toast.makeText(this, "Estado actualizado a $nuevoEstado", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al actualizar estado", Toast.LENGTH_SHORT).show()
            }
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
                val nuevoPedido = hashMapOf(
                    "mesa" to mesa,
                    "descripcion" to descripcion,
                    "estado" to "Pendiente",
                    "timestamp" to com.google.firebase.firestore.FieldValue.serverTimestamp()
                )

                Firebase.firestore.collection("pedidos")
                    .add(nuevoPedido)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Pedido agregado", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Error al guardar pedido", Toast.LENGTH_SHORT).show()
                    }
            }
        }

        dialog.show()
    }
}

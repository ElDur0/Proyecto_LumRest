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
import com.luminisoft.lumrest.data.Mesa

class Mesas : AppCompatActivity() {

    private lateinit var recyclerView   : RecyclerView
    private lateinit var adapter        : MesaAdapter

    private val db              = Firebase.firestore
    private val mesasCollection = db.collection("mesas")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mesas)

        recyclerView               = findViewById(R.id.recyclerMesas)
        recyclerView.layoutManager = LinearLayoutManager(this)

        findViewById<ImageView>(R.id.btnAgregarMesa).setOnClickListener {
            mostrarDialogoAgregarMesa()
        }

        cargarMesas()
    }

    private fun cargarMesas() {
        mesasCollection.orderBy("nombre").addSnapshotListener { snapshot, error ->
            if (error != null) {
                Toast.makeText(this, "Error al cargar mesas", Toast.LENGTH_SHORT).show()
                return@addSnapshotListener
            }

            val lista = snapshot?.documents?.mapNotNull { doc ->
                val nombre      = doc.getString("nombre")      ?: return@mapNotNull null
                val descripcion = doc.getString("descripcion") ?: ""
                Mesa(
                    id          = doc.id,
                    nombre      = nombre,
                    descripcion = descripcion
                )
            } ?: emptyList()

            adapter = MesaAdapter(lista,
                onEditar    = { mesa -> mostrarDialogoEditarMesa(mesa) },
                onEliminar  = { mesa -> eliminarMesa(mesa)             }
            )
            recyclerView.adapter = adapter
        }
    }

    private fun mostrarDialogoAgregarMesa() {
        val vista   = LayoutInflater.from(this).inflate(R.layout.dialog_agregar_mesa, null)
        val dialogo = AlertDialog.Builder(this)
            .setTitle("Agregar Mesa")
            .setView(vista)
            .create()

        val etNombre      = vista.findViewById<EditText>(R.id.etNombreMesa)
        val etDescripcion = vista.findViewById<EditText>(R.id.etDescripcion)
        val btnGuardar    = vista.findViewById<Button>(R.id.btnGuardarMesa)

        btnGuardar.setOnClickListener {
            val nombre      = etNombre.text.toString().trim()
            val descripcion = etDescripcion.text.toString().trim()

            if (nombre.isEmpty() || descripcion.isEmpty()) {
                Toast.makeText(this, "Llena todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                val nuevaMesa = hashMapOf(
                    "nombre"      to nombre,
                    "descripcion" to descripcion
                )
                mesasCollection.add(nuevaMesa)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Mesa agregada", Toast.LENGTH_SHORT).show()
                        dialogo.dismiss()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Error al agregar mesa", Toast.LENGTH_SHORT).show()
                    }
            }
        }

        dialogo.show()
    }

    private fun mostrarDialogoEditarMesa(mesa: Mesa) {
        val vista   = LayoutInflater .from(this).inflate(R.layout.dialog_agregar_mesa, null)
        val dialogo = AlertDialog    .Builder(this)
            .setTitle("Editar Mesa")
            .setView(vista)
            .create()

        val etNombre      = vista.findViewById<EditText>(R.id.etNombreMesa)
        val etDescripcion = vista.findViewById<EditText>(R.id.etDescripcion)
        val btnGuardar    = vista.findViewById<Button>(R.id.btnGuardarMesa)

        etNombre      .setText(mesa.nombre)
        etDescripcion .setText(mesa.descripcion)
        btnGuardar    .setOnClickListener {

            val nuevoNombre      = etNombre.text.toString().trim()
            val nuevaDescripcion = etDescripcion.text.toString().trim()

            if (nuevoNombre.isEmpty() || nuevaDescripcion.isEmpty()) {
                Toast.makeText(this, "Llena todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                val mesaActualizada = hashMapOf(
                    "nombre"      to nuevoNombre,
                    "descripcion" to nuevaDescripcion
                )
                mesa.id?.let {
                    mesasCollection.document(it).set(mesaActualizada)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Mesa actualizada", Toast.LENGTH_SHORT).show()
                            dialogo.dismiss()
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "Error al actualizar mesa", Toast.LENGTH_SHORT).show()
                        }
                }
            }
        }

        dialogo.show()
    }

    private fun eliminarMesa(mesa: Mesa) {
        mesa.id?.let {
            mesasCollection.document(it).delete()
                .addOnSuccessListener {
                    Toast.makeText(this, "Mesa eliminada", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error al eliminar mesa", Toast.LENGTH_SHORT).show()
                }
        }
    }
}

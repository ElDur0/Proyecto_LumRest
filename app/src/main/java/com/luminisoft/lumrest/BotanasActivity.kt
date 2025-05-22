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
import com.luminisoft.lumrest.data.Botana

class BotanasActivity : AppCompatActivity() {

    private lateinit var recyclerView   : RecyclerView
    private lateinit var adapter        : BotanaAdapter

    private val db                = Firebase.firestore
    private val botanasCollection = db.collection("botanas")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_botanas)

        recyclerView               = findViewById(R.id.recyclerBotanas)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val btnAgregar = findViewById<ImageView>(R.id.btnAgregarBotana)
        btnAgregar.setOnClickListener {
            mostrarDialogoAgregar()
        }

        cargarBotanas()
    }

    private fun cargarBotanas() {
        botanasCollection.orderBy("nombre").addSnapshotListener { snapshot, error ->
            if (error != null) {
                Toast.makeText(this, "Error al cargar botanas", Toast.LENGTH_SHORT).show()
                return@addSnapshotListener
            }

            val lista = snapshot?.documents?.mapNotNull { doc ->
                val nombre      = doc.getString("nombre") ?: return@mapNotNull null
                val descripcion = doc.getString("descripcion") ?: ""
                val piezas      = (doc.getLong("piezas") ?: 0).toInt()
                Botana( id = doc.id,
                        nombre      = nombre,
                        descripcion = descripcion,
                        piezas      = piezas
                )
            } ?: emptyList()

            adapter = BotanaAdapter(lista,
                onEditar    = { mostrarDialogoEditar(it) },
                onEliminar  = { eliminarBotana(it)       }
            )
            recyclerView.adapter = adapter
        }
    }

    private fun mostrarDialogoAgregar() {
        val vista   = LayoutInflater.from(this).inflate(R.layout.dialog_agregar_botana, null)
        val dialogo = AlertDialog.Builder(this)
            .setTitle("Agregar Botana")
            .setView(vista)
            .create()

        val etNombre      = vista.findViewById<EditText>(R.id.etNombre)
        val etDescripcion = vista.findViewById<EditText>(R.id.etDescripcion)
        val etPiezas      = vista.findViewById<EditText>(R.id.etPiezas)
        val btnGuardar    = vista.findViewById<Button>(R.id.btnGuardarBotana)

        btnGuardar.setOnClickListener {
            val nombre      = etNombre     .text.toString().trim()
            val descripcion = etDescripcion.text.toString().trim()
            val piezas      = etPiezas     .text.toString().toIntOrNull() ?: 0

            if (nombre.isEmpty() || descripcion.isEmpty() || piezas <= 0) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                val nueva = hashMapOf(
                    "nombre"        to nombre,
                    "descripcion"   to descripcion,
                    "piezas"        to piezas
                )
                botanasCollection.add(nueva)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Botana agregada", Toast.LENGTH_SHORT).show()
                        dialogo.dismiss()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Error al guardar", Toast.LENGTH_SHORT).show()
                    }
            }
        }

        dialogo.show()
    }

    private fun mostrarDialogoEditar(botana: Botana) {
        val vista   = LayoutInflater.from(this).inflate(R.layout.dialog_agregar_botana, null)
        val dialogo = AlertDialog.Builder(this)
            .setTitle("Editar Botana")
            .setView(vista)
            .create()

        val etNombre      = vista.findViewById<EditText>(R.id.etNombre)
        val etDescripcion = vista.findViewById<EditText>(R.id.etDescripcion)
        val etPiezas      = vista.findViewById<EditText>(R.id.etPiezas)
        val btnGuardar    = vista.findViewById<Button>(R.id.btnGuardarBotana)

        etNombre        .setText(botana.nombre)
        etDescripcion   .setText(botana.descripcion)
        etPiezas        .setText(botana.piezas.toString())

        btnGuardar.setOnClickListener {
            val nombre      = etNombre     .text.toString().trim()
            val descripcion = etDescripcion.text.toString().trim()
            val piezas      = etPiezas     .text.toString().toIntOrNull() ?: 0

            if (nombre.isEmpty() || descripcion.isEmpty() || piezas <= 0) {
                Toast.makeText(this, "Campos inválidos", Toast.LENGTH_SHORT).show()
            } else {
                val actualizada = hashMapOf(
                    "nombre"      to nombre,
                    "descripcion" to descripcion,
                    "piezas"      to piezas
                )
                botana.id?.let {
                    botanasCollection.document(it).set(actualizada)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Actualizada", Toast.LENGTH_SHORT).show()
                            dialogo.dismiss()
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "Error al actualizar", Toast.LENGTH_SHORT).show()
                        }
                }
            }
        }

        dialogo.show()
    }

    private fun eliminarBotana(botana: Botana) {
        botana.id?.let {
            botanasCollection.document(it).delete()
                .addOnSuccessListener {
                    Toast.makeText(this, "Eliminada", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error al eliminar", Toast.LENGTH_SHORT).show()
                }
        }
    }
}

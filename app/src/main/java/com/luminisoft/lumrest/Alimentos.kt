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
import com.luminisoft.lumrest.data.Alimento

class Alimentos : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AlimentoAdapter
    private val db = Firebase.firestore
    private val alimentos = mutableListOf<Alimento>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alimentos)

        recyclerView = findViewById(R.id.recyclerAlimentos)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = AlimentoAdapter(alimentos) { alimento ->
            mostrarDialogEditarAlimento(alimento)
        }

        recyclerView.adapter = adapter

        findViewById<ImageView>(R.id.btnAgregarAlimento).setOnClickListener {
            mostrarDialogAgregarAlimento()
        }

        cargarAlimentosDesdeFirebase()
    }

    private fun cargarAlimentosDesdeFirebase() {
        db.collection("alimentos")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Toast.makeText(this, "Error al cargar alimentos", Toast.LENGTH_SHORT).show()
                    return@addSnapshotListener
                }

                alimentos.clear()
                for (doc in snapshot!!) {
                    val alimento = doc.toObject(Alimento::class.java)
                    alimento.id = doc.id
                    alimentos.add(alimento)
                }
                adapter.actualizarLista(alimentos)
            }
    }

    private fun mostrarDialogAgregarAlimento() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_agregar_alimento, null)
        val etNombre = dialogView.findViewById<EditText>(R.id.etNombre)
        val etDescripcion = dialogView.findViewById<EditText>(R.id.etDescripcion)
        val etPiezas = dialogView.findViewById<EditText>(R.id.etPiezas)

        AlertDialog.Builder(this)
            .setTitle("Agregar alimento")
            .setView(dialogView)
            .setPositiveButton("Guardar") { dialog, _ ->
                val nombre = etNombre.text.toString().trim()
                val descripcion = etDescripcion.text.toString().trim()
                val piezas = etPiezas.text.toString().toIntOrNull() ?: 0

                if (nombre.isNotEmpty()) {
                    val nuevoAlimento = hashMapOf(
                        "nombre" to nombre,
                        "descripcion" to descripcion,
                        "piezas" to piezas
                    )
                    db.collection("alimentos").add(nuevoAlimento)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Alimento agregado", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                }

                dialog.dismiss()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun mostrarDialogEditarAlimento(alimento: Alimento) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_agregar_alimento, null)
        val etNombre = dialogView.findViewById<EditText>(R.id.etNombre)
        val etDescripcion = dialogView.findViewById<EditText>(R.id.etDescripcion)
        val etPiezas = dialogView.findViewById<EditText>(R.id.etPiezas)

        etNombre.setText(alimento.nombre)
        etDescripcion.setText(alimento.descripcion)
        etPiezas.setText(alimento.piezas.toString())

        AlertDialog.Builder(this)
            .setTitle("Editar alimento")
            .setView(dialogView)
            .setPositiveButton("Actualizar") { dialog, _ ->
                val nombre = etNombre.text.toString().trim()
                val descripcion = etDescripcion.text.toString().trim()
                val piezas = etPiezas.text.toString().toIntOrNull() ?: 0

                if (alimento.id != null) {
                    val datosActualizados = mapOf(
                        "nombre" to nombre,
                        "descripcion" to descripcion,
                        "piezas" to piezas
                    )
                    db.collection("alimentos").document(alimento.id!!)
                        .update(datosActualizados)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Alimento actualizado", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "Error al actualizar", Toast.LENGTH_SHORT).show()
                        }
                }

                dialog.dismiss()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
}

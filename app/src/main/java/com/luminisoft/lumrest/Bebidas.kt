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
import com.luminisoft.lumrest.data.Bebida

class Bebidas : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: BebidaAdapter
    private val db = Firebase.firestore
    private val bebidasCollection = db.collection("bebidas")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bebidas)

        recyclerView = findViewById(R.id.recyclerBebidas)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val btnAgregarBebida = findViewById<ImageView>(R.id.btnAgregarBebida)
        btnAgregarBebida.setOnClickListener {
            mostrarDialogoAgregarBebida()
        }

        cargarBebidas()
    }

    private fun cargarBebidas() {
        bebidasCollection.orderBy("nombre").addSnapshotListener { snapshot, error ->
            if (error != null) {
                Toast.makeText(this, "Error al cargar bebidas", Toast.LENGTH_SHORT).show()
                return@addSnapshotListener
            }

            val lista = snapshot?.documents?.mapNotNull { doc ->
                val nombre = doc.getString("nombre") ?: return@mapNotNull null
                val descripcion = doc.getString("descripcion") ?: ""
                val mililitros = when (val valor = doc.get("mililitros")) {
                    is Long -> valor.toInt() // Firestore guarda números enteros como Long
                    is Double -> valor.toInt() // Si llegara como Double, también lo puedes castear
                    is String -> valor.toIntOrNull() ?: 0 // Si está como string numérico
                    else -> 0
                }

                Bebida(id = doc.id, nombre = nombre, descripcion = descripcion, mililitros = mililitros)
            } ?: emptyList()

            adapter = BebidaAdapter(lista,
                onEditar = { bebida -> mostrarDialogoEditarBebida(bebida) },
                onEliminar = { bebida -> eliminarBebida(bebida) }
            )

            recyclerView.adapter = adapter
        }
    }

    private fun mostrarDialogoAgregarBebida() {
        val vista = LayoutInflater.from(this).inflate(R.layout.dialog_agregar_bebida, null)
        val dialogo = AlertDialog.Builder(this)
            .setTitle("Agregar Bebida")
            .setView(vista)
            .create()

        val etNombre = vista.findViewById<EditText>(R.id.etNombre)
        val etDescripcion = vista.findViewById<EditText>(R.id.etDescripcion)
        val etMililitros = vista.findViewById<EditText>(R.id.etMililitros)
        val btnGuardar = vista.findViewById<Button>(R.id.btnGuardarBebida)

        btnGuardar.setOnClickListener {
            val nombre = etNombre.text.toString().trim()
            val descripcion = etDescripcion.text.toString().trim()
            val volumen = etMililitros.text.toString().trim()

            if (nombre.isEmpty() || descripcion.isEmpty() || volumen.isEmpty()) {
                Toast.makeText(this, "Llena todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                val nuevaBebida = hashMapOf(
                    "nombre" to nombre,
                    "descripcion" to descripcion,
                    "volumen" to volumen
                )
                bebidasCollection.add(nuevaBebida)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Bebida agregada", Toast.LENGTH_SHORT).show()
                        dialogo.dismiss()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Error al agregar", Toast.LENGTH_SHORT).show()
                    }
            }
        }

        dialogo.show()
    }

    private fun mostrarDialogoEditarBebida(bebida: Bebida) {
        val vista = LayoutInflater.from(this).inflate(R.layout.dialog_agregar_bebida, null)
        val dialogo = AlertDialog.Builder(this)
            .setTitle("Editar Bebida")
            .setView(vista)
            .create()

        val etNombre = vista.findViewById<EditText>(R.id.etNombre)
        val etDescripcion = vista.findViewById<EditText>(R.id.etDescripcion)
        val etVolumen = vista.findViewById<EditText>(R.id.etMililitros)
        val btnGuardar = vista.findViewById<Button>(R.id.btnGuardarBebida)

        etNombre.setText(bebida.nombre)
        etDescripcion.setText(bebida.descripcion)
        etVolumen.setText(bebida.mililitros.toString())

        btnGuardar.setOnClickListener {
            val nuevoNombre = etNombre.text.toString().trim()
            val nuevaDescripcion = etDescripcion.text.toString().trim()
            val nuevoVolumen = etVolumen.text.toString().trim()

            if (nuevoNombre.isEmpty() || nuevaDescripcion.isEmpty() || nuevoVolumen.isEmpty()) {
                Toast.makeText(this, "Llena todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                val bebidaActualizada = hashMapOf(
                    "nombre" to nuevoNombre,
                    "descripcion" to nuevaDescripcion,
                    "volumen" to nuevoVolumen
                )
                bebida.id?.let {
                    bebidasCollection.document(it).set(bebidaActualizada)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Bebida actualizada", Toast.LENGTH_SHORT).show()
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

    private fun eliminarBebida(bebida: Bebida) {
        bebida.id?.let {
            bebidasCollection.document(it).delete()
                .addOnSuccessListener {
                    Toast.makeText(this, "Bebida eliminada", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error al eliminar", Toast.LENGTH_SHORT).show()
                }
        }
    }
}

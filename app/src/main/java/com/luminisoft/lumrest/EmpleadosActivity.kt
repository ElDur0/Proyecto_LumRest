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
import com.luminisoft.lumrest.data.Empleado

class EmpleadosActivity : AppCompatActivity() {

    private lateinit var recyclerView   : RecyclerView
    private lateinit var adapter        : EmpleadoAdapter
    private val db                  = Firebase.firestore
    private val empleadosCollection = db.collection("empleados")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empleados)

        recyclerView               = findViewById(R.id.recyclerEmpleados)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val btnAgregar             = findViewById<ImageView>(R.id.btnAgregarEmpleado)

        btnAgregar.setOnClickListener {
            mostrarDialogoAgregarEmpleado()
        }

        cargarEmpleados()
    }

    private fun cargarEmpleados() {
        empleadosCollection.orderBy("nombre").addSnapshotListener { snapshot, error ->
            if (error != null) {
                Toast.makeText(this, "Error al cargar empleados", Toast.LENGTH_SHORT).show()
                return@addSnapshotListener
            }

            val lista = snapshot?.documents?.mapNotNull { doc ->
                val nombre  = doc.getString("nombre") ?: return@mapNotNull null
                val puesto  = doc.getString("puesto") ?: ""
                val horario = doc.getString("horario") ?: ""
                Empleado(   id     = doc.id,
                            nombre = nombre,
                            puesto = puesto,
                            horario = horario
                )
            } ?: emptyList()

            adapter = EmpleadoAdapter(lista,
                onEditar    = { mostrarDialogoEditarEmpleado(it) },
                onEliminar  = { eliminarEmpleado(it)             }
            )

            recyclerView.adapter = adapter
        }
    }

    private fun mostrarDialogoAgregarEmpleado() {
        val vista   = LayoutInflater.from(this).inflate(R.layout.dialog_agregar_empleado, null)
        val dialogo = AlertDialog.Builder(this)
            .setTitle("Agregar Empleado")
            .setView(vista)
            .create()

        val etNombre    = vista.findViewById<EditText>(R.id.etNombre)
        val etPuesto    = vista.findViewById<EditText>(R.id.etPuesto)
        val etHorario   = vista.findViewById<EditText>(R.id.etHorario)
        val btnGuardar  = vista.findViewById<Button>(R.id.btnGuardarEmpleado)

        btnGuardar.setOnClickListener {
            val nombre  = etNombre.text.toString().trim()
            val puesto  = etPuesto.text.toString().trim()
            val horario = etHorario.text.toString().trim()

            if (nombre.isEmpty() || puesto.isEmpty() || horario.isEmpty()) {
                Toast.makeText(this, "Llena todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                val nuevoEmpleado = hashMapOf(
                    "nombre"  to nombre,
                    "puesto"  to puesto,
                    "horario" to horario
                )
                empleadosCollection.add(nuevoEmpleado)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Empleado agregado", Toast.LENGTH_SHORT).show()
                        dialogo.dismiss()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Error al agregar", Toast.LENGTH_SHORT).show()
                    }
            }
        }

        dialogo.show()
    }

    private fun mostrarDialogoEditarEmpleado(empleado: Empleado) {
        val vista   = LayoutInflater.from(this).inflate(R.layout.dialog_agregar_empleado, null)
        val dialogo = AlertDialog.Builder(this)
            .setTitle("Editar Empleado")
            .setView(vista)
            .create()

        val etNombre    = vista.findViewById<EditText>(R.id.etNombre)
        val etPuesto    = vista.findViewById<EditText>(R.id.etPuesto)
        val etHorario   = vista.findViewById<EditText>(R.id.etHorario)
        val btnGuardar  = vista.findViewById<Button>(R.id.btnGuardarEmpleado)

        etNombre    .setText(empleado.nombre)
        etPuesto    .setText(empleado.puesto)
        etHorario   .setText(empleado.horario)
        btnGuardar  .setOnClickListener {
            val nuevoNombre     = etNombre  .text.toString().trim()
            val nuevoPuesto     = etPuesto  .text.toString().trim()
            val nuevoHorario    = etHorario .text.toString().trim()

            if (nuevoNombre.isEmpty() || nuevoPuesto.isEmpty() || nuevoHorario.isEmpty()) {
                Toast.makeText(this, "Llena todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                val datosActualizados = hashMapOf(
                    "nombre"  to nuevoNombre,
                    "puesto"  to nuevoPuesto,
                    "horario" to nuevoHorario
                )
                empleado.id?.let {
                    empleadosCollection.document(it).set(datosActualizados)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Empleado actualizado", Toast.LENGTH_SHORT).show()
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

    private fun eliminarEmpleado(empleado: Empleado) {
        empleado.id?.let {
            empleadosCollection.document(it).delete()
                .addOnSuccessListener {
                    Toast.makeText(this, "Empleado eliminado", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error al eliminar", Toast.LENGTH_SHORT).show()
                }
        }
    }
}

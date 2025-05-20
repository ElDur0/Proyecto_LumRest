package com.luminisoft.lumrest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import androidx.room.Room
import com.luminisoft.lumrest.data.AppDatabase
import com.luminisoft.lumrest.data.Empleado

class EmpleadosActivity : AppCompatActivity() {

    // Referencia al DAO
    private lateinit var empleadoDao:   com.luminisoft.lumrest.data.EmpleadoDao
    private lateinit var recyclerView:  RecyclerView
    private lateinit var adapter:       EmpleadoAdapter

    private fun cargarListaEmpleados(){
        val empleados              = empleadoDao.getAll()
        Toast.makeText(this, "Total empleados: ${empleados.size}", Toast.LENGTH_SHORT).show()
        adapter                    = EmpleadoAdapter(empleados)
        recyclerView.adapter       = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            setContentView(R.layout.activity_empleados)

            val db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java,
                "empleados-db"
            ).allowMainThreadQueries().build()

            empleadoDao  = db.empleadoDao()
            recyclerView = findViewById(R.id.recyclerEmpleados)

            cargarListaEmpleados()

            val btnAgregarEmpleado = findViewById<View>(R.id.btnAgregarEmpleado)
            btnAgregarEmpleado.setOnClickListener {
                mostrarDialogAgregarEmpleado()
            }

        } catch (e: Exception) {
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }


    private fun mostrarDialogAgregarEmpleado() {
        val dialogView  = LayoutInflater.from(this).inflate(R.layout.dialog_agregar_empleado, null)
        val dialog      = AlertDialog.Builder(this)
            .setTitle("Agregar Empleado")
            .setView(dialogView)
            .create()

        val etNombre    = dialogView.findViewById<EditText>(R.id.etNombre)
        val etPuesto    = dialogView.findViewById<EditText>(R.id.etPuesto)
        val etHorario   = dialogView.findViewById<EditText>(R.id.etHorario)
        val btnGuardar  = dialogView.findViewById<Button>(R.id.btnGuardarEmpleado)

        btnGuardar.setOnClickListener {
            val nombre  = etNombre.text.toString().trim()
            val puesto  = etPuesto.text.toString().trim()
            val horario = etHorario.text.toString().trim()

            if (nombre.isEmpty() || puesto.isEmpty() || horario.isEmpty()) {
                Toast.makeText(this, "Llena todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                val nuevoEmpleado = Empleado(nombre = nombre, puesto = puesto, horario = horario)
                empleadoDao.insert(nuevoEmpleado)
                Toast.makeText(this, "Empleado guardado correctamente", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
                cargarListaEmpleados()
            }
        }

        dialog.show()

    }
}

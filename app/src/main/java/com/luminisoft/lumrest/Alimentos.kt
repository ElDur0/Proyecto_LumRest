package com.luminisoft.lumrest

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.luminisoft.lumrest.data.Alimento
import com.luminisoft.lumrest.data.AppDatabase

class Alimentos : AppCompatActivity() {

    private lateinit var alimentoDao: com.luminisoft.lumrest.data.AlimentoDao
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AlimentoAdapter

    private fun cargarAlimentos() {
        val alimentos = alimentoDao.getAll()
        adapter = AlimentoAdapter(alimentos, alimentoDao) {
            cargarAlimentos()
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alimentos)

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "empleados-db"
        ).fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()

        alimentoDao = db.alimentoDao()
        recyclerView = findViewById(R.id.recyclerAlimentos)

        val btnAgregar = findViewById<Button>(R.id.btnAgregarAlimento)
        btnAgregar.setOnClickListener {
            mostrarDialogAgregarAlimento()
        }

        cargarAlimentos()
    }

    private fun mostrarDialogAgregarAlimento() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_agregar_alimento, null)
        val dialog = AlertDialog.Builder(this)
            .setTitle("Agregar Alimento")
            .setView(dialogView)
            .create()

        val etNombre = dialogView.findViewById<EditText>(R.id.etNombreAlimento)
        val etDescripcion = dialogView.findViewById<EditText>(R.id.etDescripcionAlimento)
        val etPiezas = dialogView.findViewById<EditText>(R.id.etPiezas)
        val btnGuardar = dialogView.findViewById<Button>(R.id.btnGuardarAlimento)

        btnGuardar.setOnClickListener {
            val nombre = etNombre.text.toString().trim()
            val descripcion = etDescripcion.text.toString().trim()
            val piezas = etPiezas.text.toString().trim().toIntOrNull() ?: 0

            if (nombre.isEmpty() || descripcion.isEmpty()) {
                Toast.makeText(this, "Llena todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                val alimento = Alimento(nombre = nombre, descripcion = descripcion, piezas = piezas)
                alimentoDao.insert(alimento)
                Toast.makeText(this, "Alimento agregado", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
                cargarAlimentos()
            }
        }

        dialog.show()
    }
}

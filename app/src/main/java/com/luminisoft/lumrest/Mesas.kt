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
import androidx.room.Room
import com.luminisoft.lumrest.data.AppDatabase
import com.luminisoft.lumrest.data.Mesa

class Mesas : AppCompatActivity() {

    private lateinit var mesaDao: com.luminisoft.lumrest.data.MesaDao
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MesaAdapter

    private fun cargarListaMesas() {
        val mesas = mesaDao.getAll()
        adapter = MesaAdapter(mesas)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            setContentView(R.layout.activity_mesas)

            val db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java,
                "empleados-db" // Usa la misma DB
            ).fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()

            mesaDao = db.mesaDao()

            recyclerView = findViewById(R.id.recyclerMesas)
            cargarListaMesas()

            val btnAgregar = findViewById<View>(R.id.btnAgregarMesa)
            btnAgregar.setOnClickListener {
                mostrarDialogAgregarMesa()
            }

        } catch (e: Exception) {
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun mostrarDialogAgregarMesa() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_agregar_mesa, null)
        val dialog = AlertDialog.Builder(this)
            .setTitle("Agregar Mesa")
            .setView(dialogView)
            .create()

        val etNombre = dialogView.findViewById<EditText>(R.id.etNombreMesa)
        val etDescripcion = dialogView.findViewById<EditText>(R.id.etDescripcionMesa)
        val btnGuardar = dialogView.findViewById<Button>(R.id.btnGuardarMesa)

        btnGuardar.setOnClickListener {
            val nombre = etNombre.text.toString().trim()
            val descripcion = etDescripcion.text.toString().trim()

            if (nombre.isEmpty() || descripcion.isEmpty()) {
                Toast.makeText(this, "Llena todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                val nuevaMesa = Mesa(nombre = nombre, descripcion = descripcion)
                mesaDao.insert(nuevaMesa)
                Toast.makeText(this, "Mesa guardada correctamente", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
                cargarListaMesas()
            }
        }

        dialog.show()
    }
}

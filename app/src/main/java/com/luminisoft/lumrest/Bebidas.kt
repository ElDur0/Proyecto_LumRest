package com.luminisoft.lumrest

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.luminisoft.lumrest.data.AppDatabase
import com.luminisoft.lumrest.data.Bebida

class Bebidas : AppCompatActivity() {

    private lateinit var bebidaDao: com.luminisoft.lumrest.data.BebidaDao
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: BebidaAdapter

    private fun cargarBebidas() {
        val bebidas = bebidaDao.getAll()
        adapter = BebidaAdapter(bebidas, bebidaDao) {
            cargarBebidas()
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bebidas)

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "empleados-db"
        ).fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()

        bebidaDao = db.bebidaDao()
        recyclerView = findViewById(R.id.recyclerBebidas)

        cargarBebidas()

        val btnAgregarBebida = findViewById<ImageView>(R.id.btnAgregarBebida)
        btnAgregarBebida.setOnClickListener {
            mostrarDialogAgregarBebida()
        }
    }

    private fun mostrarDialogAgregarBebida() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_agregar_bebida, null)
        val dialog = AlertDialog.Builder(this)
            .setTitle("Agregar Bebida")
            .setView(dialogView)
            .create()

        val etNombre = dialogView.findViewById<EditText>(R.id.etNombreBebida)
        val etDescripcion = dialogView.findViewById<EditText>(R.id.etDescripcionBebida)
        val etPiezas = dialogView.findViewById<EditText>(R.id.etPiezasBebida)
        val btnGuardar = dialogView.findViewById<Button>(R.id.btnGuardarBebida)

        btnGuardar.setOnClickListener {
            val nombre = etNombre.text.toString().trim()
            val descripcion = etDescripcion.text.toString().trim()
            val piezas = etPiezas.text.toString().toIntOrNull() ?: 0

            if (nombre.isEmpty() || descripcion.isEmpty() || piezas <= 0) {
                Toast.makeText(this, "Completa todos los campos correctamente", Toast.LENGTH_SHORT).show()
            } else {
                val nuevaBebida = Bebida(nombre = nombre, descripcion = descripcion, piezas = piezas)
                bebidaDao.insert(nuevaBebida)
                Toast.makeText(this, "Bebida agregada", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
                cargarBebidas()
            }
        }

        dialog.show()
    }
}

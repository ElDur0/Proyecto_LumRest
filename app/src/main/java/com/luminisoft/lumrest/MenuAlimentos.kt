package com.luminisoft.lumrest

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.luminisoft.lumrest.data.Alimento
import com.luminisoft.lumrest.data.AppDatabase
import com.luminisoft.lumrest.data.CarritoManager

class MenuAlimentos : AppCompatActivity() {

    private lateinit var alimentoDao    : com.luminisoft.lumrest.data.AlimentoDao
    private lateinit var recyclerView   : RecyclerView
    private lateinit var adapter        : AlimentoMenuAdapter
    private lateinit var ivCarrito      : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_alimentos)

        // Conectar a la base de datos
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "empleados-db"
        ).fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()

        alimentoDao = db.alimentoDao()

        // Configurar RecyclerView
        recyclerView = findViewById(R.id.recyclerAlimentosMenu)
        val alimentos: List<Alimento> = alimentoDao.getAll()
        adapter = AlimentoMenuAdapter(alimentos)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        ivCarrito = findViewById(R.id.ivCarrito)
        ivCarrito.setOnClickListener {
            if (CarritoManager.obtenerCarrito().isEmpty()) {
                Toast.makeText(this, "El carrito está vacío", Toast.LENGTH_SHORT).show()
            } else {
                startActivity(Intent(this, CarritoActivity::class.java))
            }
        }
    }
}

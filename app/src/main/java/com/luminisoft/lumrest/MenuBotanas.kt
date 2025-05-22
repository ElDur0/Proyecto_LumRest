package com.luminisoft.lumrest

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.luminisoft.lumrest.data.Botana
import com.luminisoft.lumrest.data.CarritoManager

class MenuBotanas : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: BotanaMenuAdapter
    private lateinit var ivCarrito: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_botanas)

        recyclerView = findViewById(R.id.recyclerBotanasMenu)
        ivCarrito = findViewById(R.id.ivCarrito)
        recyclerView.layoutManager = LinearLayoutManager(this)

        Firebase.firestore.collection("botanas")
            .orderBy("nombre")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Toast.makeText(this, "Error al cargar botanas", Toast.LENGTH_SHORT).show()
                    return@addSnapshotListener
                }

                val lista = snapshot?.documents?.mapNotNull { doc ->
                    val nombre = doc.getString("nombre") ?: return@mapNotNull null
                    val descripcion = doc.getString("descripcion") ?: ""
                    val piezas = (doc.get("piezas") as? Long)?.toInt() ?: 0
                    Botana(nombre = nombre, descripcion = descripcion, piezas = piezas)
                } ?: emptyList()

                adapter = BotanaMenuAdapter(lista)
                recyclerView.adapter = adapter
            }

        ivCarrito.setOnClickListener {
            if (CarritoManager.obtenerCarrito().isEmpty()) {
                Toast.makeText(this, "El carrito está vacío", Toast.LENGTH_SHORT).show()
            } else {
                startActivity(Intent(this, CarritoActivity::class.java))
            }
        }
    }
}

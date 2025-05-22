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
import com.luminisoft.lumrest.data.Bebida
import com.luminisoft.lumrest.data.CarritoManager

class MenuBebidas : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var ivCarrito: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_bebidas)

        recyclerView = findViewById(R.id.recyclerBebidasMenu)
        recyclerView.layoutManager = LinearLayoutManager(this)
        ivCarrito = findViewById(R.id.ivCarrito)

        Firebase.firestore.collection("bebidas")
            .orderBy("nombre")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Toast.makeText(this, "Error al cargar bebidas", Toast.LENGTH_SHORT).show()
                    return@addSnapshotListener
                }

                val lista = snapshot?.documents?.mapNotNull { doc ->
                    val nombre = doc.getString("nombre") ?: return@mapNotNull null
                    val descripcion = doc.getString("descripcion") ?: ""
                    val ml = (doc.get("mililitros") as? Long)?.toInt() ?: 0
                    Bebida(doc.id, nombre, descripcion, ml)
                } ?: emptyList()

                recyclerView.adapter = BebidaMenuAdapter(lista)
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

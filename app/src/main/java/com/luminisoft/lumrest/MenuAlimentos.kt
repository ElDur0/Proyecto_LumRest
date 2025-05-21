package com.luminisoft.lumrest

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.luminisoft.lumrest.data.Alimento
import com.luminisoft.lumrest.data.CarritoManager

class MenuAlimentos : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AlimentoMenuAdapter
    private lateinit var ivCarrito: ImageView

    private val alimentos = mutableListOf<Alimento>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_alimentos)

        recyclerView = findViewById(R.id.recyclerAlimentosMenu)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = AlimentoMenuAdapter(alimentos)
        recyclerView.adapter = adapter

        ivCarrito = findViewById(R.id.ivCarrito)
        ivCarrito.setOnClickListener {
            if (CarritoManager.obtenerCarrito().isEmpty()) {
                Toast.makeText(this, "El carrito está vacío", Toast.LENGTH_SHORT).show()
            } else {
                startActivity(Intent(this, CarritoActivity::class.java))
            }
        }

        cargarAlimentosDesdeFirebase()
    }

    private fun cargarAlimentosDesdeFirebase() {
        val db = Firebase.firestore
        db.collection("alimentos")
            .get()
            .addOnSuccessListener { snapshot ->
                if (snapshot != null && !snapshot.isEmpty) {
                    alimentos.clear()
                    for (document in snapshot.documents) {
                        val alimento = document.toObject(Alimento::class.java)
                        alimento?.id = document.id
                        if (alimento != null) {
                            alimentos.add(alimento)
                        } else {
                            Log.w("MenuAlimentos", "Documento inválido: ${document.id}")
                        }
                    }
                    adapter.notifyDataSetChanged()
                    Log.d("MenuAlimentos", "Alimentos cargados: ${alimentos.size}")
                } else {
                    Log.w("MenuAlimentos", "No se encontraron alimentos")
                    Toast.makeText(this, "No hay alimentos en la base de datos", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { e ->
                Log.e("MenuAlimentos", "Error al cargar alimentos", e)
                Toast.makeText(this, "Error al cargar alimentos: ${e.message}", Toast.LENGTH_LONG).show()
            }
    }

}

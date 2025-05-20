package com.luminisoft.lumrest

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Catalogos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalogos)

        val tarjetaBebidas      = findViewById<LinearLayout>(R.id.tarjetaBebidas)
        val tarjetaAlimentos    = findViewById<LinearLayout>(R.id.tarjetaAlimentos)
        val tarjetaBotanas      = findViewById<LinearLayout>(R.id.tarjetaBotanas)
        val tarjetaEntradas     = findViewById<LinearLayout>(R.id.tarjetaEntradas)

        tarjetaAlimentos.setOnClickListener { startActivity(Intent(this, Alimentos::class.java)) }
        tarjetaBebidas.setOnClickListener   { startActivity(Intent(this, Bebidas::class.java))   }

    }
}
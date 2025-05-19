package com.luminisoft.lumrest

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ClientePrincipal : AppCompatActivity() {

    private lateinit var btnNFC:Button
    private lateinit var btnQR:Button
    private lateinit var btnLlamarMesero:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cliente_principal)


        btnNFC = findViewById(R.id.btnNFC)
        btnQR = findViewById(R.id.btnQR)
        btnLlamarMesero = findViewById(R.id.btnLlamarMesero)

        /*
        if (btnQR == null) Toast.makeText(this, "btnQR es null", Toast.LENGTH_LONG).show()
        if (btnNFC == null) Toast.makeText(this, "btnNFC es null", Toast.LENGTH_LONG).show()
        */
        btnNFC.setOnClickListener{ startActivity(Intent(this,ClienteInicio::class.java)) }
        btnQR.setOnClickListener{ startActivity(Intent(this,ClienteInicio::class.java)) }
        btnLlamarMesero.setOnClickListener{ Toast.makeText(this, "En un momento un mesero lo atenderá",Toast.LENGTH_SHORT).show() }
    }
}
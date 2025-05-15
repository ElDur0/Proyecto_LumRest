package com.luminisoft.lumrest

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Orden : AppCompatActivity() {
    private lateinit var btnLlamarMesero: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orden)

        btnLlamarMesero = findViewById(R.id.btnLlamarMesero)
        btnLlamarMesero.setOnClickListener { Toast.makeText(this, "En un momento un mesero lo atenderá", Toast.LENGTH_SHORT).show() }
    }
}
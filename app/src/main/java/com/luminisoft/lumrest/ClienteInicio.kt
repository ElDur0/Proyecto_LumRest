package com.luminisoft.lumrest

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ClienteInicio : AppCompatActivity() {

    private lateinit var btnMenuAlimentos: Button
    private lateinit var btnMenuBebidas:   Button
    private lateinit var btnMenuEntradas:  Button
    private lateinit var btnMenuBotanas:   Button
    private lateinit var btnLlamarMesero:  Button
    //private lateinit var btnCarrito:       Button

    //@SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cliente_inicio)

        btnMenuAlimentos = findViewById(R.id.btnMenuAlimentos)
        btnMenuBebidas   = findViewById(R.id.btnMenuBebidas)
        btnMenuEntradas  = findViewById(R.id.btnMenuEntradas)
        btnMenuBotanas   = findViewById(R.id.btnMenuBotanas)
        btnLlamarMesero  = findViewById(R.id.btnLlamarMesero)

       // btnCarrito       = findViewById(R.id.ivCarrito)

        btnMenuAlimentos.setOnClickListener { startActivity(Intent(this,MenuAlimentos::class.java)) }
        btnMenuBebidas  .setOnClickListener { startActivity(Intent(this, MenuBebidas::class.java)) }
        btnMenuEntradas .setOnClickListener { startActivity(Intent(this,MenuAlimentos::class.java)) }
        btnMenuBotanas  .setOnClickListener { startActivity(Intent(this, MenuBotanas::class.java)) }
        btnLlamarMesero .setOnClickListener { Toast.makeText(this, "En un momento un mesero lo atenderá",Toast.LENGTH_SHORT).show() }
       // btnCarrito      .setOnClickListener { startActivity(Intent(this,Orden::class.java)) }
    }
}
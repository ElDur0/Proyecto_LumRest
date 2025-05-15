package com.luminisoft.lumrest

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class RestaurantePrincipal : AppCompatActivity() {

    private lateinit var btnPedidos:   Button
    private lateinit var btnCatalogos: Button
    private lateinit var btnEmpleados: Button
    private lateinit var btnCuentas:   Button
    private lateinit var btnMesas:     Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurante_principal)

        btnPedidos      = findViewById(R.id.btnPedidos)
        btnCatalogos    = findViewById(R.id.btnCatalogos)
        btnEmpleados    = findViewById(R.id.btnEmpleados)
        btnCuentas      = findViewById(R.id.btnCuentas)
        btnMesas        = findViewById(R.id.btnMesas)


        btnPedidos.setOnClickListener   { startActivity(Intent(this,Pedidos::class.java))   }
        btnCatalogos.setOnClickListener { startActivity(Intent(this,Catalogos::class.java)) }
        btnEmpleados.setOnClickListener { startActivity(Intent(this,Empleados::class.java)) }
        btnCuentas.setOnClickListener   { startActivity(Intent(this,Cuentas::class.java))   }
        btnMesas.setOnClickListener     { startActivity(Intent(this,Mesas::class.java))     }

    }
}
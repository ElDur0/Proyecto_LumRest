package com.luminisoft.lumrest

import android.app.Notification
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class EmpleadosPrincipal : AppCompatActivity() {

    private lateinit var btnPedidos:      Button
    private lateinit var btnNotification: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empleados_principal)

        btnPedidos      = findViewById(R.id.btnPedidos)
        btnNotification = findViewById(R.id.btnNotificaciones)

        btnPedidos.setOnClickListener      { startActivity(Intent( this,PedidosEmpleados::class.java)) }
        btnNotification.setOnClickListener { startActivity(Intent(this,Notificaciones::class.java))    }

    }
}
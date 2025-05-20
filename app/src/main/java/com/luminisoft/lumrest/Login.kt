package com.luminisoft.lumrest

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Login : AppCompatActivity() {
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var tvRegister: TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        etEmail     = findViewById(R.id.etEmail)
        etPassword  = findViewById(R.id.etPassword)
        btnLogin    = findViewById(R.id.btnLogin)
        tvRegister  = findViewById(R.id.tvRegister)

        btnLogin.setOnClickListener{
            val email    = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if(email.isEmpty() || password.isEmpty()){
                Toast.makeText(this, "CAMPOS INCOMPLETOS",Toast.LENGTH_SHORT).show()
            }else{
                if (email == "cliente@gmail.com" && password == "12345") {
                    startActivity(Intent(this,ClientePrincipal::class.java))
                    finish()
                }
                if (email == "restaurante@gmail.com" && password == "123456"){
                    startActivity(Intent(this,RestaurantePrincipal::class.java))
                    finish()
                }
                if(email == "empleado@gmail.com" && password == "1234567"){
                    startActivity(Intent(this,EmpleadosPrincipal::class.java))
                    finish()
                }
                if(email == "chef@gmail.com" && password == "123"){
                    startActivity(Intent(this,PrincipalChef::class.java))
                    finish()
                }
                //Toast.makeText(this,"Correo o contraseña incorrectos",Toast.LENGTH_SHORT).show()
            }

        }


    }



}
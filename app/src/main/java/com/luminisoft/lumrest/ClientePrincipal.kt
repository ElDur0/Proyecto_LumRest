package com.luminisoft.lumrest

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.journeyapps.barcodescanner.CaptureActivity
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions

class ClientePrincipal : AppCompatActivity() {

    private lateinit var btnNFC          : Button
    private lateinit var btnQR           : Button
    private lateinit var btnLlamarMesero : Button

    private val qrLauncher = registerForActivityResult(ScanContract()) { result ->
        if (result.contents != null) {
            Toast.makeText(this, "QR leído: ${result.contents}", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, ClienteInicio::class.java))
        } else {
            Toast.makeText(this, "Escaneo cancelado", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cliente_principal)

        btnNFC          = findViewById(R.id.btnNFC)
        btnQR           = findViewById(R.id.btnQR)
        btnLlamarMesero = findViewById(R.id.btnLlamarMesero)

        btnNFC.setOnClickListener { startActivity(Intent(this, ClienteInicio::class.java)) }

        btnQR.setOnClickListener {
            val options = ScanOptions()
            options   .setPrompt("Escanea el código QR de tu mesa")
            options   .setBeepEnabled(true)
            options   .setOrientationLocked(true)
            options   .setCaptureActivity(CaptureActivity::class.java)
            qrLauncher.launch(options)
        }

        btnLlamarMesero.setOnClickListener {
            val notificacion = hashMapOf(
                "tipo"      to "llamar_mesero",
                "mensaje"   to "Un cliente ha solicitado un mesero",
                "timestamp" to FieldValue.serverTimestamp()
            )

            Firebase.firestore.collection("notificaciones")
                .add(notificacion)
                .addOnSuccessListener {
                    Toast.makeText(this, "Un mesero irá en breve", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error al enviar notificación", Toast.LENGTH_SHORT).show()
                }
        }
    }
}

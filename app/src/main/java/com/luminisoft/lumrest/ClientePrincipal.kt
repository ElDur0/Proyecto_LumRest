package com.luminisoft.lumrest

import android.content.Intent
import android.net.wifi.ScanResult
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.journeyapps.barcodescanner.CaptureActivity
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions

class ClientePrincipal : AppCompatActivity() {

    private lateinit var btnNFC         :Button
    private lateinit var btnQR          :Button
    private lateinit var btnLlamarMesero:Button


    private val qrLauncher = registerForActivityResult(ScanContract()){result ->
        if (result.contents != null){
            Toast.makeText(this, "Qr leído: ${result.contents}",Toast.LENGTH_LONG).show()
            startActivity(Intent(this,ClienteInicio::class.java))
        }else{
            Toast.makeText(this,"Escaneo cancelado", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cliente_principal)


        btnNFC          = findViewById(R.id.btnNFC)
        btnQR           = findViewById(R.id.btnQR)
        btnLlamarMesero = findViewById(R.id.btnLlamarMesero)

        /*
        if (btnQR == null) Toast.makeText(this, "btnQR es null", Toast.LENGTH_LONG).show()
        if (btnNFC == null) Toast.makeText(this, "btnNFC es null", Toast.LENGTH_LONG).show()
        */
        btnNFC.setOnClickListener { startActivity(Intent(this,ClienteInicio::class.java)) }
        btnQR.setOnClickListener {
            val options = ScanOptions()
            options.setPrompt("Escanea el código QR de tu mesa")
            options.setBeepEnabled(true)
            options.setOrientationLocked(true)
            options.setCaptureActivity(CaptureActivity::class.java)
            qrLauncher.launch(options)
        }
        btnLlamarMesero.setOnClickListener { Toast.makeText(this, "En un momento un mesero lo atenderá",Toast.LENGTH_SHORT).show() }
    }
}
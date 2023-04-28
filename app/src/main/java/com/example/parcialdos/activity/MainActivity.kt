package com.example.parcialdos.activity

import android.content.Intent
import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import com.example.parcialdos.R
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val time:Long = 4800
        val reloj = findViewById<TextView>(R.id.tvReloj)
        val format = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        val fechaHoraActual = Date()
        val fhFormat = format.format(fechaHoraActual)
        reloj.text = fhFormat

        Handler().postDelayed({
            startActivity(Intent(this, InicioSesion::class.java))
            finish()
        }, time)
    }
}
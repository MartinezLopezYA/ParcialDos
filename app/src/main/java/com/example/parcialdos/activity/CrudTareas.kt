package com.example.parcialdos.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.parcialdos.R
import com.google.firebase.auth.FirebaseAuth

class CrudTareas : AppCompatActivity() {

    private lateinit var taskRecycle: RecyclerView
    private lateinit var cargandoDato: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crud_tareas)

        val txtPerfil = findViewById<TextView>(R.id.txtPerfil)
        val btnNew = findViewById<Button>(R.id.btnNew)
        val btnVer = findViewById<Button>(R.id.btnVerTareas)
        val btnAtras = findViewById<Button>(R.id.btnAtras)

        //Se asocian los valores de la actividad principal
        val bundle:Bundle? = intent.extras
        val email:String? = bundle?.getString("email")
        txtPerfil.text = email

        btnNew.setOnClickListener {
            val intent = Intent(this, InsertarTarea::class.java)
            startActivity(intent)
        }

        btnVer.setOnClickListener {
            val ir = Intent(this, VerTareas::class.java)
            startActivity(ir)
        }

        btnAtras.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            onBackPressed()
        }
    }
}
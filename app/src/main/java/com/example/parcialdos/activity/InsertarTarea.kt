package com.example.parcialdos.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.parcialdos.R
import com.example.parcialdos.models.Model
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InsertarTarea : AppCompatActivity() {

    private lateinit var fireBase: DatabaseReference
    private lateinit var txtName: EditText
    private lateinit var txtTask: EditText
    private lateinit var txtFecha: EditText
    private lateinit var btnSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertar_tarea)

        fireBase = FirebaseDatabase.getInstance().getReference("tasks")
        txtName = findViewById(R.id.txtName)
        txtTask = findViewById(R.id.txtTask)
        txtFecha = findViewById(R.id.txtFecha)
        btnSave = findViewById(R.id.btnSave)

        fun reiniciarValores(){
            txtName.setText("")
            txtTask.setText("")
            txtFecha.setText("")
            txtName.requestFocus()
        }

        btnSave.setOnClickListener {
            guardarTarea()
            reiniciarValores()
        }

    }

    private fun guardarTarea(){

        val txtName = txtName.text.toString()
        val txtTask = txtTask.text.toString()
        val txtFecha = txtFecha.text.toString()

        if ((txtName.isEmpty()) || (txtTask.isEmpty()) || (txtFecha.isEmpty())){
            Toast.makeText(this,"Ingrese todos los campos",Toast.LENGTH_LONG).show()
        }

        val tsId = fireBase.push().key!!
        val tarea = Model(tsId, txtName, txtTask, txtFecha)
        fireBase.child(tsId).setValue(tarea)
            .addOnCompleteListener {
                Toast.makeText(this,"Tarea Registrada Correctamente", Toast.LENGTH_LONG).show()
            }.addOnFailureListener { err->
                Toast.makeText(this,"Tarea No Registrada", Toast.LENGTH_LONG).show()
            }
    }
}
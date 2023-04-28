package com.example.parcialdos.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.parcialdos.R
import com.example.parcialdos.models.Model
import com.google.firebase.database.FirebaseDatabase

class DetallesTareas : AppCompatActivity() {

    private lateinit var loadtsId: TextView
    private lateinit var loadtsName: TextView
    private lateinit var loadtsDesc: TextView
    private lateinit var loadtsFecha: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles_tareas)

        initView()
        definirValores()

        btnUpdate.setOnClickListener {
            updateTask(
                intent.getStringExtra("id").toString(),
                intent.getStringExtra("name").toString()
            )
        }
        btnDelete.setOnClickListener {
            eliminarTarea(
                intent.getStringExtra("id").toString()
            )

        }
    }

    private fun initView(){

        loadtsId = findViewById(R.id.loadtsId)
        loadtsName = findViewById(R.id.loadtsName)
        loadtsDesc = findViewById(R.id.loadtsDesc)
        loadtsFecha = findViewById(R.id.loadtsFecha)
        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)

    }

    //Define los valores a mostrar en cada tarea

    private fun definirValores(){
        loadtsId.text = intent.getStringExtra("id")
        loadtsName.text = intent.getStringExtra("name")
        loadtsDesc.text = intent.getStringExtra("desc")
        loadtsFecha.text = intent.getStringExtra("fecha")
    }

    //duncion eliminar

    private fun eliminarTarea(id:String){
        val dbRef = FirebaseDatabase.getInstance().getReference("tasks").child(id)
        val myTask = dbRef.removeValue()

        myTask.addOnSuccessListener {
            Toast.makeText(this,"Tarea eliminada", Toast.LENGTH_LONG).show()
            val intent = Intent(this,VerTareas::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{err ->
            Toast.makeText(this, "No se pudo eliminar${err}", Toast.LENGTH_LONG).show()

        }
    }
    //funcion de actualizar

    private fun updateTask(tsId:String, tsName: String){
        val dialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val viewDialog = inflater.inflate(R.layout.activity_actualizar, null)
        dialog.setView(viewDialog)

        val nName = viewDialog.findViewById<EditText>(R.id.txtNewName)
        val nDesc= viewDialog.findViewById<EditText>(R.id.txtNewDesc)
        val nFecha = viewDialog.findViewById<EditText>(R.id.txtNewFecha)
        val btnGuardar = viewDialog.findViewById<Button>(R.id.btnUpdateData)

        nName.setText(intent.getStringExtra("name").toString())
        nDesc.setText(intent.getStringExtra("desc").toString())
        nFecha.setText(intent.getStringExtra("fecha").toString())

        val alerta = dialog.create()
        alerta.show()

        btnGuardar.setOnClickListener{
            actualizarDatos(
                tsId,
                nName.text.toString(),
                nDesc.text.toString(),
                nFecha.text.toString()
            )
            Toast.makeText(this ,"Tareas Actualizada", Toast.LENGTH_LONG).show()

            loadtsName.text = nName.text.toString()
            loadtsDesc.text = nDesc.text.toString()
            loadtsFecha.text =nFecha.text.toString()

            alerta.dismiss()
        }

    }

    private fun actualizarDatos(id: String, name:String, desc:String, fecha:String) {
        val dbRef = FirebaseDatabase.getInstance().getReference("tasks").child(id)
        val info = Model(id, name, desc, fecha)
        dbRef.setValue(info)
    }
}
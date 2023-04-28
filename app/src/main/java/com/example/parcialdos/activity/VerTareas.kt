package com.example.parcialdos.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.parcialdos.R
import com.example.parcialdos.adapter.TareasAdapter
import com.example.parcialdos.models.Model
import com.google.firebase.database.*

class VerTareas : AppCompatActivity() {

    private lateinit var tareaReciclar : RecyclerView
    private lateinit var cargandoDatos: TextView
    private lateinit var TareaList: ArrayList<Model>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_tareas)

        tareaReciclar = findViewById(R.id.rvTareas)
        tareaReciclar.layoutManager = LinearLayoutManager(this)
        tareaReciclar.setHasFixedSize(true)
        cargandoDatos = findViewById(R.id.cargandoTareas)

        TareaList = arrayListOf<Model>()
        traerInformacion()

    }

    private fun traerInformacion(){
        tareaReciclar.visibility = View.GONE
        cargandoDatos.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("tasks")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                TareaList.clear()
                if (snapshot.exists()){
                    for (x in snapshot.children){
                        val datos = x.getValue(Model::class.java)
                        TareaList.add(datos!!)
                    }
                    val adap = TareasAdapter(TareaList)
                    tareaReciclar.adapter = adap
                    adap.setOnClickListener(object: TareasAdapter.onTareaListClick{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@VerTareas, DetallesTareas::class.java)

                            intent.putExtra("id",TareaList[position].tsId)
                            intent.putExtra("name",TareaList[position].tsName)
                            intent.putExtra("desc",TareaList[position].tsDescrip)
                            intent.putExtra("fecha",TareaList[position].tsFecha)

                            startActivity(intent)
                        }

                    })
                    tareaReciclar.visibility = View.VISIBLE
                    cargandoDatos.visibility = View.GONE
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

}
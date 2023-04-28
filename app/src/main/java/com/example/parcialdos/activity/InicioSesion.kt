package com.example.parcialdos.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.airbnb.lottie.LottieAnimationView
import com.example.parcialdos.R
import com.google.firebase.auth.FirebaseAuth

class InicioSesion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio_sesion)

        val txtUser = findViewById<EditText>(R.id.txtUser)
        val txtPs = findViewById<EditText>(R.id.txtPs)
        val btnIng = findViewById<Button>(R.id.btnIngresar)
        val btnCrear = findViewById<Button>(R.id.btnCrear)

        fun reiniciarValores(){
            txtUser.setText("")
            txtPs.setText("")
            txtUser.requestFocus()
        }

        btnIng.setOnClickListener {
            if (txtUser.text.toString().isNotEmpty() && txtPs.text.toString().isNotEmpty()){
                FirebaseAuth.getInstance().signInWithEmailAndPassword(txtUser.text.toString(),
                    txtPs.text.toString()).addOnCompleteListener{
                    if (it.isSuccessful){

                        //Captura los datos para enviarlos y recuperarlos en otra actividad (inicio.kt)
                        val ir = Intent(this, CrudTareas::class.java).apply{
                            putExtra("email", txtUser.text.toString())
                            putExtra("psw", txtPs.text.toString())
                        }
                        startActivity(ir)
                    }else{
                        Toast.makeText(this,"Datos incorrectos", Toast.LENGTH_LONG).show()
                    }
                }
            }
            reiniciarValores()
        }

        btnCrear.setOnClickListener{
            if (txtUser.text.toString().isNotEmpty() && txtPs.text.toString().isNotEmpty()){
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(txtUser.text.toString(),
                    txtPs.text.toString()).addOnCompleteListener{
                    if (it.isSuccessful){

                        //Captura los datos para enviarlos y recuperarlos en otra actividad (inicio.kt)
                        val ir = Intent(this, CrudTareas::class.java).apply{
                            putExtra("email", txtUser.text.toString())
                            putExtra("psw", txtPs.text.toString())

                        }
                        startActivity(ir)
                    }else{
                        Toast.makeText(this,"Datos incorrectos", Toast.LENGTH_LONG).show()
                    }
                }
            }
            reiniciarValores()
        }

    }

    private fun newScene(imageView: LottieAnimationView, animation: Int){
        imageView.setAnimation(animation)
        imageView.playAnimation()
    }

}
package com.example.fitnesscoach

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class InicioActivity : Activity(){
    private lateinit var principianteBoton: Button
    private lateinit var intermedioBoton: Button
    private lateinit var avanzadoBoton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.inicio)

            principianteBoton = findViewById<Button>(R.id.botonPrincipiante)
            intermedioBoton = findViewById<Button>(R.id.botonIntermedio)
            avanzadoBoton = findViewById<Button>(R.id.botonAvanzado)

            principianteBoton.setOnClickListener {
                abrirRutina()
            }

            intermedioBoton.setOnClickListener {
                abrirRutina()
            }

            avanzadoBoton.setOnClickListener {
                abrirRutina()
            }
        }
     catch (e: Exception) {
        Log.e("Error:", e.toString())
    }
    }

    private fun abrirRutina() {
        val intent = Intent(this, RutinaActivity::class.java)
        startActivity(intent)
    }
    }


package com.example.fitnesscoach.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.fitnesscoach.R

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
                abrirRutina(0)
            }

            intermedioBoton.setOnClickListener {
                abrirRutina(1)
            }

            avanzadoBoton.setOnClickListener {
                abrirRutina(2)
            }
        }
     catch (e: Exception) {
        Log.e("Error:", e.toString())
    }
    }

    private fun abrirRutina(nivel: Int) {
        // TODO: Apuntar al recycler
        /*
        val intent = Intent(this, ListadoEjercicios::class.java)
        intent.putExtra("nivel", nivel)
        startActivity(intent)
        */
    }
}


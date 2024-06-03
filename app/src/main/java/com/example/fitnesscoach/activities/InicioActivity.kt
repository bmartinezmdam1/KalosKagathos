package com.example.fitnesscoach.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.fitnesscoach.Ajustes
import com.example.fitnesscoach.R


class InicioActivity : AppCompatActivity() {
    private lateinit var principianteBoton: Button
    private lateinit var intermedioBoton: Button
    private lateinit var avanzadoBoton: Button
    private lateinit var textView: TextView
    private lateinit var imagenPrincipiante: ImageView
    private lateinit var imagenIntermedio: ImageView
    private lateinit var imagenAvanzado: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.inicio)

        principianteBoton = findViewById(R.id.botonPrincipiante)
        intermedioBoton = findViewById(R.id.botonIntermedio)
        avanzadoBoton = findViewById(R.id.botonAvanzado)
        textView = findViewById(R.id.textView)
        imagenPrincipiante = findViewById(R.id.imagenPrincipiante)
        imagenIntermedio = findViewById(R.id.imagenIntermedio)
        imagenAvanzado = findViewById(R.id.imagenAvanzado)

        // Example button to show fragment
        principianteBoton.setOnClickListener {
            showFragment()
        }
        intermedioBoton.setOnClickListener {
            showFragment()
        }
        avanzadoBoton.setOnClickListener {
            showFragment()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_main_drawer, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.nav_ajustes -> {
                showAjustesFragment()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showFragment() {
        val fragment = RecyclerFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()

        // Ocultar las vistas del Activity
        hideActivityViews()

        // Mostrar el contenedor del Fragment
        findViewById<FrameLayout>(R.id.fragment_container).visibility = View.VISIBLE
    }

    private fun showAjustesFragment() {
        val fragment = Ajustes()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()

        // Ocultar las vistas del Activity
        hideActivityViews()

        // Mostrar el contenedor del Fragment
        findViewById<FrameLayout>(R.id.fragment_container).visibility = View.VISIBLE
    }

    private fun hideActivityViews() {
        textView.visibility = View.GONE
        imagenPrincipiante.visibility = View.GONE
        imagenIntermedio.visibility = View.GONE
        imagenAvanzado.visibility = View.GONE
        principianteBoton.visibility = View.GONE
        intermedioBoton.visibility = View.GONE
        avanzadoBoton.visibility = View.GONE
    }
}

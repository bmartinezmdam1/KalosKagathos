package com.example.fitnesscoach.activities

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.fitnesscoach.R
import com.example.fitnesscoach.databinding.InicioBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class InicioActivity : AppCompatActivity() {
    private lateinit var principianteBoton: Button
    private lateinit var intermedioBoton: Button
    private lateinit var avanzadoBoton: Button
    private lateinit var textView: TextView
    private lateinit var imagenPrincipiante: ImageView
    private lateinit var imagenIntermedio: ImageView
    private lateinit var imagenAvanzado: ImageView
    private lateinit var nombreUser: ImageView
    private lateinit var binding: InicioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = InicioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar) // Configurar la Toolbar como ActionBar

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        principianteBoton = findViewById(R.id.botonPrincipiante)
        intermedioBoton = findViewById(R.id.botonIntermedio)
        avanzadoBoton = findViewById(R.id.botonAvanzado)
        textView = findViewById(R.id.textView)
        imagenPrincipiante = findViewById(R.id.imagenPrincipiante)
        imagenIntermedio = findViewById(R.id.imagenIntermedio)
        imagenAvanzado = findViewById(R.id.imagenAvanzado)

        navView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_dashboard -> {
                    hideElements()
                    if (navController.currentDestination?.id == R.id.navigation_home ||
                        navController.currentDestination?.id == R.id.navigation_notifications) {
                        navController.navigate(R.id.navigation_dashboard)
                        true
                    } else {
                        false
                    }
                }
                R.id.navigation_notifications -> {
                    hideElements()
                    if (navController.currentDestination?.id == R.id.navigation_home ||
                        navController.currentDestination?.id == R.id.navigation_dashboard) {
                        navController.navigate(R.id.navigation_notifications)
                        true
                    } else {
                        false
                    }
                }
                R.id.navigation_home -> {
                    navController.navigate(R.id.navigation_home)
                    true
                }
                else -> false
            }
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.navigation_home) {
                showElements()
            }
        }
    }

    private fun hideElements() {
        textView.visibility = View.GONE
        imagenPrincipiante.visibility = View.GONE
        imagenIntermedio.visibility = View.GONE
        imagenAvanzado.visibility = View.GONE
        principianteBoton.visibility = View.GONE
        intermedioBoton.visibility = View.GONE
        avanzadoBoton.visibility = View.GONE
    }

    private fun showElements() {
        textView.visibility = View.VISIBLE
        imagenPrincipiante.visibility = View.VISIBLE
        imagenIntermedio.visibility = View.VISIBLE
        imagenAvanzado.visibility = View.VISIBLE
        principianteBoton.visibility = View.VISIBLE
        intermedioBoton.visibility = View.VISIBLE
        avanzadoBoton.visibility = View.VISIBLE
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}

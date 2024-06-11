package com.example.fitnesscoach.activities

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.fitnesscoach.R
import com.example.fitnesscoach.databinding.InicioBinding

class MainActivity : AppCompatActivity() {
    // Declaración de variables para los botones y las imágenes
    private lateinit var principianteBoton: Button
    private lateinit var intermedioBoton: Button
    private lateinit var avanzadoBoton: Button
    private lateinit var imagenPrincipiante: ImageView
    private lateinit var imagenIntermedio: ImageView
    private lateinit var imagenAvanzado: ImageView
    private lateinit var binding: InicioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflar el layout usando ViewBinding
        binding = InicioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Asignar referencias a las vistas
        principianteBoton = findViewById(R.id.botonPrincipiante)
        intermedioBoton = findViewById(R.id.botonIntermedio)
        avanzadoBoton = findViewById(R.id.botonAvanzado)
        imagenPrincipiante = findViewById(R.id.imagenPrincipiante)
        imagenIntermedio = findViewById(R.id.imagenIntermedio)
        imagenAvanzado = findViewById(R.id.imagenAvanzado)

        // Configuración de Bottom Navigation View
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // Listener para los botones de nivel
        principianteBoton.setOnClickListener {
            Toast.makeText(this, "Nivel principiante activado.", Toast.LENGTH_SHORT).show()
        }
        intermedioBoton.setOnClickListener {
            Toast.makeText(this, "Nivel intermedio activado.", Toast.LENGTH_SHORT).show()
        }
        avanzadoBoton.setOnClickListener {
            Toast.makeText(this, "Nivel avanzado activado.", Toast.LENGTH_SHORT).show()
        }
    }
}

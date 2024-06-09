package com.example.fitnesscoach.activities

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnesscoach.Ajustes
import com.example.fitnesscoach.R
import com.example.fitnesscoach.databinding.InicioBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.apphosting.datastore.testing.DatastoreTestTrace.FirestoreV1Action.BeginTransaction

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
        val username = intent.getStringExtra("username")
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        principianteBoton = findViewById(R.id.botonPrincipiante)
        intermedioBoton = findViewById(R.id.botonIntermedio)
        avanzadoBoton = findViewById(R.id.botonAvanzado)
        textView = findViewById(R.id.textView)
        imagenPrincipiante = findViewById(R.id.imagenPrincipiante)
        imagenIntermedio = findViewById(R.id.imagenIntermedio)
        imagenAvanzado = findViewById(R.id.imagenAvanzado)

        principianteBoton.setOnClickListener {
            Toast.makeText(this, "Nivel intermedio activado", Toast.LENGTH_SHORT).show()


        }
        intermedioBoton.setOnClickListener {
            Toast.makeText(this, "Nivel intermedio activado", Toast.LENGTH_SHORT).show()

        }
        avanzadoBoton.setOnClickListener {
            Toast.makeText(this, "Nivel intermedio activado", Toast.LENGTH_SHORT).show()
        }
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
                        val bundle = Bundle()
                        bundle.putString("username", username)
                        navController.navigate(R.id.navigation_notifications, bundle)
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
        scheduleNotification()
    }

    private fun scheduleNotification() {
        val intent = Intent(this, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val interval = 1000 * 10L
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent)
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

    private fun navigateToFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}

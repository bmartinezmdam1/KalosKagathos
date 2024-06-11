package com.example.fitnesscoach.activities

import AlarmReceiver
import android.Manifest
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.fitnesscoach.R
import com.example.fitnesscoach.databinding.InicioBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController

class InicioActivity : AppCompatActivity() {
    companion object {
        const val REQUEST_CODE_POST_NOTIFICATIONS = 1001
        const val CHANNEL_ID = "canal1"
        const val NOTIFICATION_ID = 1
    }

    private lateinit var principianteBoton: Button
    private lateinit var intermedioBoton: Button
    private lateinit var avanzadoBoton: Button
    private lateinit var imagenPrincipiante: ImageView
    private lateinit var imagenIntermedio: ImageView
    private lateinit var imagenAvanzado: ImageView
    private lateinit var binding: InicioBinding

    // Builder para la notificación
    private val builder by lazy {
        NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.kalos)
            .setContentTitle("Es hora de entrenar!")
            .setContentText("Han pasado 24 horas desde tu último entrenamiento. Es hora de entrenar!")
            .setStyle(NotificationCompat.BigTextStyle().bigText("Han pasado 24 horas desde tu último entrenamiento. Es hora de entrenar!"))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = InicioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar el Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Configurar la navegación inferior
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // Enviar una notificación al iniciar la actividad
        sendNotification("Es hora de entrenar!")

        // Inicializar botones e imágenes
        principianteBoton = findViewById(R.id.botonPrincipiante)
        intermedioBoton = findViewById(R.id.botonIntermedio)
        avanzadoBoton = findViewById(R.id.botonAvanzado)
        imagenPrincipiante = findViewById(R.id.imagenPrincipiante)
        imagenIntermedio = findViewById(R.id.imagenIntermedio)
        imagenAvanzado = findViewById(R.id.imagenAvanzado)

        // Configurar listeners para los botones
        principianteBoton.setOnClickListener {
            Toast.makeText(this, "Nivel principiante activado", Toast.LENGTH_SHORT).show()
            sendNotification("Ahora eres principiante!")
        }
        intermedioBoton.setOnClickListener {
            Toast.makeText(this, "Nivel intermedio activado", Toast.LENGTH_SHORT).show()
            sendNotification("Ahora eres intermedio!")
        }
        avanzadoBoton.setOnClickListener {
            Toast.makeText(this, "Nivel avanzado activado", Toast.LENGTH_SHORT).show()
            sendNotification("Ahora eres avanzado!")
        }

        // Configurar listener para la navegación
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
                        bundle.putString("username", intent.getStringExtra("username"))
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

        // Mostrar elementos cuando se navega a la pantalla de inicio
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.navigation_home) {
                showElements()
            }
        }

        // Crear canal de notificación y programar la notificación
        createNotificationChannel()
        scheduleNotification()
    }

    // Programar una notificación repetitiva
    private fun scheduleNotification() {
        val intent = Intent(this, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val interval = 60 * 1000L // Intervalo de 1 minuto
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent)
    }

    // Ocultar elementos de la interfaz
    private fun hideElements() {
        imagenPrincipiante.visibility = View.GONE
        imagenIntermedio.visibility = View.GONE
        imagenAvanzado.visibility = View.GONE
        principianteBoton.visibility = View.GONE
        intermedioBoton.visibility = View.GONE
        avanzadoBoton.visibility = View.GONE
    }

    // Mostrar elementos de la interfaz
    private fun showElements() {
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

    // Crear el canal de notificación para dispositivos Android Oreo y superiores
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "canal1"
            val descriptionText = "este es el canal1"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    // Enviar una notificación
    private fun sendNotification(message: String) {
        val intent = Intent(this, InicioActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.kalos)
            .setContentTitle("Es hora de entrenar!")
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(this)) {
            if (ActivityCompat.checkSelfPermission(
                    this@InicioActivity,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            notify(NOTIFICATION_ID, builder.build())
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE_POST_NOTIFICATIONS -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    sendNotification("Es hora de entrenar!")
                } else {
                    Toast.makeText(this, "Permission denied to post notifications", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflar el menú para el Toolbar
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.pesaIcon -> {
                // Navegar a un fragmento cuando se haga clic en el ícono del menú
                hideElements()
                findNavController(R.id.nav_host_fragment_activity_main).navigate(R.id.navigation_dashboard)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

package com.example.fitnesscoach.activities

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
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
    private lateinit var textView: TextView
    private lateinit var imagenPrincipiante: ImageView
    private lateinit var imagenIntermedio: ImageView
    private lateinit var imagenAvanzado: ImageView
    private lateinit var binding: InicioBinding

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

        setSupportActionBar(binding.toolbar)

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
            Toast.makeText(this, "Nivel principiante activado", Toast.LENGTH_SHORT).show()
            sendNotification()
        }
        intermedioBoton.setOnClickListener {
            Toast.makeText(this, "Nivel intermedio activado", Toast.LENGTH_SHORT).show()
            sendNotification()
        }
        avanzadoBoton.setOnClickListener {
            Toast.makeText(this, "Nivel avanzado activado", Toast.LENGTH_SHORT).show()
            sendNotification()
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

        createNotificationChannel()
        scheduleNotification()
    }

    private fun scheduleNotification() {
        val intent = Intent(this, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val interval = 60 * 1000L // Intervalo de 1 minuto
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

    private fun sendNotification() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                REQUEST_CODE_POST_NOTIFICATIONS
            )
            return
        }

        with(NotificationManagerCompat.from(this)) {
            notify(NOTIFICATION_ID, builder.build())
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE_POST_NOTIFICATIONS -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    sendNotification()
                } else {
                    Toast.makeText(this, "Permission denied to post notifications", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}


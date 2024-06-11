package com.example.fitnesscoach.activities

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.fitnesscoach.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class Notificaciones : FirebaseMessagingService() {
    // Identificador del canal de notificación
    val CHANNEL_ID = "null"

    override fun onCreate() {
        super.onCreate()
        // Crear y configurar la notificación
        var builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.kalos)
            .setContentTitle("Es hora de entrenar!")
            .setContentText("Es hora de entrenar!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        // Crear el canal de notificación
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        // Crear el canal de notificación solo para dispositivos con API 26+ (Android 8+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "canal1"
            val descriptionText = "Este es el canal1"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Registrar el canal en el sistema
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}

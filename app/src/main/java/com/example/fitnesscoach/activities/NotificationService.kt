package com.example.fitnesscoach.activities

import android.Manifest
import android.app.IntentService
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.fitnesscoach.R
import androidx.core.content.ContextCompat


class NotificationService : IntentService("NotificationService") {

    override fun onHandleIntent(intent: Intent?) {
        if (intent != null) {
            // Construir la notificación
            val builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.kalos)
                .setContentTitle("Es hora de entrenar!")
                .setContentText("Han pasado 24 horas desde tu último entrenamiento. Es hora de entrenar!")
                .setStyle(NotificationCompat.BigTextStyle().bigText("Han pasado 24 horas desde tu último entrenamiento. Es hora de entrenar!"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            // Mostrar la notificación
            with(NotificationManagerCompat.from(this)) {
                if (ContextCompat.checkSelfPermission(
                        this@NotificationService,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return
                }
                notify(NOTIFICATION_ID, builder.build())
            }


        }
    }

    companion object {
        private const val CHANNEL_ID = "channel_id"
        private const val NOTIFICATION_ID = 1001
    }
}

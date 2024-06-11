package com.example.fitnesscoach.activities


import android.Manifest
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.fitnesscoach.R
import com.example.fitnesscoach.activities.InicioActivity

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // Crear PendingIntent para abrir InicioActivity al tocar la notificación
        val intent = Intent(context, InicioActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        // Iniciar el servicio de notificación en primer plano
        val serviceIntent = Intent(context, NotificationService::class.java)
        ContextCompat.startForegroundService(context, serviceIntent)

        // Construir la notificación
        val builder = NotificationCompat.Builder(context, InicioActivity.CHANNEL_ID)
            .setSmallIcon(R.drawable.kalos)
            .setContentTitle("¡Es hora de entrenar!")
            .setContentText("Han pasado 24 horas desde tu último entrenamiento. ¡Es hora de entrenar!")
            .setStyle(NotificationCompat.BigTextStyle().bigText("Han pasado 24 horas desde tu último entrenamiento. ¡Es hora de entrenar!"))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent) // Agregar PendingIntent a la notificación

        // Mostrar la notificación
        with(NotificationManagerCompat.from(context)) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // Si los permisos de notificación no están otorgados, regresar
                return
            }
            notify(InicioActivity.NOTIFICATION_ID, builder.build())
        }
    }
}

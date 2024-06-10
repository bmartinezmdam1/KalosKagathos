// AlarmReceiver.kt
package com.example.fitnesscoach.activities

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.fitnesscoach.R
import com.example.fitnesscoach.activities.Notificaciones

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {

        val builder = NotificationCompat.Builder(context, InicioActivity.CHANNEL_ID)
            .setSmallIcon(R.drawable.kalos)
            .setContentTitle("Es hora de entrenar!")
            .setContentText("Han pasado 24 horas desde tu último entrenamiento. Es hora de entrenar!")
            .setStyle(NotificationCompat.BigTextStyle().bigText("Han pasado 24 horas desde tu último entrenamiento. Es hora de entrenar!"))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(context)) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            notify(InicioActivity.NOTIFICATION_ID, builder.build())
        }
    }
}

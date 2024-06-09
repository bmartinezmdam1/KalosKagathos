// AlarmReceiver.kt
package com.example.fitnesscoach.activities

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.fitnesscoach.activities.Notificaciones

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val serviceIntent = Intent(context, Notificaciones::class.java)
        context.startService(serviceIntent)
    }
}

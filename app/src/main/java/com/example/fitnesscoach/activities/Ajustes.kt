package com.example.fitnesscoach

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class Ajustes : Fragment() {
    private lateinit var boton1: Button
    private lateinit var boton2: Button
    private lateinit var imagen: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.ajustes, container, false)
        boton1 = view.findViewById(R.id.cambiarFoto)
        boton2 = view.findViewById(R.id.ActivarNotificaciones)
        imagen = view.findViewById(R.id.imageView2)
        boton1.visibility = View.VISIBLE
        boton2.visibility = View.VISIBLE
        imagen.visibility = View.VISIBLE
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.let { createNotificationChannel(it) }
        boton1.setOnClickListener { btnCambiarFoto(it) }
        boton2.setOnClickListener { btnActivarNotificaciones(it) }
    }

    fun btnCambiarFoto(view: View) {
        // Implementar lógica para cambiar la foto
    }
    fun borrarDatos() {

    }

    fun btnActivarNotificaciones(view: View) {
        sendNotification(requireContext())
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "mi_canal_id"
            val channelName = "Mi Canal"
            val channelDescription = "Descripción de mi canal"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, channelName, importance).apply {
                description = channelDescription
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun sendNotification(context: Context) {
        val channelId = "mi_canal_id"
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.bellnot) // Asegúrate de que tienes un icono válido
            .setContentTitle("Es hora de entrenar!")
            .setContentText("Es hora de entrenar!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            notify(1, builder.build())
        }
    }
}
package com.example.fitnesscoach

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.fitnesscoach.activities.InicioActivity
import com.example.fitnesscoach.activities.LoginActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.FirebaseAuthCredentialsProvider

class Ajustes : Fragment() {
    private  val db = FirebaseFirestore.getInstance()
    private lateinit var boton1: Button
    private lateinit var boton2: Button
    private lateinit var boton3: Button
    private lateinit var imagen: ImageView
    private lateinit var correo: EditText
    private lateinit var nombre: EditText
    private lateinit var contrasena: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.ajustes, container, false)
        boton1 = view.findViewById(R.id.BorrarDatos)
        boton2 = view.findViewById(R.id.CambiarDatos)
        boton3 = view.findViewById(R.id.ActivarNotificaciones)
        imagen = view.findViewById(R.id.imageView2)
        correo = view.findViewById(R.id.correoElectronico)
        nombre = view.findViewById(R.id.NombreUser)
        contrasena = view.findViewById(R.id.editContrasena)
        boton1.visibility = View.VISIBLE
        boton2.visibility = View.VISIBLE
        imagen.visibility = View.VISIBLE
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       // context?.let { createNotificationChannel(it) }
        boton1.setOnClickListener {
            val username = arguments?.getString("username")
            if (username != null) {
                val docRef = db.collection("usuarios").document(username)
                docRef.get()
                    .addOnSuccessListener { document ->
                        if (document.exists()) {
                            docRef.delete()
                                .addOnSuccessListener {
                                    println("Usuario $username eliminado con éxito.")
                                    val intent = Intent (getActivity(), LoginActivity::class.java)
                                    getActivity()?.startActivity(intent)
                                }
                                .addOnFailureListener { e ->
                                    println("Error al eliminar el usuario: $e")
                                }
                        } else {
                            println("El usuario $username no existe.")
                        }
                    }
                    .addOnFailureListener { e ->
                        println("Error al obtener el usuario: $e")
                    }
            } else {
                println("El username es null.")
            }
        }
        boton2.setOnClickListener {
            if (!isUsernameValid()) return@setOnClickListener
            if (!isPasswordValid()) return@setOnClickListener
            if (!isEmailValid(correo.toString())) return@setOnClickListener
            val username = arguments?.getString("username")
            if (username != null) {
                val docRef = db.collection("usuarios").document(username)
                docRef.get()
                    .addOnSuccessListener { document ->
                        if (document.exists()) {
                            docRef.delete()
                    }
                    }
                db.collection("usuarios").document(correo.toString()).get().addOnFailureListener {
                    Toast.makeText(getActivity(), "Ha ocurrido un error durante el guardado. Contacte con su administrador.", Toast.LENGTH_SHORT).show()
                }.addOnSuccessListener { document ->
                    if (document.data != null) {
                        Toast.makeText(getActivity(),"El correo ya existe en la base de datos",Toast.LENGTH_SHORT).show();
                        return@addOnSuccessListener
                    }
                }
            val data = hashMapOf(
                "nombre" to nombre.text.toString(),
                "contrasena" to contrasena.text.toString()
            );
            db.collection("usuarios").document(correo.toString()).set(data)
    }
    }
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
    private fun isUsernameValid(): Boolean {
        val username = nombre.text.toString()

        if (username.length !in 6..20) {
            Toast.makeText(getActivity(),"El nombre de usuario tiene que tener entre 6 y 20 caracteres",Toast.LENGTH_SHORT).show();
            return false
        }
        return true
    }

    private fun isEmailValid(email: String): Boolean {
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(getActivity(),"El formato del email no es correcto",Toast.LENGTH_SHORT).show();
            return false
        }
        return true
    }

    private fun isPasswordValid(): Boolean {
        // TODO Verificar que ambas passwords son iguales....
        val password = contrasena.text.toString()

        return if (password.length !in 6..20 ) {
            Toast.makeText(getActivity(),"La contrasena tiene que tener entre 6 y 20 caracteres",Toast.LENGTH_SHORT).show();
            false
        }
        else true
    }
}
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
import com.example.fitnesscoach.R
import com.example.fitnesscoach.activities.Camera
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
        boton3 = view.findViewById(R.id.cambiarFoto)
        imagen = view.findViewById(R.id.imageView2)
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
        var username = arguments?.getString("username")

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
            val username = arguments?.getString("username")

            if (!isUsernameValid()) return@setOnClickListener
            if (!isPasswordValid()) return@setOnClickListener
            val data = hashMapOf(
                "nombre" to nombre.text.toString(),
                "contrasena" to contrasena.text.toString()
            );
            db.collection("usuarios").document(username.toString()).set(data)
    }
        boton3.setOnClickListener {
            val intent = Intent(activity, Camera::class.java)
            startActivity(intent)
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
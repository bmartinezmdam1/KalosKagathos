package com.example.fitnesscoach

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.fitnesscoach.activities.Camera
import com.example.fitnesscoach.activities.LoginActivity
import com.google.firebase.firestore.FirebaseFirestore

class Ajustes : Fragment() {
    // Initialize Firebase Firestore instance
    private val db = FirebaseFirestore.getInstance()

    // Declare UI elements
    private lateinit var boton1: Button
    private lateinit var boton2: Button
    private lateinit var boton3: Button
    private lateinit var boton4: Button
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
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.ajustes, container, false)

        // Initialize UI elements
        boton1 = view.findViewById(R.id.BorrarDatos)
        boton2 = view.findViewById(R.id.CambiarDatos)
        boton3 = view.findViewById(R.id.cambiarFoto)
        boton4 = view.findViewById(R.id.MostrarDatos)
        imagen = view.findViewById(R.id.imageView2)
        nombre = view.findViewById(R.id.NombreUser)
        contrasena = view.findViewById(R.id.editContrasena)

        // Set visibility for buttons and image view
        boton1.visibility = View.VISIBLE
        boton2.visibility = View.VISIBLE
        imagen.visibility = View.VISIBLE

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var username = arguments?.getString("username")

        // Set click listener for 'Delete Data' button
        boton1.setOnClickListener {
            val username = arguments?.getString("username")
            if (username != null) {
                val docRef = db.collection("usuarios").document(username)
                docRef.get()
                    .addOnSuccessListener { document ->
                        if (document.exists()) {
                            // If user exists, delete the document
                            docRef.delete()
                                .addOnSuccessListener {
                                    Toast.makeText(requireContext(), "Usuario $username eliminado con éxito.", Toast.LENGTH_SHORT).show()
                                    val intent = Intent(activity, LoginActivity::class.java)
                                    startActivity(intent)
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

        // Set click listener for 'Change Data' button
        boton2.setOnClickListener {
            val username = arguments?.getString("username")

            // Validate username and password before updating data
            if (!isUsernameValid()) return@setOnClickListener
            if (!isPasswordValid()) return@setOnClickListener

            // Create a map of the new data
            val data = hashMapOf(
                "nombre" to nombre.text.toString(),
                "contrasena" to contrasena.text.toString()
            )

            // Update the Firestore document with new data
            db.collection("usuarios").document(username.toString()).set(data).addOnSuccessListener {
                Toast.makeText(requireContext(), "Datos cambiados", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(requireContext(), "Algo ha fallado...", Toast.LENGTH_SHORT).show()
            }
        }

        // Set click listener for 'Change Photo' button
        boton3.setOnClickListener {
            val intent = Intent(activity, Camera::class.java)
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
        }

        // Set click listener for 'Show Data' button
        boton4.setOnClickListener {
            val username = arguments?.getString("username")
            if (username != null) {
                val docRef = db.collection("usuarios").document(username)
                docRef.get()
                    .addOnSuccessListener { document ->
                        if (document.exists()) {
                            val nom = document.get("nombre")
                            val contra = document.get("contrasena")
                            // Display the user's name and password in a Toast message
                            docRef.delete()
                                .addOnSuccessListener {
                                    Toast.makeText(requireContext(), "Usuario :  $nom Contraseña $contra", Toast.LENGTH_SHORT).show()
                                }
                                .addOnFailureListener { e ->
                                    println("Error al mostrar los datos del usuario $e")
                                }
                        } else {
                            println("El usuario $username no existe.")
                        }
                    }
                    .addOnFailureListener { e ->
                        println("Error al obtener el usuario: $e")
                    }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            // Retrieve the image URI and set it to the ImageView
            val imageUri: Uri? = data?.getStringExtra("image_uri")?.let { Uri.parse(it) }
            imagen.setImageURI(imageUri)
        }
    }

    private fun isUsernameValid(): Boolean {
        val username = nombre.text.toString()

        // Validate the length of the username
        if (username.length !in 6..20) {
            Toast.makeText(activity, "El nombre de usuario tiene que tener entre 6 y 20 caracteres", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun isPasswordValid(): Boolean {
        val password = contrasena.text.toString()

        // Validate the length of the password
        return if (password.length !in 6..20) {
            Toast.makeText(activity, "La contraseña tiene que tener entre 6 y 20 caracteres", Toast.LENGTH_SHORT).show()
            false
        } else true
    }

    companion object {
        private const val REQUEST_IMAGE_CAPTURE = 1
    }
}

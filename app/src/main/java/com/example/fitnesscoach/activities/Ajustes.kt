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
    // Inicializa la instancia de Firebase Firestore
    private val db = FirebaseFirestore.getInstance()

    // Declara los elementos de la interfaz de usuario
    private lateinit var boton1: Button
    private lateinit var boton2: Button
    private lateinit var boton3: Button
    private lateinit var boton4: Button
    private lateinit var imagen: ImageView
    private lateinit var nombre: EditText
    private lateinit var contrasena: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Infla el diseño para este fragmento
        val view = inflater.inflate(R.layout.ajustes, container, false)

        // Inicializa los elementos de la interfaz de usuario
        boton1 = view.findViewById(R.id.BorrarDatos)
        boton2 = view.findViewById(R.id.CambiarDatos)
        boton3 = view.findViewById(R.id.cambiarFoto)
        boton4 = view.findViewById(R.id.MostrarDatos)
        imagen = view.findViewById(R.id.imageView2)
        nombre = view.findViewById(R.id.NombreUser)
        contrasena = view.findViewById(R.id.editContrasena)

        // Establece la visibilidad de los botones y la imagen
        boton1.visibility = View.VISIBLE
        boton2.visibility = View.VISIBLE
        imagen.visibility = View.VISIBLE

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var username = arguments?.getString("username")

        // Establece el listener de clic para el botón 'Eliminar datos'
        boton1.setOnClickListener {
            val username = arguments?.getString("username")
            if (username != null) {
                val docRef = db.collection("usuarios").document(username)
                docRef.get()
                    .addOnSuccessListener { document ->
                        if (document.exists()) {
                            // Si el usuario existe, elimina el documento
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

        // Establece el listener de clic para el botón 'Cambiar datos'
        boton2.setOnClickListener {
            val username = arguments?.getString("username")

            // Valida el nombre de usuario y la contraseña antes de actualizar los datos
            if (!isUsernameValid()) return@setOnClickListener
            if (!isPasswordValid()) return@setOnClickListener

            // Crea un mapa con los nuevos datos
            val data = hashMapOf(
                "nombre" to nombre.text.toString(),
                "contrasena" to contrasena.text.toString()
            )

            // Actualiza el documento de Firestore con los nuevos datos
            db.collection("usuarios").document(username.toString()).set(data).addOnSuccessListener {
                Toast.makeText(requireContext(), "Datos cambiados", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(requireContext(), "Algo ha fallado...", Toast.LENGTH_SHORT).show()
            }
        }

        // Establece el listener de clic para el botón 'Cambiar foto'
        boton3.setOnClickListener {
            val intent = Intent(activity, Camera::class.java)
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
        }

        // Establece el listener de clic para el botón 'Mostrar datos'
        boton4.setOnClickListener {
            val username = arguments?.getString("username")
            if (username != null) {
                val docRef = db.collection("usuarios").document(username)
                docRef.get()
                    .addOnSuccessListener { document ->
                        if (document.exists()) {
                            val nom = document.get("nombre")
                            val contra = document.get("contrasena")
                            // Muestra el nombre y la contraseña del usuario en un mensaje Toast
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
            // Recupera la URI de la imagen y la establece en el ImageView
            val imageUri: Uri? = data?.getStringExtra("image_uri")?.let { Uri.parse(it) }
            imagen.setImageURI(imageUri)
        }
    }

    private fun isUsernameValid(): Boolean {
        val username = nombre.text.toString()

        // Valida la longitud del nombre de usuario
        if (username.length !in 6..20) {
            Toast.makeText(activity, "El nombre de usuario tiene que tener entre 6 y 20 caracteres", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun isPasswordValid(): Boolean {
        val password = contrasena.text.toString()

        // Valida la longitud de la contraseña
        return if (password.length !in 6..20) {
            Toast.makeText(activity, "La contraseña tiene que tener entre 6 y 20 caracteres", Toast.LENGTH_SHORT).show()
            false
        } else true
    }

    companion object {
        const val REQUEST_IMAGE_CAPTURE = 1
    }
}

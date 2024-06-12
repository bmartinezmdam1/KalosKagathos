package com.example.fitnesscoach.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.fitnesscoach.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : Activity() {
    // Inicializar Firebase Firestore
    private val db = FirebaseFirestore.getInstance()
    private lateinit var editTextUsername: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var nombre: String
    private lateinit var contrasena: String
    private lateinit var Confirmarcontrasena: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        // Obtener referencias de las vistas
        editTextUsername = findViewById(R.id.editTextUsername)
        editTextPassword = findViewById(R.id.editContrasena)
        editTextEmail = findViewById(R.id.editTextCorreo)
        val buttonEmpezar = findViewById<Button>(R.id.buttonEMPEZAR)
        val buttonRegistro = findViewById<Button>(R.id.buttonRegistrar)
        val bundle: Bundle? = intent.extras
        val email: String? = bundle?.getString("email")

        // Listener para el botón de registro
        buttonRegistro.setOnClickListener {
            startActivity(Intent(this, RegistroActivity::class.java))
        }
    }

    // Función para manejar el evento de clic en el botón de 'Empezar'
    fun btnEmpezar(view: View) {
        // Obtener el nombre de usuario y la contraseña ingresados por el usuario
        val username = editTextUsername.text.toString().trim()
        val password = editTextPassword.text.toString().trim()
        val correo =   editTextEmail.text.toString().trim()
        // Verificar si los campos están vacíos
        if (editTextUsername.text.isEmpty() || editTextPassword.text.isEmpty() || editTextEmail.text.isEmpty()) {
            Toast.makeText(this, "Debe ingresar todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        // Acceder a la base de datos para verificar las credenciales del usuario
        db.collection("usuarios").document(correo).get()
            .addOnFailureListener {
                // Manejar el caso de error al acceder a la base de datos
                Toast.makeText(this, "Error al acceder a la base de datos. Contacte con su administrador.", Toast.LENGTH_SHORT).show()
            }
            .addOnSuccessListener { document ->
                // Verificar si el documento del usuario existe en la base de datos
                if (document.data != null) {
                    val nombreDeUsuario = document.getString("nombre")
                    val storedPassword = document.getString("contrasena")
                    // Verificar si la contraseña ingresada coincide con la almacenada en la base de datos
                    if (storedPassword.equals(password) && nombreDeUsuario.equals(nombreDeUsuario)) {
                        // Iniciar actividad de bienvenida o pantalla principal y pasar el nombre de usuario como argumento
                        val bundle = Bundle()
                        bundle.putString("username", username)
                        bundle.putString("correo", correo)
                        val intent = Intent(this, InicioActivity::class.java)
                        intent.putExtras(bundle)
                        startActivity(intent)
                    } else {
                        // Mostrar mensaje de contraseña incorrecta
                        Toast.makeText(this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Mostrar mensaje si el usuario no existe en la base de datos
                    Toast.makeText(this, "El usuario no existe", Toast.LENGTH_SHORT).show()
                }
            }
    }
}

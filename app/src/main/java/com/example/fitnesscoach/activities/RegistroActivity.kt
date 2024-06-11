package com.example.fitnesscoach.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.fitnesscoach.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class RegistroActivity : Activity() {
    // Instancia de Firestore
    private val db = FirebaseFirestore.getInstance()
    private lateinit var editTextNombre: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextContrasena: EditText
    private lateinit var editTextConfirmarContrasena: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registro)

        // Inicializar los campos de texto
        editTextNombre = findViewById(R.id.usernameEdit)
        editTextEmail = findViewById(R.id.emailEdit)
        editTextContrasena = findViewById(R.id.passwordEdit)
        editTextConfirmarContrasena = findViewById(R.id.confirmEdit)
    }

    fun btnRegistrar(view: View) {
        // Comprobar validez de los datos de registro
        if (!isUsernameValid()) return
        if (!isPasswordValid()) return
        val email = editTextEmail.text.toString()
        if (!isEmailValid(email)) return

        // Verificar si el email ya existe en la base de datos
        db.collection("usuarios").document(email).get()
            .addOnFailureListener {
                Toast.makeText(this, "Ha ocurrido un error durante el guardado. Contacte con su administrador.", Toast.LENGTH_SHORT).show()
            }
            .addOnSuccessListener { document ->
                if (document.data != null) {
                    Toast.makeText(this, "El email ya existe en la base de datos", Toast.LENGTH_SHORT).show()
                    return@addOnSuccessListener
                }

                // Guardar datos de registro en Firestore
                val data = hashMapOf(
                    "nombre" to editTextEmail.text.toString(),
                    "contrasena" to editTextContrasena.text.toString()
                )

                db.collection("usuarios").document(editTextEmail.text.toString()).set(data)
                    .addOnSuccessListener {
                        // Ir a la actividad de inicio de sesión después del registro exitoso
                        startActivity(Intent(this, LoginActivity::class.java))
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Ha ocurrido un error durante el guardado. Contacte con su administrador.", Toast.LENGTH_SHORT).show()
                    }
            }
    }

    private fun isUsernameValid(): Boolean {
        val username = editTextNombre.text.toString()

        // Verificar si el nombre de usuario tiene una longitud válida
        if (username.length !in 6..20) {
            Toast.makeText(this, "El nombre de usuario debe tener entre 6 y 20 caracteres", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun isEmailValid(email: String): Boolean {
        // Verificar si el formato del email es válido
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "El email no es correcto", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun isPasswordValid(): Boolean {
        // Verificar si las contraseñas tienen una longitud válida y coinciden
        val password = editTextContrasena.text.toString()
        val passwordConfirmar = editTextConfirmarContrasena.text.toString()

        return if (password.length !in 6..20 && passwordConfirmar.equals(password)) {
            Toast.makeText(this, "La contraseña debe tener entre 6 y 20 caracteres y las contraseñas deben ser iguales", Toast.LENGTH_SHORT).show()
            false
        } else true
    }
}

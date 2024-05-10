package com.example.fitnesscoach

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore

class Registro : Activity(){
    private  val db = FirebaseFirestore.getInstance()
    private lateinit var editTextNombre: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextContrasena: EditText
    private lateinit var editTextConfirmarContrasena: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registro)

        editTextNombre = findViewById(R.id.usernameEdit)
        editTextEmail = findViewById(R.id.emailEdit)
        editTextContrasena = findViewById(R.id.passwordEdit)
        editTextConfirmarContrasena = findViewById(R.id.ConfirmarContrasena)

        val buttonRegistro = findViewById<Button>(R.id.buttonRegistrarse)
        if (isPasswordValid() && isUsernameValid()) {
            buttonRegistro.setOnClickListener {
                val email = intent.getStringExtra("email")
                val activity = InicioActivity()
                val bundle = Bundle()
                bundle.putString("email", email)
                startActivity(Intent(this, LoginActivity::class.java))
                db.collection("usuarios").document(editTextEmail.text.toString()).set() {
                    hashMapOf(
                        "nombre" to editTextNombre.text.toString(),
                        "contrasena" to editTextContrasena.text.toString(),
                        "confirmarContrasena" to editTextConfirmarContrasena.text.toString()
                    )
                }
            }
        }
    }
    private fun isUsernameValid(): Boolean {
        val username = editTextNombre.text.toString()

        return if (username.length !in 6..20) {
            Toast.makeText(this, "El nombre de usuario debe tener entre 6 y 20 caracteres", Toast.LENGTH_SHORT).show()
            false
        }
        else true
    }
    private fun isPasswordValid(): Boolean {
        val password = editTextContrasena.text.toString()
        val passwordConfirmar = editTextConfirmarContrasena.text.toString()

        return if (password.length !in 6..20 && passwordConfirmar.equals(password)) {
            Toast.makeText(this, "La contraseña debe tener entre 6 y 20 caracteres y las contrasenas han de ser iguales", Toast.LENGTH_SHORT).show()
            false
        }
        else true
    }
}
package com.example.fitnesscoach

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
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
    }

    fun btnRegistrar(view: View) {
        // Comprobar nombre usuario
        if (!isUsernameValid()) return

        // Comprobar email válido y que no exista en Firebase
        if (!isEmailValid()) return

        // TODO: Comprobar contraseñas

        // Guardar datos de registo en Firebase
        db.collection("usuarios").document(editTextEmail.text.toString()).set() {
            hashMapOf(
                "nombre" to editTextNombre.text.toString(),
                "contrasena" to editTextContrasena.text.toString()
            )
        }

        // TODO: Redirigir a login (segunda versión que envíe el email y pass al login e inicie sesión automáticamente)



        // if (isPasswordValid() && isUsernameValid()) {

        /*
        val email = intent.getStringExtra("email")
        val activity = InicioActivity()
        val bundle = Bundle()
        bundle.putString("email", email)
        startActivity(Intent(this, LoginActivity::class.java))

         */
    }




    private fun isUsernameValid(): Boolean {
        val username = editTextNombre.text.toString()

        if (username.length !in 6..20) {
            Toast.makeText(this, "El nombre de usuario debe tener entre 6 y 20 caracteres", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun isEmailValid(): Boolean {
        val email = editTextEmail.text.toString()

        // TODO: Comprobar que el email cumpla con el formato de email
        if (false) {
            Toast.makeText(this, "El email no es correcto", Toast.LENGTH_SHORT).show()
            return false
        }

        db.collection("usuarios").document(editTextEmail.text.toString()).get()
        .addOnSuccessListener {
            // return true
        }.addOnFailureListener {
            Toast.makeText(this, "El email ya está registrado", Toast.LENGTH_SHORT).show()
            // return false
        }
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
package com.example.fitnesscoach

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

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

        if (!isPasswordValid()) return
        // Guardar datos de registo en Firebase
        try {
            db.collection("usuarios").document(editTextEmail.text.toString()).set() {
                hashMapOf(
                    "nombre" to editTextNombre.text.toString(),
                    "contrasena" to editTextContrasena.text.toString()
                )
            }
        }
        catch (e: Exception) {
            Log.e("Error", "Error al añadir usuario: $e")
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
        var flag = false
        val email = editTextEmail.text.toString()

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "El email no es correcto", Toast.LENGTH_SHORT).show()
            flag = false
        }

        CoroutineScope(Dispatchers.Main).launch {
                val documentSnapshot = withContext(Dispatchers.IO) {
                    db.collection("usuarios").document(email).get().await()
                }
                if (documentSnapshot.exists()) {
                    Toast.makeText(this@Registro, "El email ya está registrado", Toast.LENGTH_SHORT).show()
                    flag = false
                }
            else {
                    flag = true
                }

        }
        return flag
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
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
    private  val db = FirebaseFirestore.getInstance()
    private lateinit var editTextUsername: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var nombre:String
    private lateinit var contrasena:String
    private lateinit var Confirmarcontrasena:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        editTextUsername = findViewById(R.id.editTextUsername)
        editTextPassword = findViewById(R.id.editContrasena)
        val buttonEmpezar = findViewById<Button>(R.id.buttonEMPEZAR)
        val buttonRegistro = findViewById<Button>(R.id.buttonRegistrar)
        val bundle: Bundle? = intent.extras
        val email: String? = bundle?.getString("email")


        buttonRegistro.setOnClickListener {
                startActivity(Intent(this, RegistroActivity::class.java))
        }
    }

    fun btnEmpezar(view: View) {
        val username = editTextUsername.text.toString().trim()
        val password = editTextPassword.text.toString().trim()

        if (editTextUsername.text.isEmpty() || editTextPassword.text.isEmpty()) {
            Toast.makeText(this, "Debe ingresar todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        db.collection("usuarios").document(username).get()
            .addOnFailureListener {
                Toast.makeText(this, "Error al acceder a la base de datos. Contacte con su administrador.", Toast.LENGTH_SHORT).show()
            }
            .addOnSuccessListener { document ->
                if (document.data != null) {
                    val storedPassword = document.getString("contrasena")
                    if (storedPassword.equals(password)) {
                        // Iniciar actividad de bienvenida o pantalla principal
                        val bundle = Bundle()
                        bundle.putString("username", username)
                        val intent = Intent(this, InicioActivity::class.java)
                        intent.putExtras(bundle)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "El usuario no existe", Toast.LENGTH_SHORT).show()
                }
            }
    }


}

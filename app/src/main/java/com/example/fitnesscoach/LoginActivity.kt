package com.example.fitnesscoach

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore

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

        db.collection("usuarios")
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    nombre = document.getString("nombre").toString()
                    contrasena = document.getString("contraseña").toString()
                    Confirmarcontrasena = document.getString("confirmarContrasena").toString()

                }
            }
            .addOnFailureListener { exception ->
                // Manejar el error
            }
        buttonEmpezar.setOnClickListener {
            if (editTextUsername.text.toString().equals(nombre) && contrasena.toString().equals(contrasena)) {
                val username = intent.getStringExtra("username")
                val activity = InicioActivity()
                val bundle = Bundle()
                bundle.putString("username", username)
                //activity.arguments = bundle
                startActivity(Intent(this, InicioActivity::class.java))

            }
        }
        buttonRegistro.setOnClickListener {
                startActivity(Intent(this, Registro::class.java))
        }
    }

}

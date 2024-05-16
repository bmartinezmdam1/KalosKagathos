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
                startActivity(Intent(this, Registro::class.java))
        }
    }

    fun btnEmpezar(view: View) {
        if (editTextUsername.text.toString().equals(nombre) && contrasena.toString().equals(contrasena)) {
            val username = intent.getStringExtra("username")
            val activity = InicioActivity()
            val bundle = Bundle()
            bundle.putString("username", username)
            //activity.arguments = bundle
            startActivity(Intent(this, InicioActivity::class.java))

        }
    }

    fun comprobarUsuario(email: String) {
        var pasar = false
        CoroutineScope(Dispatchers.Main).launch {
            db.collection("usuarios")
                .whereEqualTo("email", email)
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        nombre = document.getString("nombre").toString()
                        contrasena = document.getString("contraseña").toString()
                    }
                }
                .addOnFailureListener { exception ->

                }

        }
    }

}

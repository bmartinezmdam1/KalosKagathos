package com.example.fitnesscoach

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LoginActivity : Activity() {
    private lateinit var editTextUsername: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        editTextUsername = findViewById(R.id.editTextUsername)
        val buttonEmpezar = findViewById<Button>(R.id.buttonEMPEZAR)
        buttonEmpezar.setOnClickListener {
            if (isUsernameValid()) {
                val username = intent.getStringExtra("username")
                val activity = InicioActivity()
                val bundle = Bundle()
                bundle.putString("username", username)
                //activity.arguments = bundle
                startActivity(Intent(this, InicioActivity::class.java))

            }
        }
    }

    private fun isUsernameValid(): Boolean {
        val username = editTextUsername.text.toString()

        return if (username.length !in 6..20) {
            Toast.makeText(this, "El nombre de usuario debe tener entre 6 y 20 caracteres", Toast.LENGTH_SHORT).show()
            false
        }
        else true
    }
}

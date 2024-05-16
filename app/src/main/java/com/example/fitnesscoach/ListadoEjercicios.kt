package com.example.fitnesscoach

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.mariana.kudage.adapters.UsuarioAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.internal.NoOpContinuation.context
import kotlinx.coroutines.launch
import kotlin.coroutines.jvm.internal.CompletedContinuation.context
import kotlin.random.Random


class ListadoEjercicios : AppCompatActivity()  {
    private lateinit var ejercicios: List<Exercise>
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recycler)

        recyclerView = findViewById(R.id.listado_recyclerView)
        rellenarRecyclerView()
    }

    fun rellenarRecyclerView() {

        val drawable = ContextCompat.getDrawable(context, R.drawable.principiante)
        // var localDatabase: LocalDatabase = LocalDatabase.getInstance(this)
        val db = FirebaseFirestore.getInstance()
        db.collection("ejercicios").get().addOnSuccessListener { documents ->
            for (document in documents) {
                var nombreEj = document.getString("nombre").toString()
                var repeticiones = document.getString("repeticiones").toString()
            };
        recyclerView.layoutManager = LinearLayoutManager(this)
        // recyclerView.layoutManager = GridLayoutManager(this, 3)

        GlobalScope.launch(Dispatchers.IO) {
            db.collection("ejercicios").get().addOnSuccessListener { documents ->
                for (document in documents) {
                    val nombreEjercicio = document.getString("nombre").toString()
                    val repeticiones = document.getString("repeticiones").toString()

                    val exercise = drawable?.let { Exercise(nombreEjercicio,repeticiones, it) }
                    ejercicios.plus(exercise)
                }
                recyclerView.adapter = UsuarioAdapter(ejercicios)
        }
    }

    fun volver(view: View) {
        finish()
    }
}}
}
package com.example.fitnesscoach.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnesscoach.R

class RecyclerFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el diseño de fragmento
        val view = inflater.inflate(R.layout.rutina, container, false)

        // Inicializar el RecyclerView
        recyclerView = view.findViewById(R.id.recycler)
        recyclerView.visibility = View.VISIBLE

        // Crear una lista de ejercicios
        val ejercicios = createEjercicios()

        // Crear un adaptador para el RecyclerView
        val adapter = EjerciciosAdapter(requireContext(), ejercicios)

        // Establecer el LayoutManager y el adaptador para el RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        // Agregar una decoración de divisor al RecyclerView
        val dividerItemDecoration = DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.divider)
        drawable?.let {
            dividerItemDecoration.setDrawable(it)
        }
        recyclerView.addItemDecoration(dividerItemDecoration)

        return view
    }

    // Método para crear una lista de ejercicios
    private fun createEjercicios(): List<Ejercicio> {
        return listOf(
            Ejercicio("Flexiones", "10 repeticiones", 10),
            Ejercicio("Fondos en paralelas", "10 repeticiones", 10),
            Ejercicio("Press militar", "10 repeticiones", 10),
            Ejercicio("Elevaciones laterales", "10 repeticiones", 10),
            Ejercicio("Extension de triceps", "10 repeticiones", 10),
            Ejercicio("Dominadas supinas", "10 repeticiones", 10),
            Ejercicio("Curl de biceps bayesian", "10 repeticiones", 10),
            Ejercicio("Curl de biceps martillo", "10 repeticiones", 10),
            Ejercicio("Sentadillas", "10 repeticiones", 10),
            Ejercicio("Extensiones de cuadriceps", "10 repeticiones", 10),
            Ejercicio("Curl femoral", "10 repeticiones", 10),
            Ejercicio("Crunch abdominal", "10 repeticiones", 10)
        )
    }
}

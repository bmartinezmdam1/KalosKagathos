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
        val view = inflater.inflate(R.layout.rutina, container, false)
        recyclerView = view.findViewById(R.id.recycler)
        recyclerView.visibility = View.VISIBLE
        val ejercicios = createEjercicios()
        val adapter = EjerciciosAdapter(requireContext(), ejercicios)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        val dividerItemDecoration = DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.divider)
        drawable?.let {
            dividerItemDecoration.setDrawable(it)
        }
        recyclerView.addItemDecoration(dividerItemDecoration)

        return view
    }

    private fun createEjercicios(): List<Ejercicio> {
        return listOf(
            Ejercicio("Flexiones", "20 repeticiones"),
            Ejercicio("Fondos en paralelas", "15 repeticiones"),
            Ejercicio("Press militar", "10 repeticiones"),
            Ejercicio("Elevaciones laterales", "20 repeticiones"),
            Ejercicio("Extension de triceps", "20 repeticiones"),
            Ejercicio("Dominadas supinas", "6 repeticiones"),
            Ejercicio("Curl de biceps bayesian", "12 repeticiones"),
            Ejercicio("Curl de biceps martillo", "12 repeticiones"),
            Ejercicio("Sentadillas", "12 repeticiones"),
            Ejercicio("Extensiones de cuadriceps", "12 repeticiones"),
            Ejercicio("Curl femoral", "12 repeticiones"),
            Ejercicio("Crunch abdominal", "12 repeticiones"),

        )
    }
}

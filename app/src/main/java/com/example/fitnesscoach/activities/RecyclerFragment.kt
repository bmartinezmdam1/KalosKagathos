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
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.rutina, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler)

        val sitiosInteres = Recycler()
        val adapter = EjerciciosAdapter(requireContext(), sitiosInteres)
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

    private fun Recycler(): List<Ejercicio> {
        return listOf(
            Ejercicio("Flexiones", "20", 1),
            Ejercicio("Sentadillas", "12", 2),
            Ejercicio("Crunch abdominal", "12", 3),
            Ejercicio("Dominadas supinas", "6", 4),
        )
    }
}

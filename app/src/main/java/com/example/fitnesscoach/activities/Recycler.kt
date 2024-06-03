package com.example.fitnesscoach.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnesscoach.R

class Recycler  : Fragment() {
        fun OnCreateView() {
           Recycler()
           }
    private fun Recycler(): List<Ejercicio> {
        return listOf(
            Ejercicio("Flexiones","20",1),
            Ejercicio("Sentadillas","12",2),
            Ejercicio("Crunch abdominal","12",3),
            Ejercicio("Dominadas supinas","6",4),
        )
    }
}
package com.example.fitnesscoach.activities

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnesscoach.R

class EjerciciosAdapter(private val context: Context, private val ejercicios: List<Ejercicio>) : RecyclerView.Adapter<EjerciciosAdapter.ViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val row: View = LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false)
            return ViewHolder(row)
        }
        override fun getItemCount() = ejercicios.size
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val ejercicio = ejercicios[position]
            holder.bindRow(ejercicio)
        }
        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val titleTextView: TextView = view.findViewById(R.id.titleTextView)
            val subtitleTextView: TextView = view.findViewById(R.id.subtitleTextView)
            fun bindRow(ejer: Ejercicio) {
                titleTextView.text = ejer.name.toString()
                subtitleTextView.text = ejer.repeticiones.toString()
            }
        }
    }

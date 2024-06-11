package com.example.fitnesscoach.activities

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnesscoach.R
import com.example.fitnesscoach.activities.Ejercicio

class EjerciciosAdapter(private val context: Context, private val ejercicios: List<Ejercicio>) : RecyclerView.Adapter<EjerciciosAdapter.ViewHolder>() {
    // Este método crea y devuelve una nueva instancia de ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Inflar el diseño de fila y crear una instancia de ViewHolder
        val row: View = LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false)
        return ViewHolder(row)
    }

    // Este método devuelve la cantidad de elementos en la lista de ejercicios
    override fun getItemCount() = ejercicios.size

    // Este método vincula los datos de un elemento en particular en la posición dada a la vista
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ejercicio = ejercicios[position]
        holder.bindRow(ejercicio)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Referencias a las vistas dentro del elemento de la lista
        val titleTextView: TextView = view.findViewById(R.id.titleTextView)
        val subtitleTextView: TextView = view.findViewById(R.id.subtitleTextView)

        // Este método enlaza los datos del ejercicio con las vistas del elemento de la lista
        fun bindRow(ejer: Ejercicio) {
            titleTextView.text = ejer.name.toString() // Establecer el nombre del ejercicio
            subtitleTextView.text = ejer.repeticiones.toString() // Establecer las repeticiones del ejercicio

            // Establecer el OnClickListener para este elemento
            itemView.setOnClickListener {
                // Aquí puedes manejar la acción cuando se hace clic en el elemento
                // Por ejemplo, puedes abrir una nueva actividad o mostrar un diálogo
                Toast.makeText(context, "Has hecho clic en ${ejer.name}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

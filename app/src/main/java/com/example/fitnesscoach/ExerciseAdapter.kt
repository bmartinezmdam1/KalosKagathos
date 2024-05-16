package com.mariana.kudage.adapters

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnesscoach.Exercise
import com.example.fitnesscoach.R


class UsuarioAdapter(private val listaExercise: List<Exercise>) : RecyclerView.Adapter<UsuarioAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvNombre: TextView = view.findViewById(R.id.listName)
        private val tvRepeticiones: TextView = view.findViewById(R.id.listrepeticiones)

        fun bind(ejericio: Exercise) {
            tvNombre.text = "Nombre: ${ejericio.nombre}"
            tvRepeticiones.text = "Repeticiones: ${ejericio.repeticiones}"
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val exercise: Exercise = listaExercise[position]
        viewHolder.bind(exercise)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = listaExercise.size
}
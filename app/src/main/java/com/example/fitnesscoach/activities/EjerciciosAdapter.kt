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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val row: View = LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false)
        return ViewHolder(row)
    }

    override fun getItemCount() = ejercicios.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ejercicio = ejercicios[position]
        holder.bindRow(ejercicio)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.titleTextView)
        val subtitleTextView: TextView = view.findViewById(R.id.subtitleTextView)

        fun bindRow(ejer: Ejercicio) {
            titleTextView.text = ejer.name.toString()
            subtitleTextView.text = ejer.repeticiones.toString()

            // Establecer el OnClickListener para este elemento
            itemView.setOnClickListener {
                // Aquí puedes manejar la acción cuando se hace clic en el elemento
                // Por ejemplo, puedes abrir una nueva actividad o mostrar un diálogo
                Toast.makeText(context, "Haz hecho clic en ${ejer.name}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

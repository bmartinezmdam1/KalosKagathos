package com.example.fitnesscoach

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExerciseAdapter(private val exerciseList: List<Exercise>) : RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.listitem, parent, false)
        return ExerciseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = exerciseList[position]
        holder.bind(exercise)
    }

    override fun getItemCount(): Int {
        return exerciseList.size
    }

    inner class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val listImage: ImageView = itemView.findViewById(R.id.listImage)
        private val listName: TextView = itemView.findViewById(R.id.listName)
        private val listTime: TextView = itemView.findViewById(R.id.listTime)

        fun bind(exercise: Exercise) {
            listImage.setImageResource(exercise.image)
            listName.text = exercise.name
            listTime.text = exercise.time
        }
    }
}

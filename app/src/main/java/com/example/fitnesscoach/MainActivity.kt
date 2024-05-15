package com.example.fitnesscoach

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnesscoach.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ExerciseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        val exerciseList = ArrayList<Exercise>()
        val imageList = arrayOf(R.drawable.principiante, R.drawable.principiante, R.drawable.principiante)
        val nameList = arrayOf("Flexiones", "Sentadillas", "Plancha", "Burpees", "Abdominales")

        for (i in imageList.indices) {
            exerciseList.add(Exercise(imageList[i],"30 mins",nameList[i] ))
        }

        adapter = ExerciseAdapter(exerciseList)
        recyclerView.adapter = adapter
    }
}


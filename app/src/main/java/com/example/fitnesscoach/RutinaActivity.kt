package com.example.fitnesscoach

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RutinaActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ExerciseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val exerciseList = ArrayList<Exercise>()
        exerciseList.add(Exercise(1, "flexiones","rir 0 con tu propio peso corporal"))
        exerciseList.add(Exercise(2, "sentadillas","rir 0 con tu propio peso corporal"))
        exerciseList.add(Exercise(3, "dominadas","rir 0 con tu propio peso corporal"))

        adapter = ExerciseAdapter(exerciseList)
        recyclerView.adapter = adapter
    }
}

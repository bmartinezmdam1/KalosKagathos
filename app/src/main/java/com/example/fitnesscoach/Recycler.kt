package com.example.customlistview

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnesscoach.R

class Recycler : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ExerciseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var RecyclerItemClickListener
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val exerciseList = ArrayList<Exercise>()
        val imageList = arrayOf(R.drawable.principiante, R.drawable.principiante)
        val nameList = arrayOf("Flexiones", "Sentadillas", "Plancha", "Burpees", "Abdominales")

        for (i in imageList.indices) {
            exerciseList.add(Exercise(nameList[i], "30 mins", imageList[i]))
        }

        adapter = ExerciseAdapter(exerciseList)
        recyclerView.adapter = adapter

        recyclerView.addOnItemTouchListener(
            RecyclerItemClickListener(
                this,
                recyclerView,
                object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        val intent = Intent(this@Recycler, DetailedActivity::class.java)
                        intent.putExtra("name", nameList[position])
                        intent.putExtra("time", "30 mins")
                        intent.putExtra("image", imageList[position])
                        startActivity(intent)
                    }

                    override fun onLongItemClick(view: View?, position: Int) {}
                })
        )
    }
}

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

data class Exercise(val name: String, val time: String, val image: Int)

class DetailedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra("name")
        val time = intent.getStringExtra("time")
        val image = intent.getIntExtra("image", R.drawable.Principiante)

        binding.detailName.text = name
        binding.detailTime.text = time
        binding.detailImage.setImageResource(image)
    }

    fun inflate(layoutInflater: LayoutInflater): DetailedActivity {

    }
}

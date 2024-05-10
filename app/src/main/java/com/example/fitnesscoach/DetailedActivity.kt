package com.example.fitnesscoach


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.fitnesscoach.DetailedActivity

class DetailedActivity : AppCompatActivity() {

    private lateinit var binding: com.example.customlistview.DetailedActivity

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
}

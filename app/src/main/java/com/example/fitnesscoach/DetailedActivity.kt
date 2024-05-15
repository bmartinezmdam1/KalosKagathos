package com.example.fitnesscoach


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.fitnesscoach.DetailedActivity

class DetailedActivity : AppCompatActivity() {
    private var _binding: ? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = item.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra("name")
        val time = intent.getStringExtra("time")
        val image = intent.getIntExtra("image", R.drawable.principiante)

        binding.detailName.text = name
        binding.detailTime.text = time
        binding.detailImage.setImageResource(image)
    }
}

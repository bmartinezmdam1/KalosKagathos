package com.example.fitnesscoach

import android.os.Bundle

class item {
    private lateinit var binding: item

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = item.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    private fun setContentView(view: Any) {

    }
}
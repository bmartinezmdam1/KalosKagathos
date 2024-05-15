package com.example.fitnesscoach

import android.media.Image

class Exercise(s0: Int, s: String, s1: String) {
    val time: CharSequence?
    val name: CharSequence?
    val image: Int

    data class Exercise(val image: Image, val name: String, val reps: String)
}
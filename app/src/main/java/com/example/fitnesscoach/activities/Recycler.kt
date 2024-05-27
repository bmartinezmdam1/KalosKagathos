package com.example.fitnesscoach.activities

import android.app.Activity

class Recycler  : Activity() {
    private fun Recycler(): List<Ejercicio> {
        return listOf(
            Ejercicio("Flexiones","20",1),
            Ejercicio("Sentadillas","12",2),
            Ejercicio("Crunch abdominal","12",3),
            Ejercicio("Dominadas supinas","6",4),
        )
    }
}
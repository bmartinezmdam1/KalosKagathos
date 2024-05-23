package com.example.fitnesscoach.data

import android.graphics.drawable.Drawable
import android.media.Image
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class Exercise(
    @ColumnInfo(name = "NOMBRE") val nombre: String,
    @ColumnInfo(name = "REPETICIONES") val repeticiones: String,
    @ColumnInfo(name = "IMAGEN") val imagen: Drawable
)
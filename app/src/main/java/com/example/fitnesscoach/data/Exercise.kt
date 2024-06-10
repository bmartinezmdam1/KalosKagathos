package com.example.fitnesscoach.data

import android.content.Context
import android.graphics.drawable.Drawable
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.example.fitnesscoach.R

data class Exercise(
    @ColumnInfo(name = "NOMBRE") val nombre: String,
    @ColumnInfo(name = "REPETICIONES") val repeticiones: String,
    @ColumnInfo(name = "IMAGEN") val imagen: Drawable)


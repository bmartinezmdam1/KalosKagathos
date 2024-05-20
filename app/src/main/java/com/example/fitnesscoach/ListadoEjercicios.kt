package com.example.fitnesscoach

import android.content.res.Configuration
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.mariana.kudage.adapters.UsuarioAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.internal.NoOpContinuation.context
import kotlinx.coroutines.launch
import kotlin.coroutines.jvm.internal.CompletedContinuation.context
import kotlin.random.Random


class ListadoEjercicios : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener  {
    private lateinit var ejercicios: List<Exercise>
    private lateinit var recyclerView: RecyclerView
    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recycler)

        recyclerView = findViewById(R.id.listado_recyclerView)
        rellenarRecyclerView()
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        drawer = findViewById(R.id.drawer_layout)

        toggle = ActionBarDrawerToggle(this,drawer,toolbar,R.string.perfil)
        drawer.addDrawerListener(toggle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        val navigationView: NavigationView = findViewById(R.id.nav_item_one)
        navigationView.setNavigationItemSelectedListener(this)
    }

    fun rellenarRecyclerView() {

        val drawable = ContextCompat.getDrawable(context, R.drawable.principiante)
        // var localDatabase: LocalDatabase = LocalDatabase.getInstance(this)
        val db = FirebaseFirestore.getInstance()
        db.collection("ejercicios").get().addOnSuccessListener { documents ->
            for (document in documents) {
                var nombreEj = document.getString("nombre").toString()
                var repeticiones = document.getString("repeticiones").toString()
            };
        recyclerView.layoutManager = LinearLayoutManager(this)
        // recyclerView.layoutManager = GridLayoutManager(this, 3)

        GlobalScope.launch(Dispatchers.IO) {
            db.collection("ejercicios").get().addOnSuccessListener { documents ->
                for (document in documents) {
                    val nombreEjercicio = document.getString("nombre").toString()
                    val repeticiones = document.getString("repeticiones").toString()

                    val exercise = drawable?.let { Exercise(nombreEjercicio,repeticiones, it) }
                    ejercicios.plus(exercise)
                }
                recyclerView.adapter = UsuarioAdapter(ejercicios)
        }
    }

    fun volver(view: View) {
        finish()
    }
}}
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_item_one -> {
                val fragment = ajustes()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_item_one, fragment)
                    .commit()
            }
        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onPostCreate(savedInstanceState, persistentState)
        toggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toggle.onConfigurationChanged(newConfig)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onContextItemSelected(item)
    }
}
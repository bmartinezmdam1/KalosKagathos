package com.example.fitnesscoach

import android.content.res.Configuration
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawer: DrawerLayout
    private lateinit var tooggle: ActionBarDrawerToggle
     fun OnCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.recycler)

         val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_main)
         setSupportActionBar(toolbar)

         drawer = findViewById(R.id.drawer_layout)

         tooggle = ActionBarDrawerToggle(this,drawer,toolbar,R.string.perfil)
         drawer.addDrawerListener(tooggle)
         supportActionBar?.setDisplayHomeAsUpEnabled(true)
         supportActionBar?.setHomeButtonEnabled(true)

         val navigationView: NavigationView = findViewById(R.id.nav_item_one)
         navigationView.setNavigationItemSelectedListener(this)
    }



    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.nav_item_one -> Toast.makeText(this,"item1",Toast.LENGTH_SHORT).show()
        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onPostCreate(savedInstanceState, persistentState)
        tooggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        tooggle.onConfigurationChanged(newConfig)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        if (tooggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onContextItemSelected(item)
    }
}



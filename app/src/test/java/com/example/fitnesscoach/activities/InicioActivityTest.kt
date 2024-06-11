package com.example.fitnesscoach.activities

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.fitnesscoach.R
import com.google.common.base.Verify.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

class InicioActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(InicioActivity::class.java)

    private lateinit var notificationManager: NotificationManagerCompat


    @Test
    fun TestDrawable() {
        val context: Context = ApplicationProvider.getApplicationContext()

        val bitmap: Bitmap = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888)

        val drawable = BitmapDrawable(context.resources, bitmap)

        // Ver drawable no es null
        assertNotNull(drawable)

        assertTrue(drawable is BitmapDrawable)

        val bitmapFromDrawable = (drawable as BitmapDrawable).bitmap
        assertNotNull(bitmapFromDrawable)
    }

    fun testActivityLaunch() {
        //el código es una copia de lo que se ve en los apuntes, sin embargo, no funciona
        //fun test_MainActivity_carga() {
        //val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        //onView(withId(R.id.main)).check(matches(isDisplayed()))
        //}
        // Lanza la actividad InicioActivity
        val scenario = ActivityScenario.launch(InicioActivity::class.java)
        // Verifica que la vista con ID toolbar se muestre
        onView(ViewMatchers.withId(R.id.container)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}

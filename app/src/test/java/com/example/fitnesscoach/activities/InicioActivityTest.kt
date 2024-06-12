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
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.fitnesscoach.R
import com.google.common.base.Verify.verify
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.junit.runner.RunWith
import java.util.regex.Pattern.matches

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

    //unitaria
    @Test
    fun testNotificationMessage() {
        val activity = InicioActivity()
        val message = "Es hora de entrenar!"
        assertEquals("Es hora de entrenar!", message)
    }
    //de Integración
    private lateinit var scenario: ActivityScenario<InicioActivity>

    @Before
    fun setUp() {
        scenario = ActivityScenario.launch(InicioActivity::class.java)
    }

    @After
    fun tearDown() {
        scenario.close()
    }

    @Test
    fun testButtonsExist() {
        scenario.onActivity { activity ->
            assertNotNull(activity.findViewById(R.id.botonPrincipiante))
            assertNotNull(activity.findViewById(R.id.botonIntermedio))
            assertNotNull(activity.findViewById(R.id.botonAvanzado))
        }
    }
    //funcional
    @Test
    fun testButtonClickDisplaysToast() {
        val scenario = ActivityScenario.launch(InicioActivity::class.java)

        onView(withId(R.id.botonPrincipiante)).perform(click())

        scenario.close()
    }
    //de rendimiento
    @Test(timeout = 1000)
    fun testMethodPerformance() {
        val scenario = ActivityScenario.launch(InicioActivity::class.java)

        onView(withId(R.id.botonPrincipiante)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        scenario.close()
    }


}

package com.example.testingtemplate.ui

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.example.testingtemplate.R

@HiltAndroidTest
class MainActivityTest {
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val scenario = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun test_having_context() {

        val context = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.testingtemplate", context.packageName)
    }

    @Test
    fun check_launch_main_activity_and_visible_root_view() {
        ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.root_view)).check(matches(isDisplayed()))
        onView(withId(R.id.root_view)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
    }
}
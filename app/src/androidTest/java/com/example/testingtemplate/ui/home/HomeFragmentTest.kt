package com.example.testingtemplate.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.databinding.ViewDataBinding
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.Visibility
import androidx.test.espresso.matcher.ViewMatchers.hasChildCount
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.testingtemplate.R
import com.example.testingtemplate.launchFragmentInHiltContainer
import com.example.testingtemplate.ui.base.BaseAdapter
import com.example.testingtemplate.utils.Resource
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
internal class HomeFragmentTest {

    @get:Rule(order = 0)
    val hiltAndroidRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        hiltAndroidRule.inject()
    }

    @Test
    fun launch_home_fragment_and_check_recycler_view_visible() {
        launchFragmentInHiltContainer<HomeFragment>()
        onView(withId(R.id.quote_recycler_view)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

    @Test
    fun test_home_fragment_with_loading_state() {
        launchFragmentInHiltContainer<HomeFragment> {
            renderUi(Resource.Loading)
        }
        onView(withId(R.id.progress_circular)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.quote_recycler_view)).check(matches(withEffectiveVisibility(Visibility.GONE)))
    }

    @Test
    fun test_home_fragment_with_success_state() {
        launchFragmentInHiltContainer<HomeFragment> {
            //Default FakeRepository provide success response with 3 items.
        }
        onView(withId(R.id.progress_circular)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.quote_recycler_view)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.quote_recycler_view)).check(matches(hasChildCount(3)))
    }

    @Test
    fun test_home_fragment_with_error_state_by_showing_toast() {
        val msg = "failed to load"
        launchFragmentInHiltContainer<HomeFragment> {
            renderUi(Resource.Failed(msg))
        }
//        onView(withText(msg)).inRoot(ToastMatcher()).check(matches(isDisplayed()))
    }

    @Test
    fun test_home_fragment_recycler_item_click() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        launchFragmentInHiltContainer<HomeFragment> {
            navController.setGraph(R.navigation.app_navigation)
            Navigation.setViewNavController(requireView(), navController)
        }
        onView(withId(R.id.quote_recycler_view)).perform(
            RecyclerViewActions.actionOnItemAtPosition<BaseAdapter.BaseViewHolder<ViewDataBinding>>(
                2, click()
            )
        )
        assertThat(navController.currentDestination?.id).isEqualTo(R.id.detailFragment)
    }

    @Test
    fun test_home_fragment_to_detail_fragment_back_to_home_fragment() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        launchFragmentInHiltContainer<HomeFragment> {
            navController.setGraph(R.navigation.app_navigation)
            Navigation.setViewNavController(requireView(), navController)
        }
        onView(withId(R.id.quote_recycler_view)).perform(
            RecyclerViewActions.actionOnItemAtPosition<BaseAdapter.BaseViewHolder<ViewDataBinding>>(
                2, click()
            )
        )
        assertThat(navController.currentDestination?.id).isEqualTo(R.id.detailFragment)
        navController.popBackStack()
        assertThat(navController.currentDestination?.id).isEqualTo(R.id.homeFragment)
    }

}
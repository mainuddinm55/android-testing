package com.example.testingtemplate.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.testingtemplate.R
import com.example.testingtemplate.data.models.Quote
import com.example.testingtemplate.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class DetailFragmentTest {

    @get:Rule(order = 0)
    val hiltAndroidRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val quote = Quote(
        "ABAD",
        "Md Mainuddin3",
        "mainuddinm55",
        "This is just also dummy content",
        emptyList(),
        23,
        "2023-07-05",
        "2023-07-05"
    )
    private val bundle = DetailFragmentArgs(quote)

    @Before
    fun setup() {
        hiltAndroidRule.inject()
    }

    @Test
    fun test_detail_fragment_launched_success() {
        launchFragmentInHiltContainer<DetailFragment>(fragmentArgs = bundle.toBundle()) {

        }
        onView(withId(R.id.root_view)).check(matches(isDisplayed()))
    }

    @Test
    fun test_detail_fragment_launched_show_view() {
        launchFragmentInHiltContainer<DetailFragment>(bundle.toBundle()) {

        }
        onView(withId(R.id.author_text_view)).check(matches(isDisplayed()))
        onView(withId(R.id.author_slug_text_view)).check(matches(isDisplayed()))
        onView(withId(R.id.quote_text_view)).check(matches(isDisplayed()))
    }

    @Test
    fun test_detail_fragment_all_content_matched() {
        launchFragmentInHiltContainer<DetailFragment>(bundle.toBundle()) {

        }
        onView(withId(R.id.author_text_view)).check(matches(withText(quote.author)))
        onView(withId(R.id.author_slug_text_view)).check(matches(withText("@${quote.authorSlug}")))
        onView(withId(R.id.quote_text_view)).check(matches(withText("\"\n${quote.content}\n\"")))
    }
}
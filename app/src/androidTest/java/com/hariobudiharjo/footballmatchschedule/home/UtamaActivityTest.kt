package com.hariobudiharjo.footballmatchschedule.home

import android.support.test.InstrumentationRegistry.getInstrumentation
import android.support.test.espresso.Espresso.*
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.rule.ActivityTestRule
import android.support.v7.widget.RecyclerView
import com.hariobudiharjo.footballmatchschedule.Home.UtamaActivity
import com.hariobudiharjo.footballmatchschedule.R
import com.hariobudiharjo.footballmatchschedule.R.id.*

import org.junit.Rule
import org.junit.Test

class UtamaActivityTest {
    @Rule
    @JvmField
    var activityRule = ActivityTestRule(UtamaActivity::class.java)

    @Test
    fun testRecyclerViewBehaviour() {
        Thread.sleep(3000)
        onView(withId(rv_prev_match)).check(matches(isDisplayed()))
        onView(withId(rv_prev_match)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(1))
        onView(withId(rv_prev_match)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))
    }

    @Test
    fun testFavorite() {
        Thread.sleep(2000)

//        onView(withId(navigation_favorite)).check(matches(isDisplayed()))
//        onView(withId(navigation_dashboard)).check(matches(isDisplayed()))
//        onView(withId(navigation_home)).check(matches(isDisplayed()))

        onView(withId(rv_prev_match)).check(matches(isDisplayed()))
        onView(withId(rv_prev_match)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(1))
        onView(withId(rv_prev_match)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))
        Thread.sleep(2000)
        onView(withId(action_favorite_ab)).check(matches(isDisplayed()))
        onView(withId(action_favorite_ab)).perform(click())

        onView(withId(action_favorite_ab)).check(matches(isDisplayed()))
        onView(withId(action_favorite_ab)).perform(click())

        pressBack()

        Thread.sleep(2000)

        onView(withId(navigation)).check(matches(isDisplayed()))
        onView(withId(navigation_favorite)).perform(click())
    }
}
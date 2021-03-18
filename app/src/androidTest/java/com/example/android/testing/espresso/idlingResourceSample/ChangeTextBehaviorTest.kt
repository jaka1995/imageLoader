package com.example.android.testing.espresso.idlingResourceSample

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.android.testing.espresso.idlingResourceSample.image.ImageLoader
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class ChangeTextBehaviorTest {
    @Rule @JvmField var rule = PicassoTestRule()

    /**
     * Use [to launch and get access to the activity.][ActivityScenario.onActivity]
     */
    @Before
    fun registerIdlingResource() {
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    @AwaitImageLoad
    fun changeText_sameActivity() {
        onView(withId(R.id.btn)).perform(ViewActions.click())
        onView(withId(R.id.image)).check(matches(withBackgroundLoaded(ImageLoader.ImageLoadingState.Success)))
    }
}
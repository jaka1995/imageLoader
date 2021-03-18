package com.example.android.testing.espresso.idlingResourceSample

import android.view.View
import com.example.android.testing.espresso.idlingResourceSample.image.ImageLoader
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

fun withBackgroundLoaded(state: ImageLoader.ImageLoadingState) = object : TypeSafeMatcher<View>() {

    override fun describeTo(description: Description?) {
        description?.appendText("view has a ImageLoaded tag equals to $state")
    }

    override fun matchesSafely(item: View?): Boolean {   val tag: ImageLoader.ImageLoadingState? = (item?.getTag(ImageLoader.IMAGE_LOADER_TAG) as? ImageLoader.ImageLoadingState)
        return (tag != null && state == tag)
    }
}
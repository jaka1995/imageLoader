package com.example.android.testing.espresso.idlingResourceSample.idlingResource

import android.os.Handler
import androidx.test.espresso.IdlingResource
import java.util.concurrent.atomic.AtomicInteger


class ImageLoaderIdleingResource: IdlingResource {

    private val counter = AtomicInteger()

    private var mCallback: IdlingResource.ResourceCallback? = null
    private val DELAY_MILLIS = 3000L

    override fun getName(): String {
        return "ImageLoaderIdleResource"
    }

    override fun isIdleNow(): Boolean {
        val idle = counter.get() == 0
        if (idle && mCallback != null) mCallback!!.onTransitionToIdle()
        return idle

    }

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
        mCallback = callback
    }

    fun incrementLoading() {
        val counterVal = counter.getAndIncrement()
        if (counterVal == 0 && mCallback != null) {
            mCallback!!.onTransitionToIdle()
        }
    }

    fun decrementLoading() {
        val counterVal = counter.getAndDecrement()
        if (counterVal == 0 && mCallback != null) {
            mCallback!!.onTransitionToIdle()

            val handler = Handler()
            handler.postDelayed({
            }, DELAY_MILLIS)
        }
    }
}
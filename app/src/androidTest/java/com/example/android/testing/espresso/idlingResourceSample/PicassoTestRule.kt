package com.example.android.testing.espresso.idlingResourceSample

import androidx.test.espresso.IdlingRegistry
import com.example.android.testing.espresso.idlingResourceSample.idlingResource.ImageLoaderIdleingResource
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class AwaitImageLoad

class PicassoTestRule : TestRule {

    private val imageLoader = App.appComponent.imageLoader()

    override fun apply(base: Statement, description: Description): Statement = object : Statement() {
        override fun evaluate() {
            val annotation = description.getAnnotation(AwaitImageLoad::class.java)

            if (annotation != null) {
            val picassoIdlingResource = ImageLoaderIdleingResource()
            imageLoader.registerIdleResource(picassoIdlingResource)
            IdlingRegistry.getInstance().register(picassoIdlingResource)
            base.evaluate()
            IdlingRegistry.getInstance().unregister(picassoIdlingResource)
            imageLoader.unregisterIdleResource(picassoIdlingResource)
            } else {
                base.evaluate()
            }
        }
    }
}
package com.example.android.testing.espresso.idlingResourceSample.image

import android.support.annotation.DrawableRes
import android.widget.ImageView
import com.example.android.testing.espresso.idlingResourceSample.idlingResource.ImageLoaderIdleingResource
import com.example.android.testing.espresso.idlingResourceSample.R

interface ImageLoader {

    fun load(url: String): RequestBuilder

    fun load(@DrawableRes drawableId: Int): RequestBuilder

    fun registerIdleResource(imageLoaderIdleResource: ImageLoaderIdleingResource)

    fun unregisterIdleResource(imageLoaderIdleResource: ImageLoaderIdleingResource)

    /**
     * Configurable image loading Request class made with Builder pattern
     * Descendants must implement several `into` methods using user-provided configuration
     */
    abstract class RequestBuilder {
        protected var url: String? = null
        protected var isAsCircle = false
        protected var isCenterCrop = true

        @DrawableRes
        protected var drawableId = 0

        @DrawableRes
        protected var placeholderId = 0

        @DrawableRes
        protected var errorId = 0
        protected var callback: Callback? = null

        constructor(url: String?) {
            this.url = url
        }

        constructor(drawableId: Int) {
            this.drawableId = drawableId
        }

        fun placeholder(@DrawableRes placeholderId: Int): RequestBuilder {
            this.placeholderId = placeholderId
            return this
        }

        fun nonCenterCrop(): RequestBuilder {
            isCenterCrop = false
            return this
        }

        fun error(@DrawableRes errorId: Int): RequestBuilder {
            this.errorId = errorId
            return this
        }

        fun asCircle(): RequestBuilder {
            isAsCircle = true
            return this
        }

        fun callback(callback: Callback): RequestBuilder {
            this.callback = callback
            return this
        }

        abstract fun into(imageView: ImageView)

        interface Callback {
            fun onSuccess()
            fun onError(e: Exception?)
        }
    }

    sealed class ImageLoadingState {
        object Loading : ImageLoadingState()
        object Success : ImageLoadingState()
        object Error : ImageLoadingState()
    }

    companion object {
        const val IMAGE_LOADER_TAG = R.id.image_loader_tag
    }
}
package com.example.android.testing.espresso.idlingResourceSample.image

import android.widget.ImageView
import com.example.android.testing.espresso.idlingResourceSample.idlingResource.ImageLoaderIdleingResource
import com.squareup.picasso.Picasso
import java.util.*


class PicassoImageLoader : ImageLoader {

    private val resources = Collections.synchronizedSet(HashSet<ImageLoaderIdleingResource>())
    private val loadingListener: (ImageLoader.ImageLoadingState) -> Unit = {
        when (it) {
            ImageLoader.ImageLoadingState.Loading -> resources.forEach { it.incrementLoading() }
            ImageLoader.ImageLoadingState.Success -> resources.forEach { it.decrementLoading() }
            ImageLoader.ImageLoadingState.Error -> resources.forEach { it.decrementLoading() }
        }
    }

    override fun load(url: String): ImageLoader.RequestBuilder = PicassoImageRequest(url, loadingListener)

    override fun load(drawableId: Int): ImageLoader.RequestBuilder = PicassoImageRequest(drawableId, loadingListener)

    override fun registerIdleResource(imageLoaderIdleResource: ImageLoaderIdleingResource) {
        resources.add(imageLoaderIdleResource)
    }

    override fun unregisterIdleResource(imageLoaderIdleResource: ImageLoaderIdleingResource) {
        resources.remove(imageLoaderIdleResource)
    }

    private class PicassoImageRequest : ImageLoader.RequestBuilder {
        private var loadingListener: (ImageLoader.ImageLoadingState) -> Unit = {}

        constructor(url: String?, listener: (ImageLoader.ImageLoadingState) -> Unit) : super(url) {
            this.loadingListener = listener
        }

        constructor(drawableId: Int, listener: (ImageLoader.ImageLoadingState) -> Unit) : super(drawableId) {
            this.loadingListener = listener
        }

        override fun into(imageView: ImageView) {

            if (isFromResource()) {
                loadFromResource(imageView)
            } else {
                loadFromNetwork(imageView)
            }

        }

        private fun loadFromNetwork(imageView: ImageView) {
            setState(imageView, ImageLoader.ImageLoadingState.Loading)

            val picasso = Picasso.get()
            val request = picasso.load(url)

            if (placeholderId != 0)
                request.placeholder(placeholderId)

            if (errorId != 0)
                request.error(errorId)

            request.into(imageView, object : com.squareup.picasso.Callback {
                override fun onSuccess() {
                    callback?.onSuccess()
                    setState(imageView, ImageLoader.ImageLoadingState.Success)
                }

                override fun onError(e: Exception?) {
                    callback?.onError(e)
                    setState(imageView, ImageLoader.ImageLoadingState.Error)
                }
            })
        }

        private fun loadFromResource(imageView: ImageView) {
            if (url == null) {
                imageView.setImageResource(drawableId)
            } else
                if (url!!.startsWith("drawable://")) {
                    url!!.substring("drawable://".length).toIntOrNull()?.let {
                        imageView.setImageResource(it)
                    }
                }
            imageView.setTag(ImageLoader.IMAGE_LOADER_TAG, ImageLoader.ImageLoadingState.Success)
        }

        private fun isFromResource(): Boolean {
            return (url == null || url!!.startsWith("drawable://"))
        }

        private fun setState(imageView: ImageView, state: ImageLoader.ImageLoadingState) {
            imageView.setTag(ImageLoader.IMAGE_LOADER_TAG, state)
            loadingListener(state)
        }
    }
}
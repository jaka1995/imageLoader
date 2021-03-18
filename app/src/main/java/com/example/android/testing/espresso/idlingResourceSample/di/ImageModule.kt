package com.example.android.testing.espresso.idlingResourceSample.di

import com.example.android.testing.espresso.idlingResourceSample.image.ImageLoader
import com.example.android.testing.espresso.idlingResourceSample.image.PicassoImageLoader
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ImageModule {

    @Singleton
    @Provides
    fun provideImageLoader(): ImageLoader {
        return PicassoImageLoader()
    }
}
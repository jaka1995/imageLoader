package com.example.android.testing.espresso.idlingResourceSample.di

import android.content.Context
import com.example.android.testing.espresso.idlingResourceSample.image.ImageLoader
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ImageModule::class
])
interface AppComponent {

    fun context(): Context

    fun imageLoader(): ImageLoader

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }

}
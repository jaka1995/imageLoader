package com.example.android.testing.espresso.idlingResourceSample

import android.app.Application
import com.example.android.testing.espresso.idlingResourceSample.di.AppComponent
import com.example.android.testing.espresso.idlingResourceSample.di.DaggerAppComponent


class App : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder().context(this).build()

    }


}
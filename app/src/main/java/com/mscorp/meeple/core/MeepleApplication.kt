package com.mscorp.meeple.core

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.DispatchingAndroidInjector
import javax.inject.Inject

internal class MeepleApplication : DaggerApplication() {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<out DaggerApplication>

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return dispatchingAndroidInjector
    }

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent.builder()
            .application(this)
            .build()
            .inject(this)
    }
}
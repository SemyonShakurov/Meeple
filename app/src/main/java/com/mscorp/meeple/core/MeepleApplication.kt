package com.mscorp.meeple.core

import com.mscorp.meeple.di.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class MeepleApplication : DaggerApplication() {

    private val dispatchingAndroidInjector = DaggerApplicationComponent.builder()
        .application(this)
        .build()

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return dispatchingAndroidInjector
    }
}
package com.mscorp.meeple.di

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import javax.inject.Singleton

@Singleton
@Component(modules = [RootModule::class])
interface ApplicationComponent {

    fun inject(application: Application)

    fun inject(fragment: Fragment)

    fun inject(activity: Activity)

    @Component.Builder
    interface Builder {

        fun build(): ApplicationComponent

        @BindsInstance
        fun applicationBind(application: Application): Builder
    }
}

@Module(includes = [NetworkModule::class, ViewModelModule::class])
object RootModule {
    // No-op
}
package com.mscorp.meeple.di

import com.mscorp.meeple.core.MeepleApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.android.support.DaggerAppCompatActivity
import dagger.android.support.DaggerFragment
import javax.inject.Singleton

@Singleton
@Component(
    modules = [NetworkModule::class,
        ViewModelModule::class,
        AndroidSupportInjectionModule::class,
        ActivityModule::class]
)
interface ApplicationComponent : AndroidInjector<MeepleApplication> {

    override fun inject(application: MeepleApplication)

    fun inject(activity: DaggerAppCompatActivity)

    fun inject(fragment: DaggerFragment)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: MeepleApplication): Builder

        fun build(): ApplicationComponent
    }
}
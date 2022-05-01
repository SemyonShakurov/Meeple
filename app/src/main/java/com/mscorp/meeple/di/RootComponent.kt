package com.mscorp.meeple.di

import dagger.Component
import dagger.Module

@Component(modules = [RootModule::class])
class RootComponent {

}

@Module(includes = [NetworkModule::class])
object RootModule {
    // No-op
}
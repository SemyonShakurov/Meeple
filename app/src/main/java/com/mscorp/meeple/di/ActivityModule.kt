package com.mscorp.meeple.di

import com.mscorp.meeple.features.core_feature.MapFragment
import com.mscorp.meeple.features.core_feature.MenuActivity
import com.mscorp.meeple.features.core_feature.ProfileFragment
import com.mscorp.meeple.features.core_feature.login.*
import com.mscorp.meeple.features.event_feature.EventFragment
import com.mscorp.meeple.features.event_feature.EventsFragment
import com.mscorp.meeple.features.friend_feature.AddNewFriendsFragment
import com.mscorp.meeple.features.friend_feature.FriendDetailedFragment
import com.mscorp.meeple.features.friend_feature.MyFriendsFragment
import com.mscorp.meeple.features.games_feature.AddNewGameFragment
import com.mscorp.meeple.features.games_feature.GameDetailedFragment
import com.mscorp.meeple.features.games_feature.MyGamesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [LoginActivityModule::class])
internal interface ActivityModule {

    @ContributesAndroidInjector
    fun loginActivityInjector(): LoginActivity

    @ContributesAndroidInjector
    fun menuActivityInjector(): MenuActivity
}

@Module
internal interface LoginActivityModule {

    @ContributesAndroidInjector
    fun confirmCodeFragment(): ConfirmCodeFragment

    @ContributesAndroidInjector
    fun inputNickNameFragment(): InputNicknameFragment

    @ContributesAndroidInjector
    fun registrationFragment(): RegistrationFragment

    @ContributesAndroidInjector
    fun resetPasswordFragment(): ResetPasswordFragment

    @ContributesAndroidInjector
    fun startFragment(): StartFragment

    @ContributesAndroidInjector
    fun mapFragment(): MapFragment

    @ContributesAndroidInjector
    fun profileFragment(): ProfileFragment

    @ContributesAndroidInjector
    fun eventFragment(): EventFragment

    @ContributesAndroidInjector
    fun eventsFragment(): EventsFragment

    @ContributesAndroidInjector
    fun addNewFriendsFragment(): AddNewFriendsFragment

    @ContributesAndroidInjector
    fun friendDetailedFragment(): FriendDetailedFragment

    @ContributesAndroidInjector
    fun myFriendsFragment(): MyFriendsFragment

    @ContributesAndroidInjector
    fun addNewGameFragment(): AddNewGameFragment

    @ContributesAndroidInjector
    fun gameDetailedFragment(): GameDetailedFragment

    @ContributesAndroidInjector
    fun myGamesFragment(): MyGamesFragment
}
package com.mscorp.meeple.features.core_feature.view_models

import androidx.lifecycle.MutableLiveData
import com.mscorp.meeple.core.MeepleViewModel
import com.mscorp.meeple.features.core_feature.AuthorizationRepository
import com.mscorp.meeple.features.friend_feature.FriendsRepository
import com.mscorp.meeple.features.games_feature.GamesRepository
import com.mscorp.meeple.model.BoardGame
import com.mscorp.meeple.model.Request
import com.mscorp.meeple.model.User
import com.mscorp.meeple.model.UserFriends
import javax.inject.Inject

internal class LoginViewModel @Inject constructor(
    private val loginRepository: AuthorizationRepository,
    private val gamesRepository: GamesRepository,
    private val friendsRepository: FriendsRepository,
) : MeepleViewModel() {

    val gamesResponse: MutableLiveData<Request<List<BoardGame>>> = MutableLiveData()
    val loginResponse: MutableLiveData<Request<User>> = MutableLiveData()
    val friendsResponse: MutableLiveData<Request<UserFriends>> = MutableLiveData()

    fun getAllGames() = gamesRepository.getAllGames().loadResource { gamesResponse.value = it }

    fun getFriends(id: Int) =
        friendsRepository.getFriends(id = id).loadResource { friendsResponse.value = it }

    fun login(
        nickname: String,
        password: String
    ) = loginRepository.login(nickname = nickname, password = password)
        .loadResource { loginResponse.value = it }
}
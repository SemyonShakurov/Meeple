package com.mscorp.meeple.model

import java.io.Serializable

data class UserFriends(
    val friends: MutableList<User>,
    val received: MutableList<User>,
    val sent: MutableList<User>
) : Serializable

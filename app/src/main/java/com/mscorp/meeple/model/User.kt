package com.mscorp.meeple.model

import java.io.Serializable

data class User (
    val id: Int,
    val email: String,
    val nickname: String,
    var photoUrl:  String?,
    val games: HashMap<Int, BoardGame>?,
    val friends: HashMap<Int, User>?,
    val events: HashMap<Int, Event>?
): Serializable
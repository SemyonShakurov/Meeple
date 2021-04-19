package com.mscorp.meeple.model

import java.io.Serializable

data class User (
    val id: Int,
    val email: String,
    val nickname: String,
    val name: String,
    var photoUrl: String?,
    val games: List<Int>?,
    val friends: List<Int>?,
    val events: List<Int>?
): Serializable
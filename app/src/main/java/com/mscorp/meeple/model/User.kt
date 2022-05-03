package com.mscorp.meeple.model

import java.io.Serializable

internal data class User (
    val id: Int,
    val email: String,
    val nickname: String,
    val name: String,
    var photoUrl: String?,
    val games: MutableList<Int>?,
    val events: List<Int>?,
    val enabled: Boolean
): Serializable
package com.mscorp.meeple.model

import java.io.Serializable

internal data class Event(
    val id: Int,
    val title: String,
    val count: String,
    val games: List<Int>,
    val playersLevel: Int,
    val info: String,
    val date: Long,
    val members: List<User>,
    val lat: Double,
    val lng: Double,
    val creatorId: Int
): Serializable
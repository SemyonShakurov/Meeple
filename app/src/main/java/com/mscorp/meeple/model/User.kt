package com.mscorp.meeple.model

data class User(
    var email: String,
    var username: String,
    var password: String,
    var photo: String,
    val games: MutableList<BoardGame>,
    val friends: MutableList<User>,
    val events: MutableList<Event>
)
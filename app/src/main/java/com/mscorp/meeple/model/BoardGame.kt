package com.mscorp.meeple.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "games")
class BoardGame(
    @PrimaryKey
    val id: Int,
    val name: String,
    val type: String,
    val countPlayer: Int,
    val time: Int,
    val description: String,
    val pic: String
)
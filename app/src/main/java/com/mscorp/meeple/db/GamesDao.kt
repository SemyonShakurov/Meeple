package com.mscorp.meeple.db

import androidx.room.*
import com.mscorp.meeple.model.BoardGame

@Dao
interface GamesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     suspend fun insertAllGames(games: List<BoardGame>)

    @Update
    suspend fun updateGames(vararg games: BoardGame)

    @Query("SELECT * FROM games WHERE id IN (:idList)")
    suspend fun getGamesById(idList : List<Long> ) : List<BoardGame>

    @Query("SELECT * FROM games")
    suspend fun getAllGames() : List<BoardGame>

/*
    @Query("SELECT * FROM table_name WHERE name LIKE '(:query)%'")
    suspend fun getSearchResult(query: String)*/
}
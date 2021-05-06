package com.mscorp.meeple.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mscorp.meeple.model.BoardGame
/*
@Database(entities = [BoardGame::class], version = 1, exportSchema = false)
abstract class GamesDatabase : RoomDatabase() {
    abstract fun gamesDao() : GamesDao

    companion object {
        private var INSTANCE: GamesDatabase? = null

        fun getInstance(context: Context): GamesDatabase? {
            if (INSTANCE == null) {
                synchronized(GamesDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        GamesDatabase::class.java, "user.db").allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }

}*/

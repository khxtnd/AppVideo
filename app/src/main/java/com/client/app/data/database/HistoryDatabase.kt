package com.client.app.data.database


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.client.app.data.database.entities.SearchHistory
import com.client.app.data.database.entities.WatchedVideo

@Database(entities = [SearchHistory::class, WatchedVideo::class], version = 1)
abstract class HistoryDatabase : RoomDatabase() {
    abstract fun getSearchHistoryDao(): SearchHistoryDao
    abstract fun getWatchHistoryDao(): WatchedVideoDao

    companion object {
        @Volatile
        private var instance: HistoryDatabase? = null

        fun getInstance(context: Context): HistoryDatabase {
            if (instance == null) {
                instance =
                    Room.databaseBuilder(context, HistoryDatabase::class.java, "history_database.db")
                        .build()
            }
            return instance!!

        }
    }
}



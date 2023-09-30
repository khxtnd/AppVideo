package com.client.app.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.client.app.data.database.entities.WatchVideo

@Dao
interface WatchVideoDao {
    @Insert
    suspend fun insertWatchVideo(watchVideo: WatchVideo)

    @Delete
    suspend fun deleteWatchVideo(watchVideo: WatchVideo)

    @Query("select*from watch_history_table")
    fun getAllWatchVideo(): LiveData<List<WatchVideo>>

    @Query("SELECT COUNT(*) FROM watch_history_table WHERE id_video_col = :id")
    fun checkWatchVideoExisted(id: Int): Int
}
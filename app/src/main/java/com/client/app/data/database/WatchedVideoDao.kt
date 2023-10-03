package com.client.app.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.client.app.data.database.entities.WatchedVideo
import kotlinx.coroutines.flow.Flow

@Dao
interface WatchedVideoDao {
    @Insert
    suspend fun insertWatchVideo(watchedVideo: WatchedVideo)

    @Delete
    suspend fun deleteWatchVideo(watchedVideo: WatchedVideo)

    @Query("select*from watch_history_table")
    fun getAllWatchVideo(): Flow<List<WatchedVideo>>

    @Query("SELECT COUNT(*) FROM watch_history_table WHERE id_video_col = :id")
    fun checkWatchVideoExisted(id: Int): Int
}
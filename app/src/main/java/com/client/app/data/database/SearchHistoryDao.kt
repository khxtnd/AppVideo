package com.client.app.data.database


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.client.app.data.database.entities.SearchHistory
import kotlinx.coroutines.flow.Flow


@Dao
interface SearchHistoryDao {
    @Insert
    suspend fun insertSearchHistory(searchHistory: SearchHistory)
    @Delete
    suspend fun deleteSearchHistory(searchHistory: SearchHistory)

    @Query("select * from search_history_table")
    fun getAllSearchHistory(): Flow<List<SearchHistory>>

    @Query("DELETE FROM search_history_table")
    suspend fun deleteAllSearchHistory()

    @Query("SELECT COUNT(*) FROM search_history_table WHERE keySearch_col = :keySearch")
    fun checkSearchHistoryExisted(keySearch: String): Int
}
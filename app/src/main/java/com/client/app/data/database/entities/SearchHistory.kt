package com.client.app.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "search_history_table")
class SearchHistory(
    @ColumnInfo(name = "keySearch_col") var keySearch: String = ""
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "history_id_col")
    var id: Int = 0
}
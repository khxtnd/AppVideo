package com.client.app.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "watch_history_table")
class WatchVideo(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id_video_col") var id: Int = 0,
    @ColumnInfo(name = "time_col") var time: Int = 0,
    @ColumnInfo(name = "title_col") var title: String = "",
    @ColumnInfo(name = "image_col") var image: String = ""
)
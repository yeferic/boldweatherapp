package com.yeferic.boldweatherapp.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ItemResult(
    @PrimaryKey(autoGenerate = false)
    var id: Long,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "region")
    var region: String,
    @ColumnInfo(name = "country")
    var country: String,
    @ColumnInfo(name = "url")
    var url: String,
)

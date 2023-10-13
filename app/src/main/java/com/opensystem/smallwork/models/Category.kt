package com.opensystem.smallwork.models

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.opensystem.smallwork.utils.AppConstants

@Keep
@Entity(tableName = AppConstants.CATEGORY_TABLE_NAME)
class Category {

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: String = ""

    @ColumnInfo(name = "icon")
    var icon: String? = null

    @ColumnInfo(name = "name")
    var name: String? = null

}
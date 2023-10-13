package com.opensystem.smallwork.models

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.opensystem.smallwork.utils.AppConstants

@Keep
@Entity(tableName = AppConstants.ADDRESS_TABLE_NAME)
class UAddress {

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: String = ""

    @ColumnInfo(name = "uId")
    var uId: String? = null

    @ColumnInfo(name = "postalCode")
    var postalCode: String? = null

    @ColumnInfo(name = "state")
    var state: String? = null

    @ColumnInfo(name = "city")
    var city: String? = null

    @ColumnInfo(name = "nbh")
    var nbh: String? = null

    @ColumnInfo(name = "street")
    var street: String? = null

    @ColumnInfo(name = "number")
    var number: String? = null

    @ColumnInfo(name = "complement")
    var complement: String? = null

}
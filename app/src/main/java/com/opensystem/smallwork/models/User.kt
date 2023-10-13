package com.opensystem.smallwork.models

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.opensystem.smallwork.utils.AppConstants
import java.io.Serializable

@Keep
@Entity(tableName = AppConstants.USER_TABLE_NAME)
open class User : Serializable {

   @PrimaryKey(autoGenerate = false)
   @ColumnInfo(name = "id")
   var id: String = ""

   @ColumnInfo(name = "name")
   lateinit var name: String

   @ColumnInfo(name = "email")
   lateinit var email: String

   @ColumnInfo(name = "avatar")
   var avatar: String? = null

   @ColumnInfo(name = "profession")
   var profession: String? = null

   @ColumnInfo(name = "description")
   var description: String? = null

   @ColumnInfo(name = "addressId")
   var addressId: String? = null

   @ColumnInfo(name = "addressName")
   var addressName: String? = null

   @ColumnInfo(name = "status")
   var status: Int = 0

   @ColumnInfo(name = "professional")
   var professional: Boolean = false

   @ColumnInfo(name = "dailyValue")
   var dailyValue: String? = ""

   @ColumnInfo(name = "rating")
   var rating: Float? = null

   @ColumnInfo(name = "fcmToken")
   var fcmToken: String? = ""

   @Ignore
   var password: String = ""

}
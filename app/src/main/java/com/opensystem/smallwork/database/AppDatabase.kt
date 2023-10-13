package com.opensystem.smallwork.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.opensystem.smallwork.database.address.AddressDAO
import com.opensystem.smallwork.database.category.CategoryDAO
import com.opensystem.smallwork.database.user.UserDAO
import com.opensystem.smallwork.models.Category
import com.opensystem.smallwork.models.UAddress
import com.opensystem.smallwork.models.User

/**
 * UserDAO
 *
 * @author Android version Wesley Brito
 * @Created  2023/08/05
 *
 */

const val DATABASE_NAME = "smallWorkBD"

@Database(entities = [User::class, UAddress::class, Category::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {

   abstract fun userDAO(): UserDAO
   abstract fun addressDAO(): AddressDAO
   abstract fun categoryDAO(): CategoryDAO

   companion object {
      private var INSTANCE: AppDatabase? = null
      fun getDatabase(context: Context): AppDatabase {
         if (INSTANCE == null) {
            synchronized(AppDatabase) {
               INSTANCE = Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                  .allowMainThreadQueries()
                  .build()
            }
         }

         return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
               .allowMainThreadQueries()
               .build()
            INSTANCE = instance
            instance
         }
      }
   }

}
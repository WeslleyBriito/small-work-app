package com.opensystem.smallwork.database.category

import androidx.room.*
import com.opensystem.smallwork.models.Category
import com.opensystem.smallwork.utils.AppConstants

@Dao
interface CategoryDAO {

   @Insert
   fun save(category: Category): Long

   @Update
   fun update(category: Category): Int

   @Delete
   fun delete(category: Category)

   @Query("SELECT * FROM ${AppConstants.CATEGORY_TABLE_NAME}")
   fun get(): List<Category>

}
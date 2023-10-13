package com.opensystem.smallwork.database.user

import androidx.room.*
import com.opensystem.smallwork.models.User
import com.opensystem.smallwork.utils.AppConstants

/**
 * UserDAO
 *
 * @author Android version Wesley Brito
 * @Created  2023/08/05
 */

@Dao
interface UserDAO {

   @Insert
   fun save(user: User): Long

   @Update
   fun update(user: User): Int

   @Delete
   fun delete(user: User)

   @Query("SELECT * FROM ${AppConstants.USER_TABLE_NAME} WHERE id = :id")
   fun get(id: String): User

}
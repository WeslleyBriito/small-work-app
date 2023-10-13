package com.opensystem.smallwork.database.address

import androidx.room.*
import com.opensystem.smallwork.models.UAddress
import com.opensystem.smallwork.utils.AppConstants

@Dao
interface AddressDAO {

   @Insert
   fun save(address: UAddress): Long

   @Update
   fun update(address: UAddress): Int

   @Delete
   fun delete(address: UAddress)

   @Query("SELECT * FROM ${AppConstants.ADDRESS_TABLE_NAME} WHERE id = :id")
   fun get(id: String): UAddress

}
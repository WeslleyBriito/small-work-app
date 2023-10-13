package com.opensystem.smallwork.database.category

import android.content.Context
import com.opensystem.smallwork.database.AppDatabase
import com.opensystem.smallwork.models.Category

class CategoryRepository(context: Context) {

   //acesso ao banco de dados
   private val mDatabase = AppDatabase.getDatabase(context).categoryDAO()

   fun get(): List<Category> {
      return mDatabase.get()
   }

   fun save(category: Category): Boolean {
      return mDatabase.save(category) > 0
   }

   fun update(category: Category): Boolean {
      return mDatabase.update(category) > 0
   }

   fun delete(category: Category) {
      mDatabase.delete(category)
   }

}
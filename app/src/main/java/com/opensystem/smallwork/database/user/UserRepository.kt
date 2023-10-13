package com.opensystem.smallwork.database.user

import android.content.Context
import com.opensystem.smallwork.database.AppDatabase
import com.opensystem.smallwork.models.User

class UserRepository(context: Context) {

   //acesso ao banco de dados
   private val mDatabase = AppDatabase.getDatabase(context).userDAO()

   /**
    * Carrega os dados do usuário
    */
   fun get(id: String): User {
      return mDatabase.get(id)
   }

   /**
    * Salva os dados do usuário
    */
   fun save(user: User): Boolean {
      return mDatabase.save(user) > 0
   }

   /**
    * Atualiza os dados do usuário
    */
   fun update(user: User): Boolean {
      return mDatabase.update(user) > 0
   }

   /**
    * Remove os dados do usuário
    */
   fun delete(user: User) {
      mDatabase.delete(user)
   }

}
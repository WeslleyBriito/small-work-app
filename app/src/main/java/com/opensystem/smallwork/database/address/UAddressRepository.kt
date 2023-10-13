package com.opensystem.smallwork.database.address

import android.content.Context
import com.opensystem.smallwork.database.AppDatabase
import com.opensystem.smallwork.models.UAddress

class UAddressRepository(context: Context) {

   //acesso ao banco de dados
   private val mDatabase = AppDatabase.getDatabase(context).addressDAO()

   /**
    * Carrega os dados do usuário
    */
   fun get(id: String): UAddress {
      return mDatabase.get(id)
   }

   /**
    * Salva os dados do usuário
    */
   fun save(address: UAddress): Boolean {
      return mDatabase.save(address) > 0
   }

   /**
    * Atualiza os dados do usuário
    */
   fun update(address: UAddress): Boolean {
      return mDatabase.update(address) > 0
   }

   /**
    * Remove os dados do usuário
    */
   fun delete(address: UAddress) {
      mDatabase.delete(address)
   }

}
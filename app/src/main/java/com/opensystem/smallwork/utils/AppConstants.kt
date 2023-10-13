package com.opensystem.smallwork.utils

/**
 * SWConstants
 *
 * A class of Wesley Brito
 *
 * @author Android version Wesley Brito
 * @Created  2023/08/05
 *
 */

object AppConstants {
   //database
   const val USER_TABLE_NAME = "users"
   const val ADDRESS_TABLE_NAME = "addresses"
   const val CATEGORY_TABLE_NAME = "categories"
   const val CONTRACT_TABLE_NAME = "contracts"

   //status from requests
   object RequestStatus {
      const val PENDING = 0
      const val ACCEPTED = 1
      const val DECLINED = 2
      const val COMPLETED = 3
      const val CANCELED = 4
   }

   //web_service
   const val VIACEP = "https://viacep.com.br/ws/"
   const val FCM = "https://fcm.googleapis.com/fcm/"
}

package com.opensystem.smallwork.ui.activities

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.opensystem.smallwork.utils.AppConstants
import com.opensystem.smallwork.utils.appUser

class MainViewModel: ViewModel() {

   fun updateUserNotificationToken(token: String){
      val db = Firebase.firestore
      val ref = db.collection(AppConstants.USER_TABLE_NAME).document(appUser.id)

      ref.update(
         "fcmToken", token
      ).addOnCompleteListener {
         Log.d("FCM", "success")
      }.addOnFailureListener { e ->
         Log.w("FCM", "Error updating document $e")
      }
   }

}
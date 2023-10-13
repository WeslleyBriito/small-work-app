package com.opensystem.smallwork.ui.fragments.main.home.worker

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.opensystem.smallwork.R
import com.opensystem.smallwork.apis.ApiResult
import com.opensystem.smallwork.apis.firebase.ControllerFCM
import com.opensystem.smallwork.models.ErrorSW
import com.opensystem.smallwork.models.Notification
import com.opensystem.smallwork.models.NotificationBody
import com.opensystem.smallwork.models.Worker
import com.opensystem.smallwork.utils.AppConstants
import com.opensystem.smallwork.utils.appUser

class WorkerViewModel: ViewModel() {

   private var _success = MutableLiveData<Boolean>()
   val success: LiveData<Boolean> = _success

   fun request(worker: Worker, context: Context){
      // Access a Cloud Firestore instance
      val db = Firebase.firestore

      // Add a new document
      db.collection(AppConstants.CONTRACT_TABLE_NAME).add(
         mapOf(
            "userId" to appUser.id,
            "workerId" to worker.id,
            "daily" to worker.dailyValue,
            "user" to mapOf(
               "name" to appUser.name,
               "addressName" to appUser.addressName,
               "avatar" to appUser.avatar,
               "description" to appUser.description,
               "addressId" to appUser.addressId,
               "email" to appUser.email,
               "fcmToken" to appUser.fcmToken
            ),
            "worker" to mapOf(
               "name" to worker.name,
               "addressName" to worker.addressName,
               "avatar" to worker.avatar,
               "description" to worker.description,
               "addressId" to worker.addressId,
               "email" to worker.email,
               "fcmToken" to worker.fcmToken
            ),
            "requestDate" to System.currentTimeMillis(),
            "status" to AppConstants.RequestStatus.PENDING
         )
      ).addOnSuccessListener {
         db.collection(AppConstants.CONTRACT_TABLE_NAME).document(it.id).update(
            mapOf("id" to it.id)
         ).addOnCompleteListener {
            _success.value = true

            ControllerFCM.postNotification(NotificationBody(
               worker.fcmToken,
               Notification(context.getString(R.string.new_request), context.getString(R.string.you_have_request))
            ), object : ApiResult<NotificationBody>{
               override fun responseBody(body: NotificationBody, type: String) {
                  Log.d("FCM", "notification success")
               }
               override fun error(error: ErrorSW) {}
            })
         }
      }.addOnFailureListener { e ->
         _success.value = false
         Log.w("User", "Error adding document", e)
      }
   }

}
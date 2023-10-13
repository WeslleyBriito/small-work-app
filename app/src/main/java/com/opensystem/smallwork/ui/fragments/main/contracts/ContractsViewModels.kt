package com.opensystem.smallwork.ui.fragments.main.contracts

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
import com.opensystem.smallwork.models.Contract
import com.opensystem.smallwork.models.ErrorSW
import com.opensystem.smallwork.models.Notification
import com.opensystem.smallwork.models.NotificationBody
import com.opensystem.smallwork.utils.AppConstants
import com.opensystem.smallwork.utils.appUser

class ContractsViewModels : ViewModel() {

   private var _contracts = MutableLiveData<List<Contract>>()
   val contracts: LiveData<List<Contract>> = _contracts

   private var _noContracts = MutableLiveData<String>()
   val noContracts: LiveData<String> = _noContracts

   private var _acceptRequestResponse = MutableLiveData<String>()
   val acceptRequestResponse: LiveData<String> = _acceptRequestResponse

   private var _declineRequestResponse = MutableLiveData<String>()
   val declineRequestResponse: LiveData<String> = _declineRequestResponse

   private val contractList = ArrayList<Contract>()

   fun getContractsById(context: Context){
      val db = Firebase.firestore

      db.collection(AppConstants.CONTRACT_TABLE_NAME)
         .whereEqualTo("userId", appUser.id)
         .get()
         .addOnSuccessListener { documents ->
            contractList.clear()
            for (document in documents){
               val contract = document.toObject(Contract::class.java)
               contractList.add(contract)
            }

            if (contractList.isEmpty()){
               _noContracts.value = context.getString(R.string.no_services_solicitation)
            }

            if (contractList.isNotEmpty()){
               _contracts.value = contractList
            }
         }
         .addOnFailureListener { exception ->
            Log.d("firebaseResult",  "Error getting documents: ", exception)
         }

   }

   fun replyRequest(context: Context, finish: Boolean, contract: Contract){
      val db = Firebase.firestore
      val ref = db.collection(AppConstants.CONTRACT_TABLE_NAME).document(contract.id)

      val nBody = NotificationBody(
         contract.worker.fcmToken,
         Notification(
            context.getString(if (finish) R.string.request_finished else R.string.request_canceled),
            context.getString(if (finish) R.string.your_request_has_been_finish else R.string.your_request_canceled)
         )
      )
      ref.update(
         mapOf(
            "status" to if (finish) AppConstants.RequestStatus.COMPLETED else AppConstants.RequestStatus.CANCELED,
         )
      ).addOnCompleteListener {
         ControllerFCM.postNotification(nBody, object: ApiResult<NotificationBody> {
            override fun error(error: ErrorSW) {
               Log.w("firebaseResult", "Error to send notification")
            }
         })

         if (finish){
            _acceptRequestResponse.value = context.getString(R.string.success_accept_request)
         }else{
            _declineRequestResponse.value = context.getString(R.string.success_decline_request)
         }
      }.addOnFailureListener { e ->
         Log.w("firebaseResult", "Error updating document $e")
         if (finish){
            _acceptRequestResponse.value = context.getString(R.string.failure_accept_request)
         }else{
            _declineRequestResponse.value = context.getString(R.string.failure_decline_request)
         }
      }
   }

}
package com.opensystem.smallwork.ui.fragments.main.home

import android.content.Context
import android.util.Log
import android.widget.Toast
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
import com.opensystem.smallwork.models.Worker
import com.opensystem.smallwork.ui.fragments.main.menu.profile.details.DetailsViewModel
import com.opensystem.smallwork.utils.AppConstants
import com.opensystem.smallwork.utils.Preferences
import com.opensystem.smallwork.utils.appUser

class HomeViewModel: ViewModel() {
   private var _workers = MutableLiveData<List<Worker>>()
   val workers: LiveData<List<Worker>> = _workers

   private var _noWorkers = MutableLiveData<String>()
   val noWorkers: LiveData<String> = _noWorkers

   private val workerList = ArrayList<Worker>()

   // CONTRACTS

   private var _contracts = MutableLiveData<List<Contract>>()
   val contracts: LiveData<List<Contract>> = _contracts

   private var _noContracts = MutableLiveData<String>()
   val noContracts: LiveData<String> = _noContracts

   private var _acceptRequestResponse = MutableLiveData<String>()
   val acceptRequestResponse: LiveData<String> = _acceptRequestResponse

   private var _declineRequestResponse = MutableLiveData<String>()
   val declineRequestResponse: LiveData<String> = _declineRequestResponse

   private val contractList = ArrayList<Contract>()

   fun getWorkers(context: Context){
      val db = Firebase.firestore

      db.collection("users")
         .whereEqualTo("professional", true)
         .get()
         .addOnSuccessListener { documents ->
            workerList.clear()
            for (document in documents){
               val worker = document.toObject(Worker::class.java)
               workerList.add(worker)
            }

            if (workerList.isEmpty()){
               _noWorkers.value = context.getString(R.string.no_workers_found)
            }

            if (workerList.isNotEmpty()){
               _workers.value = workerList
            }
         }
         .addOnFailureListener { exception ->
            Log.d("firebaseResult",  "Error getting documents: ", exception)
         }

   }

   fun getContractsById(context: Context){
      val db = Firebase.firestore

      db.collection(AppConstants.CONTRACT_TABLE_NAME)
         .whereEqualTo("workerId", appUser.id)
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

   fun replyRequest(context: Context, accept: Boolean, contract: Contract){
      val db = Firebase.firestore
      val ref = db.collection(AppConstants.CONTRACT_TABLE_NAME).document(contract.id)
      val nBody = NotificationBody(
         contract.user.fcmToken,
         Notification(
            context.getString(if (accept) R.string.request_accept else R.string.request_decline),
            context.getString(if (accept) R.string.your_request_has_been_accept else R.string.your_request_was_decline)
         )
      )

      ref.update(
         mapOf(
            "status" to if (accept) AppConstants.RequestStatus.ACCEPTED else AppConstants.RequestStatus.DECLINED,
         )
      ).addOnCompleteListener {
         ControllerFCM.postNotification(nBody, object: ApiResult<NotificationBody>{
            override fun error(error: ErrorSW) {
               Log.w("firebaseResult", "Error to send notification")
            }
         })
         if (accept){
            _acceptRequestResponse.value = context.getString(R.string.success_accept_request)
         }else{
            _declineRequestResponse.value = context.getString(R.string.success_decline_request)
         }
      }.addOnFailureListener { e ->
         Log.w("firebaseResult", "Error updating document $e")
         if (accept){
            _acceptRequestResponse.value = context.getString(R.string.failure_accept_request)
         }else{
            _declineRequestResponse.value = context.getString(R.string.failure_decline_request)
         }
      }
   }

}
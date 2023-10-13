package com.opensystem.smallwork.ui.fragments.auth.login

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.opensystem.smallwork.database.user.UserRepository
import com.opensystem.smallwork.database.address.UAddressRepository
import com.opensystem.smallwork.models.UAddress
import com.opensystem.smallwork.models.User
import com.opensystem.smallwork.utils.AppConstants
import com.opensystem.smallwork.utils.Preferences
import com.opensystem.smallwork.utils.setAppInfo

/**
 * LoginViewModel
 *
 * A class of Wesley Brito
 *
 * @author Android version Wesley Brito
 * @since Version 1.2.0
 * @Created  06/2021 - 07/2021
 *
 */

class LoginViewModel(application: Application) : AndroidViewModel(application) {
   // Access a Cloud Firestore instance
   private val db = Firebase.firestore

   private lateinit var uRepository: UserRepository
   private lateinit var uAddressRepository: UAddressRepository

   private var mSaveUser = MutableLiveData<Boolean>()
   val saveUser: LiveData<Boolean> = mSaveUser

   private lateinit var auth: FirebaseAuth

   fun initRepositories(context: Context){
      uRepository = UserRepository(context)
      uAddressRepository = UAddressRepository(context)
   }

   fun getUserData(context: Context, id: String) {
      db.collection(AppConstants.USER_TABLE_NAME).document(id)
         .get()
         .addOnSuccessListener { document: DocumentSnapshot ->
            //Log.d(TAG, "${document.id} => ${document.data}")
            val user = document.toObject(User::class.java)

            if (user?.addressId == null) {
               saveUserLogged(context, user!!)
            } else {
               getUserAddress(context, user)
            }
         }
         .addOnFailureListener {
            //Log.w(TAG, "Error getting documents.", it)
            mSaveUser.value = false
         }
   }

   private fun saveUserLogged(context: Context, user: User, address: UAddress = UAddress()) {
      try {
         uRepository.save(user)
         Preferences(context).setLoggedUId(user.id)
         setAppInfo(user, address)
         mSaveUser.value = true
      }catch (e: Exception){
         e.printStackTrace()
         mSaveUser.value = false
      }
   }

   private fun getUserAddress(context: Context, user: User) {
      db.collection(AppConstants.ADDRESS_TABLE_NAME).document(user.addressId!!)
         .get()
         .addOnSuccessListener { document: DocumentSnapshot ->
            Log.d("user", "${document.id} => ${document.data}")
            val address = document.toObject(UAddress::class.java)

            uAddressRepository.save(address!!)
            saveUserLogged(context, user, address)
         }
         .addOnFailureListener {
            //Log.w(TAG, "Error getting documents.", it)
            auth.signOut()
            mSaveUser.value = false
         }
   }

}
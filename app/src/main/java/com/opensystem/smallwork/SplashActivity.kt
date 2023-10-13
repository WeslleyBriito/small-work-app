package com.opensystem.smallwork

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.google.gson.Gson
import com.opensystem.smallwork.database.address.UAddressRepository
import com.opensystem.smallwork.database.user.UserRepository
import com.opensystem.smallwork.models.Category
import com.opensystem.smallwork.models.UAddress
import com.opensystem.smallwork.models.User
import com.opensystem.smallwork.ui.activities.AuthAct
import com.opensystem.smallwork.ui.activities.MainAct
import com.opensystem.smallwork.ui.dialogs.AppMessage
import com.opensystem.smallwork.utils.AppConstants
import com.opensystem.smallwork.utils.Preferences
import com.opensystem.smallwork.utils.setAppInfo

class SplashActivity : AppCompatActivity() {

   private val db = Firebase.firestore

   private val categories: MutableList<Category> = ArrayList()

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_splash)
      Firebase.messaging.isAutoInitEnabled = true
      getCategories()
   }

   private fun getUserData() {
      if (Firebase.auth.currentUser != null) {
         db.collection(AppConstants.USER_TABLE_NAME).document(Preferences(this).getLoggedUId())
            .get()
            .addOnSuccessListener { document: DocumentSnapshot ->
               val user = document.toObject(User::class.java)

               if (user == null) {
                  showError()
               }else{
                  if (user.addressId == null) {
                     saveUserLogged(user)
                  } else {
                     getUserAddress(user)
                  }
               }
            }
            .addOnFailureListener {
               showError()
            }
      } else {
         setAppInfo(User(), UAddress(), categories)
         startActivity(Intent(this, AuthAct::class.java))
         finish()
      }
   }

   private fun getUserAddress(user: User) {
      db.collection(AppConstants.ADDRESS_TABLE_NAME).document(user.addressId!!)
         .get()
         .addOnSuccessListener { document: DocumentSnapshot ->
            Log.d("user", "${document.id} => ${document.data}")

            val address = document.toObject(UAddress::class.java)

            UAddressRepository(this).update(address!!)
            saveUserLogged(user, address)
         }
         .addOnFailureListener {
            showError()
         }
   }

   private fun getCategories(){
      db.collection("categories").get()
         .addOnSuccessListener { documents ->
            Log.d("category",  "documents: " + documents.size())
            for (document in documents){
               val category = document.toObject(Category::class.java)
               categories.add(category)
            }

            getUserData()
         }
         .addOnFailureListener { exception ->
            Log.d("category",  "Error getting documents: ", exception)
         }

   }

   private fun saveUserLogged( user: User, address: UAddress = UAddress()) {
      try {
         UserRepository(this).update(user)
         setAppInfo(user, address, categories)
         startActivity(Intent(this, MainAct::class.java))
         finish()
      }catch (e: Exception){
         showError()
      }
   }

   private fun showError(){
      AppMessage.error(
         this,
         "${getString(R.string.error_get_user_data)} \n\nError Code: 0001"
      ){
         startActivity(Intent(this, AuthAct::class.java))
         Firebase.auth.signOut()
         finish()
      }
   }
}
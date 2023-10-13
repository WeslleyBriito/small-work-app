package com.opensystem.smallwork.ui.fragments.auth.signup

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.opensystem.smallwork.database.user.UserRepository
import com.opensystem.smallwork.models.User
import com.opensystem.smallwork.utils.Preferences

class SignUpViewModel : ViewModel() {

   private var mUser = MutableLiveData<User>()
   private var mAccept = MutableLiveData<Boolean>()

   val user: LiveData<User> = mUser
   val accept: LiveData<Boolean> = mAccept

   fun register(name: String, email: String, password: String) {
      val user = User()
      user.name = name
      user.email = email
      user.password = password
      user.professional = false

      mUser.value = user
   }

   fun save(context: Context, user: User) {
      // Access a Cloud Firestore instance
      val db = Firebase.firestore

      val u = hashMapOf(
         "id" to user.id,
         "name" to user.name,
         "email" to user.email,
         "status" to 2,
         "professional" to user.professional
      )

      // Add a new document
      db.collection("users").document(user.id).set(u).addOnCompleteListener {
         Log.d("User", "DocumentSnapshot successfully written!")

         UserRepository(context).save(user)
         Preferences(context).setLoggedUId(user.id)

      }.addOnFailureListener { e -> Log.w("User", "Error adding document", e) }
   }
}
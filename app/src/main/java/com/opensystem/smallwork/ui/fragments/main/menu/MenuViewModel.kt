package com.opensystem.smallwork.ui.fragments.main.menu

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.opensystem.smallwork.R
import com.opensystem.smallwork.database.address.UAddressRepository
import com.opensystem.smallwork.database.user.UserRepository
import com.opensystem.smallwork.models.User
import com.opensystem.smallwork.ui.fragments.main.menu.profile.details.DetailsViewModel
import com.opensystem.smallwork.utils.AppConstants
import com.opensystem.smallwork.utils.Preferences
import com.opensystem.smallwork.utils.appUAddress
import com.opensystem.smallwork.utils.appUser

/**
 * MenuViewModel
 *
 * @author Android version Wesley Brito
 * @Created  2023/08/13
 */
class MenuViewModel(application: Application) : AndroidViewModel(application) {

   private var _hasSignOut = MutableLiveData<Boolean>()
   val hasSignOut: LiveData<Boolean> = _hasSignOut

   private var _userUpdatedSuccess = MutableLiveData<Boolean>()
   val userUpdatedSuccess: LiveData<Boolean> = _userUpdatedSuccess

   fun signOut(context: Context) {
      FirebaseAuth.getInstance().signOut()
      Preferences(context).clearUserData()
      if (appUser.addressId != null) {
         UAddressRepository(context).delete(appUAddress)
      }

      UserRepository(context).delete(appUser)

      _hasSignOut.value = true
   }

   fun updatedUser(context: Context, value: String) {
      val db = Firebase.firestore
      val pref = Preferences(context)
      val ref = db.collection(AppConstants.USER_TABLE_NAME).document(pref.getLoggedUId())

      val dailyValue = value.ifEmpty { "R$0,00" }

      ref.update(
         mapOf(
            "dailyValue" to dailyValue,
            "professional" to true
         )
      ).addOnCompleteListener {
         _userUpdatedSuccess.value = true
         pref.setProActivated(true)
      }.addOnFailureListener {
         _userUpdatedSuccess.value = false
      }
   }

   // return a string id about the user type
   fun getTextToAd(pref: Preferences) = when{
      appUser.professional && pref.proActivated() -> R.string.login_as_user

      appUser.professional && !pref.proActivated() -> R.string.login_as_worker

      else -> R.string.advertise_your_work
   }
}
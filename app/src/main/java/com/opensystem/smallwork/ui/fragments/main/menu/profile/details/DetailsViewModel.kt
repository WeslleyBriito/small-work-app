package com.opensystem.smallwork.ui.fragments.main.menu.profile.details

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.opensystem.smallwork.database.address.UAddressRepository
import com.opensystem.smallwork.database.user.UserRepository
import com.opensystem.smallwork.models.UAddress
import com.opensystem.smallwork.models.User
import com.opensystem.smallwork.utils.AppConstants
import com.opensystem.smallwork.utils.Preferences
import com.opensystem.smallwork.utils.Useful
import com.opensystem.smallwork.utils.appUAddress
import com.opensystem.smallwork.utils.appUser
import com.opensystem.smallwork.utils.setAppInfo
import java.io.ByteArrayOutputStream

class DetailsViewModel(application: Application) : AndroidViewModel(application) {

   companion object {
      private const val TAG = "firebaseResult"
   }

   @SuppressLint("StaticFieldLeak")
   private val _context = application.applicationContext
   private val _preferences = Preferences(_context)
   private val _uRepository = UserRepository(_context)
   private val _aRepository = UAddressRepository(_context)

   private var idImage = ""

   private var _uAddress = MutableLiveData<String>()
   val uAddress get() = _uAddress

   private var _updateUserSuccess = MutableLiveData<Boolean>()
   val updateUserSuccess: LiveData<Boolean> = _updateUserSuccess

   fun updateAvatar(bitmap: Bitmap) {

      // Create a storage reference from our app
      val storageRef = Firebase.storage.reference

      idImage = Useful.getRandomString(12)

      // Create a reference to 'avatar/'
      val avatarImagesRef = storageRef.child("avatars/$idImage")

      // Get the data from an ImageView as bytes
      val baos = ByteArrayOutputStream()
      bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
      val data = baos.toByteArray()

      val uploadTask = avatarImagesRef.putBytes(data)

      uploadTask.addOnFailureListener { exception ->
         // Handle unsuccessful uploads
         Log.w(TAG, "error: " + exception.message)
      }.addOnSuccessListener {
         finishUpdateUserAvatar()
      }

   }

   fun editAddress(address: UAddress) {
      if (appUser.addressId != null) {
         updatedUAddress(address)
      } else {
         saveUAddress(address)
      }
   }

   //Gera URL de download e salva no nó do usuário(users/uId)
   private fun finishUpdateUserAvatar() {
      Firebase.storage.reference
         .child("avatars/$idImage")
         .downloadUrl.addOnSuccessListener { downloadUrl: Uri ->
            Log.d("firebaseResult", "image: $downloadUrl")
            saveUrl(downloadUrl.toString())
         }
   }

   private fun saveUrl(url: String) {
      val db = Firebase.firestore
      val ref = db.collection(AppConstants.USER_TABLE_NAME).document(_preferences.getLoggedUId())
      ref.update("avatar", url)
         .addOnCompleteListener {
            val user = appUser
            user.avatar = url
            UserRepository(_context).update(user)
            setAppInfo(user, appUAddress)
            _updateUserSuccess.value = true
         }
         .addOnFailureListener { e ->
            Toast.makeText(_context, "Error updating avatar", Toast.LENGTH_SHORT).show()
            Log.w(TAG, "Error updating document $e")
         }
   }

   private fun updateUserAddressId(address: UAddress) {
      val db = Firebase.firestore
      val ref = db.collection(AppConstants.USER_TABLE_NAME).document(_preferences.getLoggedUId())
      ref.update(mapOf(
         "addressId" to address.id,
         "addressName" to "${address.street}, ${address.number}"
      )).addOnCompleteListener {
         updateLocalDatabase(address)
      }.addOnFailureListener { e ->
         Log.w(TAG, "Error updating document $e")
      }
   }

   fun updatedUser(user: User, context: Context) {
      val db = Firebase.firestore
      val ref = db.collection(AppConstants.USER_TABLE_NAME).document(_preferences.getLoggedUId())

      ref.update(
         mapOf(
            "name" to user.name,
            "profession" to user.profession,
            "description" to user.description
         )
      ).addOnCompleteListener {
         UserRepository(context).update(user)
         setAppInfo(user, appUAddress)
         _updateUserSuccess.value = true
      }.addOnFailureListener { e ->
         Toast.makeText(context, "Error updating", Toast.LENGTH_LONG).show()
         Log.w(TAG, "Error updating document $e")
      }
   }

   private fun updatedUAddress(address: UAddress) {
      val db = Firebase.firestore
      val ref = db.collection(AppConstants.ADDRESS_TABLE_NAME).document(address.id)

      ref.update(
         mapOf(
            "id" to address.id,
            "uId" to address.uId,
            "postaCode" to address.postalCode,
            "state" to address.state,
            "nbh" to address.nbh,
            "city" to address.city,
            "street" to address.street,
            "number" to address.number,
            "complement" to address.complement
         )
      ).addOnCompleteListener {
         Toast.makeText(_context, "Address successfully updated!", Toast.LENGTH_SHORT).show()
         updateUserAddressId(address)
      }.addOnFailureListener { e ->
         Toast.makeText(_context, "Error updating address", Toast.LENGTH_SHORT).show()
         Log.w(TAG, "Error updating document $e")
      }
   }

   private fun saveUAddress(address: UAddress) {
      // Access a Cloud Firestore instance
      val db = Firebase.firestore

      address.uId = _preferences.getLoggedUId()

      // Add a new document
      db.collection(AppConstants.ADDRESS_TABLE_NAME).add(
         mapOf(
            "uId" to address.uId,
            "postaCode" to address.postalCode,
            "state" to address.state,
            "nbh" to address.nbh,
            "city" to address.city,
            "street" to address.street,
            "number" to address.number,
            "complement" to address.complement
         )
      ).addOnSuccessListener {
         address.id = it.id
         updatedUAddress(address)
      }.addOnFailureListener { e ->
         Toast.makeText(_context, "Error updating address", Toast.LENGTH_SHORT).show()
         Log.w("User", "Error adding document", e)
      }
   }

   private fun updateLocalDatabase(address: UAddress) {
      if (appUser.addressId != null) {
         //Atualiza o endereço local
         _aRepository.update(address)
      } else {
         val user = appUser
         user.addressId = address.id
         //salva o endereço local
         _aRepository.save(address)
         //Atualiza o usuario local com o id de endereço
         _uRepository.update(user)
         setAppInfo(user, address)
         _updateUserSuccess.value = true
      }
   }

   fun updatedUserValue(context: Context, value: String) {
      val db = Firebase.firestore
      val pref = Preferences(context)
      val ref = db.collection(AppConstants.USER_TABLE_NAME).document(pref.getLoggedUId())

      val dailyValue = value.ifEmpty { "R$0,00" }

      ref.update(
         mapOf(
            "dailyValue" to dailyValue
         )
      ).addOnCompleteListener {
         val user = appUser
         user.dailyValue = dailyValue
         setAppInfo(user, appUAddress)
         _updateUserSuccess.value = true
      }.addOnFailureListener {
         _updateUserSuccess.value = false
      }
   }

}
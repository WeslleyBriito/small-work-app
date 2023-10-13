package com.opensystem.smallwork.ui.activities

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.opensystem.smallwork.R

class MainAct : AppCompatActivity() {
   private val viewModel: MainViewModel by viewModels()

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_main)
      askNotificationPermission()
   }

   override fun onSupportNavigateUp(): Boolean {
      val navController = findNavController(R.id.nav_host_fragment_activity_main)
      return NavigationUI.navigateUp(navController, null)
            || super.onSupportNavigateUp()
   }

   private val requestPermissionLauncher = registerForActivityResult(
      ActivityResultContracts.RequestPermission(),
   ) { isGranted: Boolean ->
      if (isGranted) {
         askNotificationPermission()
      }
   }

   private fun askNotificationPermission() {
      // This is only necessary for API level >= 33 (TIRAMISU)
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
         if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
            PackageManager.PERMISSION_GRANTED
         ) {
            // FCM SDK (and your app) can post notifications.
            getFMInstance()
         } else {
            // Directly ask for the permission
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
         }
      } else {
         getFMInstance()
      }
   }

   private fun getFMInstance() {
      FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
         if (!task.isSuccessful) {
            Log.w("FCM_token", "Fetching FCM registration token failed", task.exception)
            return@OnCompleteListener
         }

         viewModel.updateUserNotificationToken(task.result.toString())
      })
   }
}
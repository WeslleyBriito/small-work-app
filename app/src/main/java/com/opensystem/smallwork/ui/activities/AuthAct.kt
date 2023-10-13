package com.opensystem.smallwork.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.opensystem.smallwork.R

class AuthAct : AppCompatActivity() {
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_auth)
   }

   override fun onSupportNavigateUp(): Boolean {
      val navController = findNavController(R.id.nav_host_fragment_activity_auth)
      return NavigationUI.navigateUp(navController, null)
            || super.onSupportNavigateUp()
   }
}
package com.opensystem.smallwork.utils

import android.content.Context

/**
 * Preferences is a class to manage the shared preferences
 *
 * @author Android version Wesley Brito
 */

class Preferences(context: Context?) {

   private var preferences = context?.getSharedPreferences("swu.preference", 0)
   private var editor = preferences?.edit()

   fun setProActivated(value: Boolean) {
      editor!!.putBoolean("pro_activated", value)
      editor!!.commit()
   }

   fun proActivated(): Boolean {
      return preferences!!.getBoolean("pro_activated", false)
   }

   fun setLoggedUId(id: String?) {
      editor!!.putString("u_id", id)
      editor!!.commit()
   }

   fun getLoggedUId(): String {
      return preferences!!.getString("u_id", "")!!
   }

   fun clearUserData() {
      editor?.remove("u_id")
      editor?.remove("pro_activated")
      editor?.commit()
   }

}
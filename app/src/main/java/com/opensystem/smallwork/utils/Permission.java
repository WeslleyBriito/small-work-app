package com.opensystem.smallwork.utils;

import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.content.ContextCompat;

/**
 * Created by Wesley Brito
 */
public class Permission {

   public static Boolean hasPermissions(String[] permissions, Context context) {
      boolean havePermission = true;

      for (String permission : permissions) {
         havePermission = ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
         if (!havePermission) break;
      }

      return havePermission;
   }

   public static Boolean hasNoPermissions (String[] permissions, Context context) {
      return !hasPermissions(permissions, context);
   }

}

package com.opensystem.smallwork

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.opensystem.smallwork.ui.activities.MainAct

class AppFirebaseMessagingService : FirebaseMessagingService() {

   override fun onNewToken(token: String) {}

   override fun onMessageReceived(remoteMessage: RemoteMessage) {
      Log.d("FCM", "From: ${remoteMessage.from}")

      // Check if message contains a notification payload.
      remoteMessage.notification?.let {
         Log.d("FCM", "Message Notification Body: ${it.body}")
         val title = it.title
         val body = it.body

         showNotification(title, body)
      }
   }

   private fun showNotification(title: String?, body: String?){
      val pendingIntent = PendingIntent.getActivity(
         this,
         0,
         Intent(this, MainAct::class.java),
         PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
      )
      val notification = NotificationCompat.Builder(this, "fcm_default_channel_id")
         .setContentTitle(title)
         .setContentText(body)
         .setSmallIcon(R.drawable.ic_launcher_foreground)
         .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
         .setAutoCancel(true)
         .setContentIntent(pendingIntent)

      val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
         val channel = NotificationChannel("fcm_default_channel_id", "channel", NotificationManager.IMPORTANCE_DEFAULT)
         notificationManager.createNotificationChannel(channel)
      }

      notificationManager.notify(0, notification.build())
   }

}
package com.opensystem.smallwork.models

data class NotificationBody(
   var to: String?,
   var notification: Notification
)

data class Notification(
   var title: String,
   var body: String
)
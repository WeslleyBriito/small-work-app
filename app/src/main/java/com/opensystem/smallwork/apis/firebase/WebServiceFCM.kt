package com.opensystem.smallwork.apis.firebase

import com.opensystem.smallwork.models.NotificationBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface WebServiceFCM {

   @POST("send")
   @Headers(
      "Authorization:key=AAAADCc1K60:APA91bGZvQ80Hw13yiGQWcRhqHLozbOvrdmLatB_PpBEpWmxnkhObcCWwkEpJP_eA2dBDClMDXt7pomnu2y8hO1KyRBqnxu9R8h3FgOZL_Qobsj7FrMSkVnrVbfYAaXrget0xpVNXneD",
      "Content-Type:application/json"
   )
   fun send(@Body body: NotificationBody): Call<NotificationBody>

}
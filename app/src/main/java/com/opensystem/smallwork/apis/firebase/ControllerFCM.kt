package com.opensystem.smallwork.apis.firebase

import com.opensystem.smallwork.apis.ApiProvider
import com.opensystem.smallwork.apis.ApiResult
import com.opensystem.smallwork.models.ErrorSW
import com.opensystem.smallwork.models.NotificationBody
import com.opensystem.smallwork.utils.AppConstants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ControllerFCM {

   fun postNotification(post: NotificationBody, result: ApiResult<NotificationBody>) {

      val webService: WebServiceFCM =
         ApiProvider.getRetrofit(AppConstants.FCM).create(WebServiceFCM::class.java)

      val callFCM: Call<NotificationBody> = webService.send(post)

      callFCM.enqueue(object : Callback<NotificationBody> {
         override fun onResponse(call: Call<NotificationBody>, response: Response<NotificationBody>) {
            if (response.isSuccessful) {
               result.responseBody(response.body()!!, "fcm")
            } else {
               result.error(ErrorSW("Error to send notification\n\ncode: FCM002", "fcm"))
            }
         }

         override fun onFailure(call: Call<NotificationBody>, t: Throwable) {
            result.error(ErrorSW("Error to send notification\n\ncode: FCM001", "fcm"))
         }

      })
   }

}
package com.opensystem.smallwork.apis.viacep

import com.opensystem.smallwork.apis.ApiProvider
import com.opensystem.smallwork.apis.ApiResult
import com.opensystem.smallwork.models.ErrorSW
import com.opensystem.smallwork.models.ViaCep
import com.opensystem.smallwork.utils.AppConstants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ControllerVC {

   fun getAddressByCep(cep: String, result: ApiResult<ViaCep>) {

      val webService: WebServiceVC =
         ApiProvider.getRetrofit(AppConstants.VIACEP).create(WebServiceVC::class.java)

      val callCEPByCEP: Call<ViaCep> = webService.getAddressByCEP(cep)

      callCEPByCEP.enqueue(object : Callback<ViaCep> {
         override fun onResponse(call: Call<ViaCep>, response: Response<ViaCep>) {
            if (response.isSuccessful) {
               result.responseBody(response.body()!!, "cep")
            } else {
               result.error(ErrorSW("", ""))
            }
         }

         override fun onFailure(call: Call<ViaCep>, t: Throwable) {
            result.error(ErrorSW("", ""))
         }

      })
   }

}
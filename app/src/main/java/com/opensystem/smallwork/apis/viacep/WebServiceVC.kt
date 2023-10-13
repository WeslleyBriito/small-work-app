package com.opensystem.smallwork.apis.viacep

import com.opensystem.smallwork.models.ViaCep
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface WebServiceVC {

    @GET("{CEP}/json/")
    fun getAddressByCEP(@Path("CEP") CEP: String?): Call<ViaCep>

}
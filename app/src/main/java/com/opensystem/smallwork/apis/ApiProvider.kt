package com.opensystem.smallwork.apis

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiProvider {
   fun getRetrofit(baseUrl: String, log_enable: Boolean = false): Retrofit {
      val logging = HttpLoggingInterceptor()
      logging.level = HttpLoggingInterceptor.Level.BODY
      val client: OkHttpClient =
         if (log_enable) OkHttpClient.Builder().connectTimeout(40, TimeUnit.SECONDS)
            .writeTimeout(40, TimeUnit.SECONDS).readTimeout(40, TimeUnit.SECONDS)
            .addInterceptor(logging).build() else OkHttpClient.Builder()
            .connectTimeout(40, TimeUnit.SECONDS)
            .writeTimeout(40, TimeUnit.SECONDS).readTimeout(40, TimeUnit.SECONDS).build()
      return Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create())
         .client(client).build()
   }
}
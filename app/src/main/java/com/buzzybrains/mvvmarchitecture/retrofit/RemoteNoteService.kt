package com.buzzybrains.mvvmarchitecture.retrofit


import com.buzzybrains.mvvmarchitecture.util.ModelConstants
import com.google.gson.Gson
import com.google.gson.GsonBuilder

import java.util.concurrent.TimeUnit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RemoteNoteService {

    private var retrofit: Retrofit? = null

    val client: Retrofit
        get() {

            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val httpClient = OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .build()

            val gson = GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .create()

            retrofit = Retrofit.Builder()
                    .client(httpClient)
                    .baseUrl(ModelConstants.API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()

            return retrofit!!
        }


}

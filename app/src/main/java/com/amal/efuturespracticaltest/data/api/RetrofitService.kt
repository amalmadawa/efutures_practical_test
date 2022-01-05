package com.amal.efuturespracticaltest.data.api

import com.amal.efuturespracticaltest.data.model.Cases
import com.amal.efuturespracticaltest.data.model.Scenarios
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitService {

    @GET("scenarios/")
    suspend fun getScenarios(): Response<List<Scenarios>> // Response<> using instead of Call<T> //Call.enqueue

    @GET("scenarios/cases/{id}")
    suspend fun getCases(@Path("id") id: Int?): Response<List<Cases>>

    companion object {
        var retrofitService: RetrofitService? = null

        fun getInstance(): RetrofitService {

            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("http://mobileapi.efserver.net/mobile_api_test/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
}
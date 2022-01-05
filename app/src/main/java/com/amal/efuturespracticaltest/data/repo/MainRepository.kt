package com.amal.efuturespracticaltest.data.repo

import com.amal.efuturespracticaltest.data.api.RetrofitService

class MainRepository (private val retrofitService: RetrofitService) {

    suspend fun getScenarios() = retrofitService.getScenarios()

    suspend fun getCases(id:Int) = retrofitService.getCases(id)
}
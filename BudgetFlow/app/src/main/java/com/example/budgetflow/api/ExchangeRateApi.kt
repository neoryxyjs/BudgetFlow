package com.example.budgetflow.api

import retrofit2.http.GET

interface ExchangeRateApi {
    @GET("latest/CLP")
    suspend fun getLatestRates(): ExchangeRateResponse
}


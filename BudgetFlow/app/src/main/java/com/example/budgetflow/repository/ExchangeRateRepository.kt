package com.example.budgetflow.repository

import com.example.budgetflow.api.ApiClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ExchangeRateRepository {
    private val api = ApiClient.exchangeRateApi
    
    suspend fun getExchangeRates(): Flow<Result<Map<String, Double>>> = flow {
        try {
            val response = api.getLatestRates()
            emit(Result.success(response.rates))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}


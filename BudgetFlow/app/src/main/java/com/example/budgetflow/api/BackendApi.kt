package com.example.budgetflow.api

import com.example.budgetflow.api.dto.ExpenseDto
import com.example.budgetflow.api.dto.UserDto
import retrofit2.Response
import retrofit2.http.*

interface BackendApi {
    // Users
    @GET("users")
    suspend fun getAllUsers(): Response<List<UserDto>>
    
    @GET("users/{id}")
    suspend fun getUserById(@Path("id") id: String): Response<UserDto>
    
    @POST("users")
    suspend fun createUser(@Body user: UserDto): Response<UserDto>
    
    @PUT("users/{id}")
    suspend fun updateUser(@Path("id") id: String, @Body user: UserDto): Response<UserDto>
    
    @DELETE("users/{id}")
    suspend fun deleteUser(@Path("id") id: String): Response<Unit>
    
    // Expenses
    @GET("expenses")
    suspend fun getAllExpenses(): Response<List<ExpenseDto>>
    
    @GET("expenses/user/{userId}")
    suspend fun getExpensesByUserId(@Path("userId") userId: String): Response<List<ExpenseDto>>
    
    @GET("expenses/{id}")
    suspend fun getExpenseById(@Path("id") id: Long): Response<ExpenseDto>
    
    @POST("expenses")
    suspend fun createExpense(@Body expense: ExpenseDto): Response<ExpenseDto>
    
    @PUT("expenses/{id}")
    suspend fun updateExpense(@Path("id") id: Long, @Body expense: ExpenseDto): Response<ExpenseDto>
    
    @DELETE("expenses/{id}")
    suspend fun deleteExpense(@Path("id") id: Long): Response<Unit>
}


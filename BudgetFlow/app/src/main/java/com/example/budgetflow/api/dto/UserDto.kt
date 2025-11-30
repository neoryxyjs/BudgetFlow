package com.example.budgetflow.api.dto

import com.google.gson.annotations.SerializedName

data class UserDto(
    val id: String,
    val name: String,
    val email: String,
    // El backend devuelve camelCase, no snake_case
    val monthlyBudget: Double = 0.0,
    @SerializedName("profile_image_url")
    val profileImageUrl: String? = null,
    @SerializedName("created_at")
    val createdAt: String? = null // ISO 8601 format
)


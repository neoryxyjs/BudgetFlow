package com.example.budgetflow.api.dto

import com.google.gson.annotations.SerializedName

data class ExpenseDto(
    val id: Long? = null,
    // Probar sin @SerializedName primero - el backend podría esperar camelCase
    val userId: String,
    val amount: Double,
    val category: String,
    val description: String? = null,
    val date: String, // ISO 8601 format
    @SerializedName("created_at")
    val createdAt: String? = null // ISO 8601 format
) {
    // Función para asegurar que userId nunca sea null o vacío
    fun validate(): ExpenseDto {
        if (userId.isBlank()) {
            throw IllegalArgumentException("userId no puede estar vacío")
        }
        return this
    }
}


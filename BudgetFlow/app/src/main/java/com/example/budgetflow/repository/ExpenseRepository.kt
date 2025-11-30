package com.example.budgetflow.repository

import com.example.budgetflow.api.BackendApiClient
import com.example.budgetflow.api.mapper.ModelMapper.toDto
import com.example.budgetflow.api.mapper.ModelMapper.toModel
import com.example.budgetflow.model.Expense
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Calendar

class ExpenseRepository {
    private val backendApi = BackendApiClient.backendApi
    private val auth = FirebaseAuth.getInstance()
    
    private fun getCurrentUserId(): String {
        return auth.currentUser?.uid ?: throw IllegalStateException("Usuario no autenticado")
    }
    
    // Obtener todos los gastos del usuario actual desde Spring Boot
    fun getExpenses(): Flow<List<Expense>> = flow {
        val userId = getCurrentUserId()
        
        android.util.Log.d("ExpenseRepository", "Obteniendo gastos para usuario: $userId")
        
        try {
            val response = backendApi.getExpensesByUserId(userId)
            if (response.isSuccessful && response.body() != null) {
                val expenses = response.body()!!.map { it.toModel() }
                // Ordenar por fecha descendente
                val sortedExpenses = expenses.sortedByDescending { it.date.seconds }
                android.util.Log.d("ExpenseRepository", "Gastos obtenidos: ${sortedExpenses.size}")
                emit(sortedExpenses)
            } else {
                android.util.Log.w("ExpenseRepository", "No se pudieron obtener gastos: ${response.code()}")
                emit(emptyList())
            }
        } catch (e: Exception) {
            android.util.Log.e("ExpenseRepository", "Error al obtener gastos desde backend", e)
            emit(emptyList())
        }
    }
    
    // Obtener gastos del mes actual desde Spring Boot
    fun getExpensesThisMonth(): Flow<List<Expense>> = flow {
        val userId = getCurrentUserId()
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        val startOfMonth = Timestamp(calendar.time)
        
        android.util.Log.d("ExpenseRepository", "Obteniendo gastos del mes para usuario: $userId")
        
        try {
            val response = backendApi.getExpensesByUserId(userId)
            if (response.isSuccessful && response.body() != null) {
                val expenses = response.body()!!.map { it.toModel() }
                // Filtrar por mes y ordenar
                val filteredExpenses = expenses
                    .filter { it.date.seconds >= startOfMonth.seconds }
                    .sortedByDescending { it.date.seconds }
                
                android.util.Log.d("ExpenseRepository", "Gastos del mes obtenidos: ${filteredExpenses.size}")
                emit(filteredExpenses)
            } else {
                android.util.Log.w("ExpenseRepository", "No se pudieron obtener gastos del mes: ${response.code()}")
                emit(emptyList())
            }
        } catch (e: Exception) {
            android.util.Log.e("ExpenseRepository", "Error al obtener gastos del mes desde backend", e)
            emit(emptyList())
        }
    }
    
    // Agregar un gasto en Spring Boot
    suspend fun addExpense(expense: Expense): Result<String> {
        return try {
            val userId = getCurrentUserId()
            
            // Asegurar que el usuario exista en el backend antes de crear el gasto
            val userRepository = com.example.budgetflow.repository.UserRepository()
            val userExists = userRepository.userExists().getOrElse { false }
            
            if (!userExists) {
                android.util.Log.d("ExpenseRepository", "Usuario no existe en backend, creándolo...")
                val currentUser = auth.currentUser
                if (currentUser != null) {
                    val newUser = com.example.budgetflow.model.User(
                        id = userId,
                        email = currentUser.email ?: "",
                        name = currentUser.displayName ?: "Usuario",
                        monthlyBudget = 0.0,
                        profileImageUrl = "",
                        createdAt = Timestamp.now()
                    )
                    val createUserResult = userRepository.saveUser(newUser)
                    if (createUserResult.isFailure) {
                        android.util.Log.w("ExpenseRepository", "No se pudo crear usuario, pero continuando...")
                    }
                }
            }
            
            val expenseWithUser = expense.copy(
                userId = userId,
                createdAt = Timestamp.now()
            )
            
            android.util.Log.d("ExpenseRepository", "Guardando gasto para usuario: $userId")
            
            val expenseDto = expenseWithUser.toDto()
            
            // Validar que userId no esté vacío
            if (expenseDto.userId.isBlank()) {
                android.util.Log.e("ExpenseRepository", "ERROR: userId está vacío!")
                return Result.failure(Exception("userId no puede estar vacío"))
            }
            
            // Log del DTO que se está enviando (JSON completo)
            val gson = com.google.gson.Gson()
            val jsonBody = gson.toJson(expenseDto)
            android.util.Log.d("ExpenseRepository", "JSON a enviar: $jsonBody")
            android.util.Log.d("ExpenseRepository", "DTO a enviar: userId=${expenseDto.userId}, amount=${expenseDto.amount}, category=${expenseDto.category}, date=${expenseDto.date}, description=${expenseDto.description}")
            
            // Verificar que el JSON contiene user_id
            if (!jsonBody.contains("user_id")) {
                android.util.Log.e("ExpenseRepository", "ERROR: JSON no contiene 'user_id'! JSON: $jsonBody")
            }
            
            val response = backendApi.createExpense(expenseDto)
            
            if (response.isSuccessful && response.body() != null) {
                val expenseId = response.body()!!.id?.toString() ?: ""
                android.util.Log.d("ExpenseRepository", "Gasto guardado con ID: $expenseId")
                Result.success(expenseId)
            } else {
                // Capturar el mensaje de error del backend si está disponible
                val errorBody = response.errorBody()?.string()
                val errorMessage = errorBody ?: "Error al guardar gasto: ${response.code()}"
                android.util.Log.e("ExpenseRepository", "Error al guardar gasto: ${response.code()}, Mensaje: $errorMessage")
                android.util.Log.e("ExpenseRepository", "Response headers: ${response.headers()}")
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            android.util.Log.e("ExpenseRepository", "Error al guardar gasto", e)
            Result.failure(e)
        }
    }
    
    // Actualizar un gasto en Spring Boot
    suspend fun updateExpense(expense: Expense): Result<Unit> {
        return try {
            val expenseId = expense.id.toLongOrNull()
                ?: return Result.failure(Exception("ID de gasto inválido"))
            
            val expenseDto = expense.toDto()
            val response = backendApi.updateExpense(expenseId, expenseDto)
            
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Error al actualizar gasto: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    // Eliminar un gasto en Spring Boot
    suspend fun deleteExpense(expenseId: String): Result<Unit> {
        return try {
            val id = expenseId.toLongOrNull()
                ?: return Result.failure(Exception("ID de gasto inválido"))
            
            val response = backendApi.deleteExpense(id)
            
            if (response.isSuccessful) {
                android.util.Log.d("ExpenseRepository", "Gasto eliminado exitosamente")
                Result.success(Unit)
            } else {
                Result.failure(Exception("Error al eliminar gasto: ${response.code()}"))
            }
        } catch (e: Exception) {
            android.util.Log.e("ExpenseRepository", "Error al eliminar gasto", e)
            Result.failure(e)
        }
    }
    
    // Obtener gasto por ID desde Spring Boot
    suspend fun getExpenseById(expenseId: String): Result<Expense> {
        return try {
            val id = expenseId.toLongOrNull()
                ?: return Result.failure(Exception("ID de gasto inválido"))
            
            val response = backendApi.getExpenseById(id)
            
            if (response.isSuccessful && response.body() != null) {
                val expense = response.body()!!.toModel()
                Result.success(expense)
            } else {
                Result.failure(Exception("Gasto no encontrado"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    // Calcular total de gastos del mes desde Spring Boot
    suspend fun getTotalExpensesThisMonth(): Result<Double> {
        return try {
            val userId = getCurrentUserId()
            val response = backendApi.getExpensesByUserId(userId)
            
            if (response.isSuccessful && response.body() != null) {
                val calendar = Calendar.getInstance()
                calendar.set(Calendar.DAY_OF_MONTH, 1)
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                val startOfMonth = Timestamp(calendar.time)
                
                val expenses = response.body()!!.map { it.toModel() }
                val total = expenses
                    .filter { it.date.seconds >= startOfMonth.seconds }
                    .sumOf { it.amount }
                
                Result.success(total)
            } else {
                Result.success(0.0)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}


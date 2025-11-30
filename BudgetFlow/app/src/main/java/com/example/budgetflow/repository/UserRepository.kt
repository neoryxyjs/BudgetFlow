package com.example.budgetflow.repository

import android.net.Uri
import com.example.budgetflow.api.BackendApiClient
import com.example.budgetflow.api.mapper.ModelMapper.toDto
import com.example.budgetflow.api.mapper.ModelMapper.toModel
import com.example.budgetflow.model.User
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class UserRepository {
    private val backendApi = BackendApiClient.backendApi
    private val auth = FirebaseAuth.getInstance()
    private val storage = FirebaseStorage.getInstance() // Mantener para imágenes
    
    private fun getCurrentUserId(): String {
        return auth.currentUser?.uid ?: throw IllegalStateException("Usuario no autenticado")
    }
    
    // Obtener información del usuario actual desde Spring Boot
    fun getCurrentUser(): Flow<User?> = flow {
        val userId = getCurrentUserId()
        
        try {
            val response = backendApi.getUserById(userId)
            if (response.isSuccessful && response.body() != null) {
                val userDto = response.body()!!
                android.util.Log.d("UserRepository", "UserDto recibido (Flow) - monthlyBudget: ${userDto.monthlyBudget}")
                
                // Log del JSON recibido
                val gson = com.google.gson.Gson()
                val jsonBody = gson.toJson(userDto)
                android.util.Log.d("UserRepository", "JSON recibido del backend (Flow): $jsonBody")
                
                val user = userDto.toModel()
                android.util.Log.d("UserRepository", "User convertido (Flow) - monthlyBudget: ${user.monthlyBudget}")
                emit(user)
            } else {
                android.util.Log.w("UserRepository", "Usuario no encontrado en backend: $userId, código: ${response.code()}")
                emit(null)
            }
        } catch (e: Exception) {
            android.util.Log.e("UserRepository", "Error al obtener usuario desde backend", e)
            emit(null)
        }
    }
    
    // Obtener usuario actual directamente (sin Flow) para refrescar
    suspend fun getCurrentUserDirectly(): User? {
        val userId = getCurrentUserId()
        
        try {
            val response = backendApi.getUserById(userId)
            if (response.isSuccessful && response.body() != null) {
                val userDto = response.body()!!
                android.util.Log.d("UserRepository", "UserDto recibido - monthlyBudget: ${userDto.monthlyBudget}")
                
                // Log del JSON recibido
                val gson = com.google.gson.Gson()
                val jsonBody = gson.toJson(userDto)
                android.util.Log.d("UserRepository", "JSON recibido del backend: $jsonBody")
                
                val user = userDto.toModel()
                android.util.Log.d("UserRepository", "User convertido - monthlyBudget: ${user.monthlyBudget}")
                return user
            } else {
                android.util.Log.w("UserRepository", "Usuario no encontrado en backend: $userId, código: ${response.code()}")
                return null
            }
        } catch (e: Exception) {
            android.util.Log.e("UserRepository", "Error al obtener usuario desde backend", e)
            return null
        }
    }
    
    // Crear o actualizar usuario en Spring Boot
    suspend fun saveUser(user: User): Result<Unit> {
        return try {
            val userId = getCurrentUserId()
            val email = auth.currentUser?.email ?: ""
            
            val userWithId = user.copy(
                id = userId,
                email = email,
                createdAt = Timestamp.now()
            )
            
            val userDto = userWithId.toDto()
            val response = backendApi.createUser(userDto)
            
            if (response.isSuccessful) {
                android.util.Log.d("UserRepository", "Usuario guardado en backend exitosamente")
                Result.success(Unit)
            } else {
                android.util.Log.e("UserRepository", "Error al guardar usuario: ${response.code()}")
                Result.failure(Exception("Error al guardar usuario: ${response.code()}"))
            }
        } catch (e: Exception) {
            android.util.Log.e("UserRepository", "Error al guardar usuario", e)
            Result.failure(e)
        }
    }
    
    // Actualizar presupuesto mensual en Spring Boot
    suspend fun updateMonthlyBudget(budget: Double): Result<Unit> {
        return try {
            val userId = getCurrentUserId()
            android.util.Log.d("UserRepository", "Actualizando presupuesto para usuario: $userId, presupuesto: $budget")
            
            // Obtener usuario actual
            val currentUserResponse = backendApi.getUserById(userId)
            if (!currentUserResponse.isSuccessful || currentUserResponse.body() == null) {
                android.util.Log.e("UserRepository", "Usuario no encontrado en backend: $userId")
                return Result.failure(Exception("Usuario no encontrado"))
            }
            
            val currentUserDto = currentUserResponse.body()!!
            android.util.Log.d("UserRepository", "Usuario actual - presupuesto: ${currentUserDto.monthlyBudget}")
            
            val updatedUserDto = currentUserDto.copy(monthlyBudget = budget)
            android.util.Log.d("UserRepository", "DTO actualizado - presupuesto: ${updatedUserDto.monthlyBudget}")
            
            // Log del JSON que se está enviando
            val gson = com.google.gson.Gson()
            val jsonBody = gson.toJson(updatedUserDto)
            android.util.Log.d("UserRepository", "JSON a enviar: $jsonBody")
            
            val response = backendApi.updateUser(userId, updatedUserDto)
            
            if (response.isSuccessful) {
                android.util.Log.d("UserRepository", "Presupuesto actualizado en backend exitosamente")
                Result.success(Unit)
            } else {
                val errorBody = response.errorBody()?.string()
                android.util.Log.e("UserRepository", "Error al actualizar presupuesto: ${response.code()}, Mensaje: $errorBody")
                Result.failure(Exception("Error al actualizar presupuesto: ${response.code()} - $errorBody"))
            }
        } catch (e: Exception) {
            android.util.Log.e("UserRepository", "Error al actualizar presupuesto", e)
            Result.failure(e)
        }
    }
    
    // Verificar si el usuario existe en Spring Boot
    suspend fun userExists(): Result<Boolean> {
        return try {
            val userId = getCurrentUserId()
            val response = backendApi.getUserById(userId)
            Result.success(response.isSuccessful && response.body() != null)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    // Subir foto de perfil (mantener Firebase Storage)
    suspend fun uploadProfileImage(imageUri: Uri): Result<String> {
        return try {
            val userId = getCurrentUserId()
            android.util.Log.d("UserRepository", "Subiendo foto para usuario: $userId")
            
            val storageRef = storage.reference
                .child("profile_images")
                .child("$userId.jpg")
            
            // Subir imagen a Firebase Storage
            android.util.Log.d("UserRepository", "Iniciando subida a Storage...")
            storageRef.putFile(imageUri).await()
            
            // Obtener URL de descarga
            val downloadUrl = storageRef.downloadUrl.await()
            android.util.Log.d("UserRepository", "URL de descarga: $downloadUrl")
            
            // Actualizar URL en Spring Boot
            val currentUserResponse = backendApi.getUserById(userId)
            if (currentUserResponse.isSuccessful && currentUserResponse.body() != null) {
                val currentUserDto = currentUserResponse.body()!!
                val updatedUserDto = currentUserDto.copy(profileImageUrl = downloadUrl.toString())
                val updateResponse = backendApi.updateUser(userId, updatedUserDto)
                
                if (updateResponse.isSuccessful) {
                    android.util.Log.d("UserRepository", "URL guardada en backend exitosamente")
                    Result.success(downloadUrl.toString())
                } else {
                    Result.failure(Exception("Error al actualizar URL en backend"))
                }
            } else {
                Result.failure(Exception("Usuario no encontrado en backend"))
            }
        } catch (e: Exception) {
            android.util.Log.e("UserRepository", "Error al subir foto de perfil", e)
            Result.failure(e)
        }
    }
    
    // Actualizar nombre de usuario en Spring Boot
    suspend fun updateUserName(name: String): Result<Unit> {
        return try {
            val userId = getCurrentUserId()
            android.util.Log.d("UserRepository", "Actualizando nombre para usuario: $userId a: $name")
            
            // Obtener usuario actual
            val currentUserResponse = backendApi.getUserById(userId)
            if (!currentUserResponse.isSuccessful || currentUserResponse.body() == null) {
                return Result.failure(Exception("Usuario no encontrado"))
            }
            
            val currentUserDto = currentUserResponse.body()!!
            val updatedUserDto = currentUserDto.copy(name = name)
            
            val response = backendApi.updateUser(userId, updatedUserDto)
            
            if (response.isSuccessful) {
                android.util.Log.d("UserRepository", "Nombre actualizado en backend exitosamente")
                Result.success(Unit)
            } else {
                Result.failure(Exception("Error al actualizar nombre: ${response.code()}"))
            }
        } catch (e: Exception) {
            android.util.Log.e("UserRepository", "Error al actualizar nombre", e)
            Result.failure(e)
        }
    }
}


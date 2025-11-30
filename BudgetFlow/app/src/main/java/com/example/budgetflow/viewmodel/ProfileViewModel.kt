package com.example.budgetflow.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgetflow.model.User
import com.example.budgetflow.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private val userRepository = UserRepository()
    
    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user
    
    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val uiState: StateFlow<ProfileUiState> = _uiState
    
    init {
        loadUser()
    }
    
    private fun loadUser() {
        viewModelScope.launch {
            userRepository.getCurrentUser()
                .catch { e ->
                    _uiState.value = ProfileUiState.Error(e.message ?: "Error al cargar perfil")
                }
                .collect { user ->
                    _user.value = user
                    _uiState.value = ProfileUiState.Success
                }
        }
    }
    
    fun uploadProfileImage(imageUri: Uri) {
        viewModelScope.launch {
            _uiState.value = ProfileUiState.UploadingImage
            val result = userRepository.uploadProfileImage(imageUri)
            
            if (result.isSuccess) {
                // Recargar el usuario directamente para reflejar los cambios inmediatamente
                val updatedUser = userRepository.getCurrentUserDirectly()
                if (updatedUser != null) {
                    _user.value = updatedUser
                }
                _uiState.value = ProfileUiState.Success
            } else {
                _uiState.value = ProfileUiState.Error("Error al subir la imagen: ${result.exceptionOrNull()?.message}")
            }
        }
    }
    
    fun updateUserName(newName: String) {
        if (newName.isBlank()) {
            _uiState.value = ProfileUiState.Error("El nombre no puede estar vacío")
            return
        }
        
        viewModelScope.launch {
            _uiState.value = ProfileUiState.Loading
            val result = userRepository.updateUserName(newName)
            
            if (result.isSuccess) {
                // Recargar el usuario directamente para reflejar los cambios inmediatamente
                val updatedUser = userRepository.getCurrentUserDirectly()
                if (updatedUser != null) {
                    _user.value = updatedUser
                }
                _uiState.value = ProfileUiState.Success
            } else {
                _uiState.value = ProfileUiState.Error("Error al actualizar nombre: ${result.exceptionOrNull()?.message}")
            }
        }
    }
    
    fun updateMonthlyBudget(budget: Double) {
        if (budget <= 0) {
            _uiState.value = ProfileUiState.Error("El presupuesto debe ser mayor a 0")
            return
        }
        
        viewModelScope.launch {
            _uiState.value = ProfileUiState.Loading
            android.util.Log.d("ProfileViewModel", "Actualizando presupuesto a: $budget")
            val result = userRepository.updateMonthlyBudget(budget)
            
            if (result.isSuccess) {
                android.util.Log.d("ProfileViewModel", "Presupuesto actualizado exitosamente, recargando usuario...")
                // Recargar el usuario directamente (sin Flow) para reflejar los cambios inmediatamente
                val updatedUser = userRepository.getCurrentUserDirectly()
                if (updatedUser != null) {
                    android.util.Log.d("ProfileViewModel", "Usuario refrescado - presupuesto: ${updatedUser.monthlyBudget}")
                    _user.value = updatedUser
                } else {
                    android.util.Log.w("ProfileViewModel", "No se pudo refrescar usuario, usando loadUser()")
                    loadUser()
                }
                _uiState.value = ProfileUiState.Success
            } else {
                android.util.Log.e("ProfileViewModel", "Error al actualizar presupuesto: ${result.exceptionOrNull()?.message}")
                _uiState.value = ProfileUiState.Error("Error al actualizar presupuesto: ${result.exceptionOrNull()?.message}")
            }
        }
    }
    
    fun refreshUser() {
        viewModelScope.launch {
            android.util.Log.d("ProfileViewModel", "Refrescando usuario...")
            val updatedUser = userRepository.getCurrentUserDirectly()
            if (updatedUser != null) {
                android.util.Log.d("ProfileViewModel", "Usuario refrescado - presupuesto: ${updatedUser.monthlyBudget}")
                _user.value = updatedUser
            }
        }
    }
}

sealed class ProfileUiState {
    object Loading : ProfileUiState()
    object Success : ProfileUiState()
    object UploadingImage : ProfileUiState()
    data class Error(val message: String) : ProfileUiState()
}


package com.example.budgetflow.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgetflow.model.User
import com.example.budgetflow.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AuthViewModel : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    private val userRepository = UserRepository()

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                auth.signInWithEmailAndPassword(email, password).await()
                _authState.value = AuthState.Success
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Error desconocido")
            }
        }
    }

    fun register(email: String, password: String, nombre: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                // Crear usuario en Firebase Auth
                val result = auth.createUserWithEmailAndPassword(email, password).await()
                
                // Guardar información adicional en Firestore
                val user = User(
                    id = result.user?.uid ?: "",
                    name = nombre,
                    email = email,
                    monthlyBudget = 0.0,
                    profileImageUrl = ""
                )
                
                val saveResult = userRepository.saveUser(user)
                if (saveResult.isSuccess) {
                    _authState.value = AuthState.Success
                } else {
                    // Si falla guardar en Firestore, eliminar usuario de Auth
                    auth.currentUser?.delete()
                    _authState.value = AuthState.Error("Error al crear el perfil de usuario")
                }
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Error desconocido")
            }
        }
    }

    fun isUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }

    fun logout() {
        auth.signOut()
        _authState.value = AuthState.Idle
    }
    
    fun resetState() {
        _authState.value = AuthState.Idle
    }
}

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object Success : AuthState()
    data class Error(val message: String) : AuthState()
}
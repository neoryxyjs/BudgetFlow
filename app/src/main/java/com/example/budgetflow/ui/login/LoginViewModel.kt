package com.example.budgetflow.ui.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginViewModel : ViewModel() {

    // Estado del login - usar mutableStateOf para Compose
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    // Credenciales hardcodeadas
    private val validUsers = mapOf(
        "usuario@ejemplo.com" to "123456",
        "test@test.com" to "password"
    )

    fun login(email: String, password: String) {
        // Verificación inmediata (sin corrutinas por ahora)
        val isValid = validUsers[email] == password

        if (isValid) {
            _loginState.value = LoginState.Success
        } else {
            _loginState.value = LoginState.Error("Credenciales incorrectas")
        }
    }

    // Función para resetear el estado
    fun resetState() {
        _loginState.value = LoginState.Idle
    }
}

// Estados posibles del login
sealed class LoginState {
    object Idle : LoginState()
    object Success : LoginState()
    data class Error(val message: String) : LoginState()
}
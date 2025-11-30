package com.example.budgetflow.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgetflow.model.Expense
import com.example.budgetflow.model.User
import com.example.budgetflow.repository.ExpenseRepository
import com.example.budgetflow.repository.ExchangeRateRepository
import com.example.budgetflow.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class DashboardViewModel : ViewModel() {
    private val expenseRepository = ExpenseRepository()
    private val userRepository = UserRepository()
    private val exchangeRateRepository = ExchangeRateRepository()
    
    private val _uiState = MutableStateFlow<DashboardUiState>(DashboardUiState.Loading)
    val uiState: StateFlow<DashboardUiState> = _uiState
    
    private val _expenses = MutableStateFlow<List<Expense>>(emptyList())
    val expenses: StateFlow<List<Expense>> = _expenses
    
    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user
    
    private val _exchangeRates = MutableStateFlow<Map<String, Double>>(emptyMap())
    val exchangeRates: StateFlow<Map<String, Double>> = _exchangeRates
    
    init {
        loadData()
        loadExchangeRates()
    }
    
    private fun loadExchangeRates() {
        viewModelScope.launch {
            exchangeRateRepository.getExchangeRates()
                .catch { e ->
                    android.util.Log.e("DashboardViewModel", "Error al cargar tasas de cambio", e)
                }
                .collect { result ->
                    result.onSuccess { rates ->
                        _exchangeRates.value = rates
                        android.util.Log.d("DashboardViewModel", "Tasas de cambio cargadas: ${rates.size} monedas")
                    }.onFailure { e ->
                        android.util.Log.e("DashboardViewModel", "Error al obtener tasas de cambio", e)
                    }
                }
        }
    }
    
    private fun loadData() {
        viewModelScope.launch {
            // Cargar usuario desde Spring Boot
            userRepository.getCurrentUser()
                .catch { e ->
                    android.util.Log.e("DashboardViewModel", "Error al cargar usuario", e)
                    _uiState.value = DashboardUiState.Error(e.message ?: "Error al cargar usuario")
                }
                .collect { user ->
                    android.util.Log.d("DashboardViewModel", "Usuario cargado: ${user?.name ?: "null"} (${user?.email ?: "null"}), Presupuesto: ${user?.monthlyBudget ?: 0.0}")
                    _user.value = user
                }
        }
        
        viewModelScope.launch {
            // Cargar gastos del mes desde Spring Boot
            expenseRepository.getExpensesThisMonth()
                .catch { e ->
                    android.util.Log.e("DashboardViewModel", "Error al cargar gastos", e)
                    _uiState.value = DashboardUiState.Error(e.message ?: "Error al cargar gastos")
                }
                .collect { expenses ->
                    android.util.Log.d("DashboardViewModel", "Gastos cargados desde backend: ${expenses.size}")
                    expenses.forEach { expense ->
                        android.util.Log.d("DashboardViewModel", "  - Gasto: $${expense.amount} (${expense.category})")
                    }
                    _expenses.value = expenses
                    val total = expenses.sumOf { it.amount }
                    android.util.Log.d("DashboardViewModel", "Total calculado: $$total")
                    _uiState.value = DashboardUiState.Success
                }
        }
    }
    
    fun refreshData() {
        android.util.Log.d("DashboardViewModel", "Refrescando datos...")
        // Cancelar las corrutinas anteriores y recargar
        loadData()
    }
    
    fun refreshUser() {
        viewModelScope.launch {
            android.util.Log.d("DashboardViewModel", "Refrescando usuario...")
            try {
                val user = userRepository.getCurrentUserDirectly()
                if (user != null) {
                    android.util.Log.d("DashboardViewModel", "Usuario refrescado: ${user.name}, Presupuesto: ${user.monthlyBudget}")
                    _user.value = user
                } else {
                    android.util.Log.w("DashboardViewModel", "No se pudo refrescar usuario: usuario es null")
                }
            } catch (e: Exception) {
                android.util.Log.e("DashboardViewModel", "Error al refrescar usuario", e)
            }
        }
    }
    
    fun deleteExpense(expenseId: String) {
        viewModelScope.launch {
            val result = expenseRepository.deleteExpense(expenseId)
            if (result.isFailure) {
                _uiState.value = DashboardUiState.Error("Error al eliminar gasto")
            } else {
                // Refrescar datos después de eliminar
                refreshData()
            }
        }
    }
    
    fun getTotalExpenses(): Double {
        return _expenses.value.sumOf { it.amount }
    }
    
    fun getRemainingBudget(): Double {
        val budget = _user.value?.monthlyBudget ?: 0.0
        val spent = getTotalExpenses()
        return budget - spent
    }
}

sealed class DashboardUiState {
    object Loading : DashboardUiState()
    object Success : DashboardUiState()
    data class Error(val message: String) : DashboardUiState()
}


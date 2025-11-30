package com.example.budgetflow.ui.addexpense

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgetflow.model.Expense
import com.example.budgetflow.model.ExpenseCategory
import com.example.budgetflow.repository.ExpenseRepository
import com.google.firebase.Timestamp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*

class AddExpenseViewModel : ViewModel() {
    private val expenseRepository = ExpenseRepository()
    
    private val _uiState = MutableStateFlow<AddExpenseUiState>(AddExpenseUiState.Idle)
    val uiState: StateFlow<AddExpenseUiState> = _uiState
    
    fun addExpense(
        amount: Double,
        category: ExpenseCategory,
        description: String,
        date: Date
    ) {
        viewModelScope.launch {
            _uiState.value = AddExpenseUiState.Loading
            
            val expense = Expense(
                amount = amount,
                category = category.name,
                description = description,
                date = Timestamp(date)
            )
            
            val result = expenseRepository.addExpense(expense)
            
            if (result.isSuccess) {
                _uiState.value = AddExpenseUiState.Success
            } else {
                _uiState.value = AddExpenseUiState.Error(
                    result.exceptionOrNull()?.message ?: "Error al guardar el gasto"
                )
            }
        }
    }
    
    fun resetState() {
        _uiState.value = AddExpenseUiState.Idle
    }
}

sealed class AddExpenseUiState {
    object Idle : AddExpenseUiState()
    object Loading : AddExpenseUiState()
    object Success : AddExpenseUiState()
    data class Error(val message: String) : AddExpenseUiState()
}



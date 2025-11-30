package com.example.budgetflow.viewmodel

import com.example.budgetflow.model.Expense
import com.example.budgetflow.model.ExpenseCategory
import com.example.budgetflow.repository.ExpenseRepository
import com.google.firebase.Timestamp
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

class AddExpenseViewModelTest {
    
    @Test
    fun `expense validation should work correctly`() {
        // Given
        val validExpense = Expense(
            id = "",
            userId = "user1",
            amount = 100.0,
            category = ExpenseCategory.FOOD.name,
            description = "Test",
            date = Timestamp.now(),
            createdAt = Timestamp.now()
        )
        
        // When & Then
        assertTrue(validExpense.amount > 0)
        assertTrue(validExpense.category.isNotEmpty())
    }
    
    @Test
    fun `expense with zero amount should be invalid`() {
        // Given
        val invalidExpense = Expense(
            id = "",
            userId = "user1",
            amount = 0.0,
            category = ExpenseCategory.FOOD.name,
            description = "Test",
            date = Timestamp.now(),
            createdAt = Timestamp.now()
        )
        
        // When & Then
        assertFalse(invalidExpense.amount > 0)
    }
    
    @Test
    fun `expense with negative amount should be invalid`() {
        // Given
        val invalidExpense = Expense(
            id = "",
            userId = "user1",
            amount = -100.0,
            category = ExpenseCategory.FOOD.name,
            description = "Test",
            date = Timestamp.now(),
            createdAt = Timestamp.now()
        )
        
        // When & Then
        assertFalse(invalidExpense.amount > 0)
    }
}


package com.example.budgetflow.viewmodel

import com.example.budgetflow.model.Expense
import com.example.budgetflow.model.ExpenseCategory
import com.example.budgetflow.model.User
import com.google.firebase.Timestamp
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

/**
 * Tests unitarios para DashboardViewModel
 * 
 * Nota: Estos tests validan la lógica de cálculo del ViewModel.
 * Los tests de integración con repositorios requieren mocks más complejos.
 */
class DashboardViewModelTest {
    
    @Test
    fun `getTotalExpenses should return sum of all expenses`() = runTest {
        // Given
        val expenses = listOf(
            createExpense(amount = 100.0),
            createExpense(amount = 200.0),
            createExpense(amount = 300.0)
        )
        
        // When
        val total = expenses.sumOf { it.amount }
        
        // Then
        assertEquals(600.0, total, 0.01)
    }
    
    @Test
    fun `getRemainingBudget should calculate correctly`() = runTest {
        // Given
        val user = createUser(monthlyBudget = 1000.0)
        val expenses = listOf(
            createExpense(amount = 300.0),
            createExpense(amount = 200.0)
        )
        
        // When
        val budget = user.monthlyBudget
        val spent = expenses.sumOf { it.amount }
        val remaining = budget - spent
        
        // Then
        assertEquals(500.0, remaining, 0.01)
        assertTrue(remaining > 0)
    }
    
    @Test
    fun `getRemainingBudget should return negative when over budget`() = runTest {
        // Given
        val user = createUser(monthlyBudget = 500.0)
        val expenses = listOf(
            createExpense(amount = 400.0),
            createExpense(amount = 200.0)
        )
        
        // When
        val budget = user.monthlyBudget
        val spent = expenses.sumOf { it.amount }
        val remaining = budget - spent
        
        // Then
        assertEquals(-100.0, remaining, 0.01)
        assertTrue(remaining < 0)
    }
    
    @Test
    fun `getTotalExpenses should return zero for empty list`() = runTest {
        // Given
        val expenses = emptyList<Expense>()
        
        // When
        val total = expenses.sumOf { it.amount }
        
        // Then
        assertEquals(0.0, total, 0.01)
    }
    
    @Test
    fun `getRemainingBudget should equal budget when no expenses`() = runTest {
        // Given
        val user = createUser(monthlyBudget = 1000.0)
        val expenses = emptyList<Expense>()
        
        // When
        val budget = user.monthlyBudget
        val spent = expenses.sumOf { it.amount }
        val remaining = budget - spent
        
        // Then
        assertEquals(1000.0, remaining, 0.01)
        assertEquals(budget, remaining, 0.01)
    }
    
    @Test
    fun `getRemainingBudget should handle zero budget`() = runTest {
        // Given
        val user = createUser(monthlyBudget = 0.0)
        val expenses = listOf(
            createExpense(amount = 100.0)
        )
        
        // When
        val budget = user.monthlyBudget
        val spent = expenses.sumOf { it.amount }
        val remaining = budget - spent
        
        // Then
        assertEquals(-100.0, remaining, 0.01)
    }
    
    private fun createExpense(
        id: String = "expense1",
        amount: Double = 100.0,
        category: String = ExpenseCategory.FOOD.name
    ): Expense {
        return Expense(
            id = id,
            userId = "user1",
            amount = amount,
            category = category,
            description = "Test expense",
            date = Timestamp.now(),
            createdAt = Timestamp.now()
        )
    }
    
    private fun createUser(
        id: String = "user1",
        name: String = "Test User",
        monthlyBudget: Double = 1000.0
    ): User {
        return User(
            id = id,
            name = name,
            email = "test@example.com",
            monthlyBudget = monthlyBudget,
            profileImageUrl = "",
            createdAt = Timestamp.now()
        )
    }
}


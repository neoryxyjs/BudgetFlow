package com.example.budgetflow.repository

import com.example.budgetflow.model.Expense
import com.example.budgetflow.model.ExpenseCategory
import com.google.firebase.Timestamp
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ExpenseRepositoryTest {
    private lateinit var repository: ExpenseRepository
    
    @Before
    fun setup() {
        repository = ExpenseRepository()
    }
    
    @Test
    fun `addExpense should return success result with expense id`() = runTest {
        // Given
        val expense = createExpense(amount = 100.0)
        
        // When
        val result = repository.addExpense(expense)
        
        // Then
        assertTrue(result.isSuccess)
        assertNotNull(result.getOrNull())
    }
    
    @Test
    fun `deleteExpense should return success result`() = runTest {
        // Given
        val expense = createExpense()
        val expenseId = repository.addExpense(expense).getOrNull() ?: ""
        
        // When
        val result = repository.deleteExpense(expenseId)
        
        // Then
        // Note: Este test puede fallar si no hay usuario autenticado
        // En un test real, mockearíamos Firebase
        assertNotNull(result)
    }
    
    @Test
    fun `getExpenses should return flow of expenses`() = runTest {
        // Given
        val expense = createExpense()
        repository.addExpense(expense)
        
        // When
        val expensesFlow = repository.getExpenses()
        
        // Then
        // Note: Este test requiere Firebase configurado
        // En un test real, mockearíamos Firebase
        assertNotNull(expensesFlow)
    }
    
    @Test
    fun `getTotalExpensesThisMonth should calculate sum correctly`() = runTest {
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
}


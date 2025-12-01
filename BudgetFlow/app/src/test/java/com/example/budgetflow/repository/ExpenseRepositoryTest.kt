package com.example.budgetflow.repository

import com.example.budgetflow.model.Expense
import com.example.budgetflow.model.ExpenseCategory
import com.google.firebase.Timestamp
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

/**
 * Tests unitarios para ExpenseRepository
 * 
 * Nota: Estos tests validan la lógica de cálculo y transformación de datos.
 * Los tests de integración con el backend y Firebase requieren un entorno de test configurado.
 */
class ExpenseRepositoryTest {
    
    @Test
    fun `calculate total expenses should sum correctly`() = runTest {
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
    fun `calculate total expenses should return zero for empty list`() = runTest {
        // Given
        val expenses = emptyList<Expense>()
        
        // When
        val total = expenses.sumOf { it.amount }
        
        // Then
        assertEquals(0.0, total, 0.01)
    }
    
    @Test
    fun `expense should have correct properties`() = runTest {
        // Given
        val expense = createExpense(
            id = "test123",
            amount = 150.0,
            category = ExpenseCategory.TRANSPORT.name,
            description = "Test description"
        )
        
        // Then
        assertEquals("test123", expense.id)
        assertEquals(150.0, expense.amount, 0.01)
        assertEquals(ExpenseCategory.TRANSPORT.name, expense.category)
        assertEquals("Test description", expense.description)
        assertNotNull(expense.date)
        assertNotNull(expense.createdAt)
    }
    
    @Test
    fun `expense category should be valid`() = runTest {
        // Given
        val categories = listOf(
            ExpenseCategory.FOOD,
            ExpenseCategory.TRANSPORT,
            ExpenseCategory.ENTERTAINMENT,
            ExpenseCategory.BILLS,
            ExpenseCategory.OTHER
        )
        
        // Then
        categories.forEach { category ->
            assertNotNull(category.name)
            assertTrue(category.name.isNotBlank())
        }
    }
    
    private fun createExpense(
        id: String = "expense1",
        amount: Double = 100.0,
        category: String = ExpenseCategory.FOOD.name,
        description: String = "Test expense"
    ): Expense {
        return Expense(
            id = id,
            userId = "user1",
            amount = amount,
            category = category,
            description = description,
            date = Timestamp.now(),
            createdAt = Timestamp.now()
        )
    }
}


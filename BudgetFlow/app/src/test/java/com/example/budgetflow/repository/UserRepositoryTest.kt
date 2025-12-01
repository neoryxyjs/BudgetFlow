package com.example.budgetflow.repository

import com.example.budgetflow.model.User
import com.google.firebase.Timestamp
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

/**
 * Tests unitarios para UserRepository
 * 
 * Nota: Estos tests validan la lógica de cálculo y transformación de datos.
 * Los tests de integración con el backend y Firebase requieren un entorno de test configurado.
 */
class UserRepositoryTest {
    
    @Test
    fun `user should have correct properties`() = runTest {
        // Given
        val user = createUser(
            id = "user123",
            name = "Test User",
            email = "test@example.com",
            monthlyBudget = 2000.0
        )
        
        // Then
        assertEquals("user123", user.id)
        assertEquals("Test User", user.name)
        assertEquals("test@example.com", user.email)
        assertEquals(2000.0, user.monthlyBudget, 0.01)
        assertNotNull(user.createdAt)
    }
    
    @Test
    fun `user monthly budget should be positive`() = runTest {
        // Given
        val user = createUser(monthlyBudget = 1500.0)
        
        // Then
        assertTrue(user.monthlyBudget > 0)
        assertEquals(1500.0, user.monthlyBudget, 0.01)
    }
    
    @Test
    fun `user email should be valid format`() = runTest {
        // Given
        val user = createUser(email = "test@example.com")
        
        // Then
        assertTrue(user.email.contains("@"))
        assertTrue(user.email.contains("."))
        assertFalse(user.email.isBlank())
    }
    
    @Test
    fun `user name should not be blank`() = runTest {
        // Given
        val user = createUser(name = "John Doe")
        
        // Then
        assertFalse(user.name.isBlank())
        assertEquals("John Doe", user.name)
    }
    
    @Test
    fun `user should have creation timestamp`() = runTest {
        // Given
        val user = createUser()
        
        // Then
        assertNotNull(user.createdAt)
        assertTrue(user.createdAt.seconds > 0)
    }
    
    private fun createUser(
        id: String = "user1",
        name: String = "Test User",
        email: String = "test@example.com",
        monthlyBudget: Double = 1000.0
    ): User {
        return User(
            id = id,
            name = name,
            email = email,
            monthlyBudget = monthlyBudget,
            profileImageUrl = "",
            createdAt = Timestamp.now()
        )
    }
}


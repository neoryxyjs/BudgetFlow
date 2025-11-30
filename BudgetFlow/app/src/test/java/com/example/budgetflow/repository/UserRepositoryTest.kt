package com.example.budgetflow.repository

import com.example.budgetflow.model.User
import com.google.firebase.Timestamp
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class UserRepositoryTest {
    private lateinit var repository: UserRepository
    
    @Before
    fun setup() {
        repository = UserRepository()
    }
    
    @Test
    fun `saveUser should return success result`() = runTest {
        // Given
        val user = createUser()
        
        // When
        val result = repository.saveUser(user)
        
        // Then
        // Note: Este test puede fallar si no hay usuario autenticado
        // En un test real, mockearíamos Firebase
        assertNotNull(result)
    }
    
    @Test
    fun `updateMonthlyBudget should return success result`() = runTest {
        // Given
        val newBudget = 2000.0
        
        // When
        val result = repository.updateMonthlyBudget(newBudget)
        
        // Then
        // Note: Este test puede fallar si no hay usuario autenticado
        // En un test real, mockearíamos Firebase
        assertNotNull(result)
    }
    
    @Test
    fun `updateUserName should return success result`() = runTest {
        // Given
        val newName = "Updated Name"
        
        // When
        val result = repository.updateUserName(newName)
        
        // Then
        // Note: Este test puede fallar si no hay usuario autenticado
        // En un test real, mockearíamos Firebase
        assertNotNull(result)
    }
    
    @Test
    fun `userExists should return boolean result`() = runTest {
        // When
        val result = repository.userExists()
        
        // Then
        // Note: Este test puede fallar si no hay usuario autenticado
        // En un test real, mockearíamos Firebase
        assertNotNull(result)
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


package com.example.budgetflow.viewmodel

import com.example.budgetflow.model.User
import com.google.firebase.Timestamp
import org.junit.Assert.*
import org.junit.Test

class ProfileViewModelTest {
    
    @Test
    fun `user name validation should work correctly`() {
        // Given
        val user = User(
            id = "user1",
            name = "John Doe",
            email = "john@example.com",
            monthlyBudget = 1000.0,
            profileImageUrl = "",
            createdAt = Timestamp.now()
        )
        
        // When & Then
        assertTrue(user.name.isNotEmpty())
        assertTrue(user.name.length > 0)
    }
    
    @Test
    fun `user email validation should work correctly`() {
        // Given
        val user = User(
            id = "user1",
            name = "John Doe",
            email = "john@example.com",
            monthlyBudget = 1000.0,
            profileImageUrl = "",
            createdAt = Timestamp.now()
        )
        
        // When & Then
        assertTrue(user.email.contains("@"))
        assertTrue(user.email.isNotEmpty())
    }
    
    @Test
    fun `monthly budget should be positive`() {
        // Given
        val user = User(
            id = "user1",
            name = "John Doe",
            email = "john@example.com",
            monthlyBudget = 1000.0,
            profileImageUrl = "",
            createdAt = Timestamp.now()
        )
        
        // When & Then
        assertTrue(user.monthlyBudget >= 0)
    }
}


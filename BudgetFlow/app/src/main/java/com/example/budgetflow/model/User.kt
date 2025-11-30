package com.example.budgetflow.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class User(
    @DocumentId
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val monthlyBudget: Double = 0.0,
    val profileImageUrl: String = "",
    val createdAt: Timestamp = Timestamp.now()
) {
    // Constructor vacío necesario para Firestore
    constructor() : this("", "", "", 0.0, "", Timestamp.now())
    
    fun toMap(): Map<String, Any> {
        return hashMapOf(
            "name" to name,
            "email" to email,
            "monthlyBudget" to monthlyBudget,
            "profileImageUrl" to profileImageUrl,
            "createdAt" to createdAt
        )
    }
    
    companion object {
        fun fromMap(id: String, map: Map<String, Any>): User {
            return User(
                id = id,
                name = map["name"] as? String ?: "",
                email = map["email"] as? String ?: "",
                monthlyBudget = (map["monthlyBudget"] as? Number)?.toDouble() ?: 0.0,
                profileImageUrl = map["profileImageUrl"] as? String ?: "",
                createdAt = map["createdAt"] as? Timestamp ?: Timestamp.now()
            )
        }
    }
}


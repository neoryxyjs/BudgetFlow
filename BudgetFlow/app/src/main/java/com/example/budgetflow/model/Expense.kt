package com.example.budgetflow.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class Expense(
    @DocumentId
    val id: String = "",
    val userId: String = "",
    val amount: Double = 0.0,
    val category: String = ExpenseCategory.OTHER.name,
    val description: String = "",
    val date: Timestamp = Timestamp.now(),
    val createdAt: Timestamp = Timestamp.now()
) {
    // Constructor vacío necesario para Firestore
    constructor() : this("", "", 0.0, ExpenseCategory.OTHER.name, "", Timestamp.now(), Timestamp.now())
    
    fun toMap(): Map<String, Any> {
        return hashMapOf(
            "userId" to userId,
            "amount" to amount,
            "category" to category,
            "description" to description,
            "date" to date,
            "createdAt" to createdAt
        )
    }
    
    companion object {
        fun fromMap(id: String, map: Map<String, Any>): Expense {
            return Expense(
                id = id,
                userId = map["userId"] as? String ?: "",
                amount = (map["amount"] as? Number)?.toDouble() ?: 0.0,
                category = map["category"] as? String ?: ExpenseCategory.OTHER.name,
                description = map["description"] as? String ?: "",
                date = map["date"] as? Timestamp ?: Timestamp.now(),
                createdAt = map["createdAt"] as? Timestamp ?: Timestamp.now()
            )
        }
    }
}



package com.example.budgetflow.model

import androidx.compose.ui.graphics.Color
import com.example.budgetflow.ui.theme.*

enum class ExpenseCategory(
    val displayName: String,
    val color: Color,
    val icon: String
) {
    FOOD("Comida", CategoryFood, "🍔"),
    TRANSPORT("Transporte", CategoryTransport, "🚗"),
    SHOPPING("Compras", CategoryShopping, "🛍️"),
    ENTERTAINMENT("Entretenimiento", CategoryEntertainment, "🎮"),
    HEALTH("Salud", CategoryHealth, "🏥"),
    BILLS("Facturas", Color(0xFFE74C3C), "📄"),
    EDUCATION("Educación", Color(0xFF3498DB), "📚"),
    SAVINGS("Ahorros", Color(0xFF27AE60), "💰"),
    OTHER("Otros", CategoryOther, "📦");

    companion object {
        fun fromString(value: String): ExpenseCategory {
            return values().find { it.name == value } ?: OTHER
        }
    }
}



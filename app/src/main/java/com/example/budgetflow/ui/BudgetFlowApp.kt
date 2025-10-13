package com.example.budgetflow.ui

import androidx.compose.runtime.*
import com.example.budgetflow.ui.login.LoginScreen
import com.example.budgetflow.ui.dashboard.DashboardScreen

@Composable
fun BudgetFlowApp() {
    var isLoggedIn by remember { mutableStateOf(false) }

    if (isLoggedIn) {
        DashboardScreen(
            onLogout = { isLoggedIn = false }
        )
    } else {
        LoginScreen(
            onLoginSuccess = { isLoggedIn = true }
        )
    }
}
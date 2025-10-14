package com.example.budgetflow.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.example.budgetflow.ui.dashboard.DashboardScreen
import com.example.budgetflow.ui.login.LoginScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun BudgetFlowApp() {
    val navController = rememberNavController()
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser

    NavHost(
        navController = navController,
        startDestination = if (currentUser != null) "dashboard" else "login"
    ) {
        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate("dashboard") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onNavigateToRegister = {   // 👈 agregamos esto
                    navController.navigate("register")
                }
            )
        }

        composable("dashboard") {
            DashboardScreen(
                onLogout = {
                    auth.signOut()
                    navController.navigate("login") {
                        popUpTo("dashboard") { inclusive = true }
                    }
                }
            )
        }
    }
}


package com.example.budgetflow.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.example.budgetflow.ui.addexpense.AddExpenseScreen
import com.example.budgetflow.ui.dashboard.DashboardScreen
import com.example.budgetflow.ui.login.LoginScreen
import com.example.budgetflow.ui.profile.ProfileScreen
import com.example.budgetflow.ui.register.RegisterScreen
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
                onNavigateToRegister = {
                    navController.navigate("register")
                }
            )
        }

        composable("register") {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate("dashboard") {
                        popUpTo("register") { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.popBackStack()
                }
            )
        }

        composable("dashboard") {
            // Detectar cuando se vuelve a dashboard usando el back stack entry
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            var refreshKey by remember { mutableStateOf(0) }
            
            // Incrementar refreshKey cuando se vuelve a dashboard
            androidx.compose.runtime.LaunchedEffect(navBackStackEntry?.id) {
                refreshKey++
            }
            
            DashboardScreen(
                refreshKey = refreshKey,
                onLogout = {
                    auth.signOut()
                    navController.navigate("login") {
                        popUpTo("dashboard") { inclusive = true }
                    }
                },
                onNavigateToAddExpense = {
                    navController.navigate("add_expense")
                },
                onNavigateToProfile = {
                    navController.navigate("profile")
                }
            )
        }
        
        composable("add_expense") {
            AddExpenseScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onExpenseAdded = {
                    navController.popBackStack()
                }
            )
        }
        
        composable("profile") {
            ProfileScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}


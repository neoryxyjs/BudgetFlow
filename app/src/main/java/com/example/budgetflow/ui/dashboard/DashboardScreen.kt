package com.example.budgetflow.ui.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.ExperimentalMaterial3Api  // ← Agregar este import

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onLogout: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Gastos") },
                actions = {
                    Button(
                        onClick = onLogout,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer,
                            contentColor = MaterialTheme.colorScheme.onErrorContainer
                        )
                    ) {
                        Text("Salir")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Resumen Mensual",
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Gastos totales: \$0.00")
                    Text("Presupuesto restante: \$0.00")
                }
            }

            // Botón para agregar gasto
            Button(
                onClick = {
                    println("🔹 Botón Agregar Gasto presionado")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Agregar Gasto")
            }

            // Lista de gastos (vacía por ahora)
            Text(
                text = "Tus gastos aparecerán aquí",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}
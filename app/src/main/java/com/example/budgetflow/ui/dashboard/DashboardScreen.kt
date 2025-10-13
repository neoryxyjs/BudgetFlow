package com.example.budgetflow.ui.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onLogout: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Mis Gastos") },
                actions = {
                    IconButton(onClick = onLogout) {
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
            // Tarjeta de resumen
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
                onClick = { /* Navegar a agregar gasto */ },
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
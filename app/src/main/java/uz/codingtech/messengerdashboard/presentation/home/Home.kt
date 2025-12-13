package uz.codingtech.messengerdashboard.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@ExperimentalMaterial3Api
@Composable
fun Home(navController: NavController, modifier: Modifier = Modifier) {
    OrderListScreen(navController = navController, modifier = modifier)
}

// Data model

data class Order(
    val title: String,
    val createdDate: String,
    val cpm: String,
    val budget: String,
    val url: String,
    val isCompleted: Boolean,
    val isActive: Boolean
)

@ExperimentalMaterial3Api
@Composable
fun OrderListScreen(navController: NavController, modifier: Modifier = Modifier) {
    val orders = remember {
        listOf(
            Order("Order #1", "2025-01-01", "$2.5", "$100", "https://example.com", isCompleted = true, isActive = false),
            Order("Order #2", "2025-01-03", "$3.1", "$250", "https://google.com", isCompleted = false, isActive = true),
            Order("Order #3", "2025-01-05", "$1.9", "$80", "https://openai.com", isCompleted = false, isActive = false)
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Orders") },
                actions = {
                    IconButton(onClick = {
                        navController.navigate("login") {
                            popUpTo("home") { inclusive = true }
                        }
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Logout,
                            contentDescription = "Logout"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                // navController.navigate("add_order")
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add Order")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(orders) { order ->
                OrderItem(order = order)
            }
        }
    }
}

@Composable
fun OrderItem(order: Order) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = order.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                StatusChip(
                    text = if (order.isCompleted) "Completed" else "In progress",
                    statusType = if (order.isCompleted) StatusType.COMPLETED else StatusType.IN_PROGRESS
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Created: ${order.createdDate}",
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "CPM: ${order.cpm}")
                Text(text = "Budget: ${order.budget}")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = order.url,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )

                StatusChip(
                    text = if (order.isActive) "Active" else "Inactive",
                    statusType = if (order.isActive) StatusType.ACTIVE else StatusType.INACTIVE
                )
            }
        }
    }
}

enum class StatusType {
    COMPLETED,
    IN_PROGRESS,
    ACTIVE,
    INACTIVE
}

@Composable
fun StatusChip(text: String, statusType: StatusType) {
    val backgroundColor = when (statusType) {
        StatusType.COMPLETED -> Color(0xFF4CAF50) // Green
        StatusType.IN_PROGRESS -> MaterialTheme.colorScheme.primary // Blue
        StatusType.ACTIVE -> Color(0xFF4CAF50) // Green
        StatusType.INACTIVE -> MaterialTheme.colorScheme.error // Red
    }

    val contentColor = Color.White

    Surface(
        color = backgroundColor,
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelSmall,
            color = contentColor
        )
    }
}







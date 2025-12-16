package uz.codingtech.messengerdashboard.presentation.home

import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import uz.codingtech.messengerdashboard.domain.models.Order
import uz.codingtech.messengerdashboard.presentation.common.navigation.AddOrder
import uz.codingtech.messengerdashboard.presentation.common.navigation.OrderDetailsInfo
import uz.codingtech.messengerdashboard.presentation.main_app.vm.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navController: NavController, viewModel: MainViewModel = hiltViewModel()) {

    val userData by viewModel.userData.collectAsState()
    val userBalance by viewModel.userBalance.collectAsState()

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_CREATE -> {
                    viewModel.getBalance()
                }

                Lifecycle.Event.ON_RESUME -> {

                }

                Lifecycle.Event.ON_PAUSE -> {

                }

                Lifecycle.Event.ON_DESTROY -> {

                }

                else -> {}
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }


    val orders = remember {
        listOf(
            Order(
                "Order #1",
                1000001,
                "KunUz1",
                "$2.5",
                "$100",
                isCompleted = true,
                isActive = false,
                40000,
                40000,
                "2025-01-01"
            ),
            Order(
                "Order #2",
                1000002,
                "KunUz2",
                "$5",
                "$100",
                isCompleted = false,
                isActive = true,
                20000,
                10000,
                "2025-01-02"
            ),
            Order(
                "Order #3",
                1000003,
                "KunUz3",
                "$2",
                "$100",
                isCompleted = false,
                isActive = false,
                50000,
                10000,
                "2025-01-03"
            ),
            Order(
                "Order #1",
                1000004,
                "KunUz1",
                "$1",
                "$100",
                isCompleted = false,
                isActive = true,
                100000,
                50000,
                "2025-01-04"
            )
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Orders") },
                actions = {
                    Text(text = "${userBalance?.amount} $")
                    IconButton(onClick = {
                        viewModel.logout()
                        navController.popBackStack()
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
                navController.navigate(AddOrder)
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
                OrderItem(
                    order = order,
                    onClick = {
                        navController.navigate(OrderDetailsInfo(id =  "1"))
                    }
                )
            }
        }
    }
}
@Composable
fun OrderItem(order: Order, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
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
                    text = order.channelUsername,
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
        StatusType.COMPLETED -> Color(0xFF4CAF50)
        StatusType.IN_PROGRESS -> MaterialTheme.colorScheme.primary
        StatusType.ACTIVE -> Color(0xFF4CAF50)
        StatusType.INACTIVE -> MaterialTheme.colorScheme.error
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







package uz.codingtech.messengerdashboard.presentation.orders

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FileCopy
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import uz.codingtech.messengerdashboard.domain.models.OrderModel
import uz.codingtech.messengerdashboard.presentation.common.navigation.AddOrder
import uz.codingtech.messengerdashboard.presentation.common.navigation.OrderDetailsInfo
import uz.codingtech.messengerdashboard.utils.copy
import uz.codingtech.messengerdashboard.utils.formatDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Orders(
    navController: NavController,
    orderViewModel: OrderViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val activity = context as? Activity

    BackHandler {
        activity?.finish()
    }

    val state by orderViewModel.state.collectAsState()

    val navBackStackEntry = navController.currentBackStackEntry
    val savedStateHandle = navBackStackEntry?.savedStateHandle

    LaunchedEffect(savedStateHandle) {
        savedStateHandle?.getStateFlow<Boolean>("order_updated", false)
            ?.collect { updated ->
                if (updated) {
                    orderViewModel.refreshOrders()
                    savedStateHandle["order_updated"] = false
                }
            }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Orders") },
                actions = {
                    Text(text = "${state.successBalance?.amount} $")
                    IconButton(onClick = {
                        orderViewModel.event(OrdersEvent.Logout)
                        activity?.finish()
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "UserId: ${state.userId}",
                    modifier = Modifier
                        .weight(1f) // bitta qatorda sig‘diradi
                        .padding(end = 8.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyMedium
                )

                IconButton(
                    onClick = {
                        copy(state.userId, context)
                    },
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.FileCopy,
                        contentDescription = "Copy"
                    )
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                itemsIndexed(state.successOrders) { index, order ->

                    if (index == state.successOrders.lastIndex) {
                        LaunchedEffect(Unit) {
                            orderViewModel.event(OrdersEvent.LoadOrders)
                        }
                    }

                    OrderItem(
                        order = order,
                        onClick = {
                            navController.navigate(OrderDetailsInfo(id = order.id))
                        }
                    )
                }

//            item {
//                CircularProgressIndicator(
//                    modifier = Modifier
//                        .padding(16.dp)
//                        .size(20.dp)
//                )
//            }
            }
        }
    }
}

@Composable
fun OrderItem(order: OrderModel, onClick: () -> Unit) {
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
                    text = order.order_name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                StatusChip(
                    text = if (order.completed) "Completed" else "In progress",
                    statusType = if (order.completed) StatusType.COMPLETED else StatusType.IN_PROGRESS
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Created: ${formatDateTime(order.created_at)}",
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "CPM: ${order.spm}")
                Text(text = "Budget: ${order.budget}")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = order.channel_name,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )

                StatusChip(
                    text = if (order.is_active) "Active" else "Inactive",
                    statusType = if (order.is_active) StatusType.ACTIVE else StatusType.INACTIVE
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







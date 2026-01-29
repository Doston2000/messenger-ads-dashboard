package uz.codingtech.messengerdashboard.presentation.post_order.post_orders

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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import uz.codingtech.messengerdashboard.domain.models.ChatOrderModel
import uz.codingtech.messengerdashboard.domain.models.PostOrderModel
import uz.codingtech.messengerdashboard.presentation.chat_order.chat_orders.ChatOrderStatusType
import uz.codingtech.messengerdashboard.presentation.chat_order.chat_orders.ChatOrdersEvent
import uz.codingtech.messengerdashboard.presentation.chat_order.chat_orders.OrderItem
import uz.codingtech.messengerdashboard.presentation.chat_order.chat_orders.StatusChip
import uz.codingtech.messengerdashboard.presentation.common.navigation.AddPostOrder
import uz.codingtech.messengerdashboard.presentation.common.navigation.OrderDetailsInfo
import uz.codingtech.messengerdashboard.presentation.common.navigation.PostOrderDetails
import uz.codingtech.messengerdashboard.utils.formatDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostOrders(
    navController: NavController,
    postOrdersViewModel: PostOrdersViewModel = hiltViewModel()
) {

    val state by postOrdersViewModel.state.collectAsState()

    val navBackStackEntry = navController.currentBackStackEntry
    val savedStateHandle = navBackStackEntry?.savedStateHandle

    LaunchedEffect(savedStateHandle) {
        savedStateHandle?.getStateFlow<Boolean>("order_updated", false)
            ?.collect { updated ->
                if (updated) {
                    postOrdersViewModel.refreshOrders()
                    savedStateHandle["order_updated"] = false
                }
            }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("ChatOrder") },
                actions = {
                    Text(text = "${state.successBalance?.amount} $")
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(AddPostOrder)
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add Post Order")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                itemsIndexed(state.successOrders) { index, order ->

                    if (index == state.successOrders.lastIndex) {
                        LaunchedEffect(Unit) {
                            postOrdersViewModel.event(PostOrdersEvent.LoadOrders)
                        }
                    }

                    PostOrderItem(
                        order = order,
                        onClick = {
                            navController.navigate(PostOrderDetails(id = order.order_id))
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
fun PostOrderItem(order: PostOrderModel, onClick: () -> Unit) {

    var orderStatusStr = if (order.completed) "Completed" else "In progress"
    if (order.cancelled) {
        orderStatusStr = "Canceled"
    }

    var orderStatus = if (order.completed) ChatOrderStatusType.COMPLETED else ChatOrderStatusType.IN_PROGRESS
    if (order.cancelled) {
        orderStatus = ChatOrderStatusType.CANCELED
    }

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
                    text = orderStatusStr,
                    statusType = orderStatus
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
                    text = order.text,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )

                StatusChip(
                    text = if (order.is_active) "Active" else "Inactive",
                    statusType = if (order.is_active) ChatOrderStatusType.ACTIVE else ChatOrderStatusType.INACTIVE
                )
            }
        }
    }
}
package uz.codingtech.messengerdashboard.presentation.dialog_order.chat_order_details

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import uz.codingtech.messengerdashboard.presentation.common.ClicksProgress
import uz.codingtech.messengerdashboard.presentation.common.PlayPauseButton
import uz.codingtech.messengerdashboard.presentation.common.StatusBadge
import uz.codingtech.messengerdashboard.presentation.common.ViewsProgress
import uz.codingtech.messengerdashboard.utils.formatDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatOrderDetails(
    navController: NavController,
    orderId: Int,
    orderViewModel: ChatOrderDetailsViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    var showCancelDialog by remember { mutableStateOf(false) }
    val state by orderViewModel.state.collectAsState()


    if (state.loading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.3f))
                .clickable(enabled = true, onClick = {}),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                strokeWidth = 4.dp,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }

    if (showCancelDialog) {
        AlertDialog(
            onDismissRequest = { showCancelDialog = false },
            icon = {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error
                )
            },
            title = {
                Text("Orderni bekor qilish")
            },
            text = {
                Text("Haqiqatan ham ushbu orderni bekor qilmoqchimisiz?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        orderViewModel.event(ChatOrderDetailEvent.Cancel(orderId))
                        showCancelDialog = false
                    }
                ) {
                    Text(
                        "Roziman",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showCancelDialog = false
                    }
                ) {
                    Text("Bekor qilish")
                }
            }
        )
    }

    LaunchedEffect(Unit) {
        orderViewModel.navController = navController
        orderViewModel.event(ChatOrderDetailEvent.InitOrderDetail(orderId))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Order Details") }
            )
        }
    ) { padding ->

        if (state.success != null) {
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Text(
                    state.success?.order_name ?: "",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    StatusBadge(
                        text = if (state.success!!.completed){
                            "Completed"
                        } else{
                            "In progress"
                        },
                        icon = if (state.success!!.completed)
                            Icons.Default.CheckCircle
                        else
                            Icons.Default.Schedule,
                        color = if (state.success!!.completed)
                            Color(0xFF4CAF50)
                        else
                            MaterialTheme.colorScheme.primary
                    )

                    StatusBadge(
                        text = if (state.success!!.is_active) "Active" else "Paused",
                        icon = if (state.success!!.is_active)
                            Icons.Default.PlayArrow
                        else
                            Icons.Default.Pause,
                        color = if (state.success!!.is_active)
                            Color(0xFF4CAF50)
                        else
                            MaterialTheme.colorScheme.error
                    )
                }

                Divider()

                Card(
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Text("channel name: ${state.success?.channel_name}")
                            Text("channel ID: ${state.success?.channel_id}")
                            Text("created order: ${formatDateTime(state.success?.created_at)}")
                            Text("cpm: ${state.success?.spm}")
                            Text("budget: ${state.success?.budget}")

                            if (state.success!!.tags.isNotEmpty()) {
                                Divider(modifier = Modifier.padding(vertical = 6.dp))

                                Text(
                                    text = "tags",
                                    style = MaterialTheme.typography.labelLarge,
                                    fontWeight = FontWeight.SemiBold
                                )

                                FlowRow(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    state.success!!.tags.forEach { tag ->
                                        AssistChip(
                                            onClick = { /* optional */ },
                                            label = { Text(tag) },
                                            leadingIcon = {
                                                Icon(
                                                    imageVector = Icons.Default.Tag,
                                                    contentDescription = null,
                                                    modifier = Modifier.size(16.dp)
                                                )
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                ViewsProgress(
                    viewed = state.success!!.shown_views,
                    total = state.success!!.total_views
                )

                ClicksProgress(
                    views = state.success!!.shown_views,
                    clicks = state.success!!.clicks
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    OutlinedButton(
                        enabled = !state.success!!.cancelled,
                        onClick = { showCancelDialog = true },
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.error
                        ),
                        border = BorderStroke(
                            1.dp,
                            MaterialTheme.colorScheme.error
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Cancel order"
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Cancel")
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    PlayPauseButton(
                        isActive = state.success?.is_active ?: false,
                        onToggle = {
                            orderViewModel.event(
                                ChatOrderDetailEvent.ChangeActive(
                                    orderId,
                                    !(state.success?.is_active ?: false)
                                )
                            )
                        }
                    )
                }
            }
        }
    }
}

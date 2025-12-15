package uz.codingtech.messengerdashboard.presentation.order_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import uz.codingtech.messengerdashboard.presentation.common.PlayPauseButton
import uz.codingtech.messengerdashboard.presentation.common.StatusBadge
import uz.codingtech.messengerdashboard.presentation.common.ViewsProgress
import uz.codingtech.messengerdashboard.presentation.home.Order

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderDetails(channelId: Long) {

    var order by remember {
        mutableStateOf(
            Order(
                title = "Order #$channelId",
                channelId = channelId,
                channelUsername = "KunUz",
                cpm = "$2.5",
                budget = "$100",
                isCompleted = false,
                isActive = true,
                viewCount = 40000,
                viewedCount = 15000,
                createdDate = "2025-01-01"
            )
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Order Details") }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text(
                order.title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                StatusBadge(
                    text = if (order.isCompleted) "Completed" else "In progress",
                    icon = if (order.isCompleted)
                        Icons.Default.CheckCircle
                    else
                        Icons.Default.Schedule,
                    color = if (order.isCompleted)
                        Color(0xFF4CAF50)
                    else
                        MaterialTheme.colorScheme.primary
                )

                StatusBadge(
                    text = if (order.isActive) "Active" else "Paused",
                    icon = if (order.isActive)
                        Icons.Default.PlayArrow
                    else
                        Icons.Default.Pause,
                    color = if (order.isActive)
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
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Channel: ${order.channelUsername}")
                    Text("Channel ID: ${order.channelId}")
                    Text("Created: ${order.createdDate}")
                    Text("CPM: ${order.cpm}")
                    Text("Budget: ${order.budget}")
                }
            }

            ViewsProgress(
                viewed = order.viewedCount,
                total = order.viewCount
            )

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                PlayPauseButton(
                    isActive = order.isActive,
                    onToggle = {
                        order = order.copy(isActive = !order.isActive)
                    }
                )
            }
        }
    }
}

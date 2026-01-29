package uz.codingtech.messengerdashboard.presentation.chat_order.add_chat_order

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import uz.codingtech.messengerdashboard.domain.models.PostOrderChatModel
import uz.codingtech.messengerdashboard.utils.calculateViews

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddChatOrder(
    navController: NavController,
    addOrderViewModel: AddChatOrderViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        addOrderViewModel.navController = navController
    }

    val context = LocalContext.current

    val state by addOrderViewModel.state.collectAsState()

    if (state.success != "") {
        navController.popBackStack()
    }

    if (state.errorMessage != null) {
        Toast.makeText(context, "${state.errorMessage?.name}", Toast.LENGTH_SHORT).show()
    }

    var title by remember { mutableStateOf("") }
    var channelUsername by remember { mutableStateOf("") }
    var channelId by remember { mutableStateOf("") }
    var cpm by remember { mutableStateOf("") }
    var budget by remember { mutableStateOf("") }
    var maxViewsPerUser by remember { mutableStateOf(1) }
    var tagStr by remember { mutableStateOf("") }
    var isActive by remember { mutableStateOf(true) }

    val totalViews = remember(cpm, budget) {
        calculateViews(cpm, budget)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("New Order") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Default.ArrowBack, null)
                    }
                }
            )
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {

                Text(
                    "Order information",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Order title example: Order 1") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = channelUsername,
                    onValueChange = { channelUsername = it },
                    label = { Text("Channel username example: vipadsuz") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = channelId,
                    onValueChange = { channelId = it },
                    label = { Text("Channel ID example: 1399936418") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                Divider()

                OutlinedTextField(
                    value = cpm,
                    onValueChange = { cpm = it },
                    label = { Text("CPM ($) example: 2.5") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = budget,
                    onValueChange = { budget = it },
                    label = { Text("Budget ($) example: 10") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = maxViewsPerUser.toString(),
                    onValueChange = {
                        maxViewsPerUser = try {
                            it.toInt()
                        } catch (e: Exception) {
                            1
                        }
                    },
                    label = { Text("Max Views Per User example: 5") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = tagStr,
                    onValueChange = { tagStr = it },
                    label = { Text("Tags example: news, world news, news24") },
                    modifier = Modifier.fillMaxWidth()
                )

                // 🔹 Auto calculated views (READ ONLY)
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "Estimated views",
                            style = MaterialTheme.typography.labelMedium
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "%,d views".format(totalViews),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                Divider()

                // 🔹 Active switch
                Card(
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .padding(14.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Order status")
                            Text(
                                if (isActive) "Active" else "Paused",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Switch(
                            checked = isActive,
                            onCheckedChange = { isActive = it }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    onClick = {
                        val list = tagStr.split(",")
                        val tags = ArrayList<String>()
                        list.forEach {
                            tags.add(it.trim())
                        }
                        val postOrder = PostOrderChatModel(
                            order_name = title,
                            channel_name = channelUsername,
                            channel_id = channelId,
                            spm = cpm,
                            budget = budget,
                            tags = tags,
                            max_views_per_user = maxViewsPerUser,
                            is_active = isActive
                        )
                        addOrderViewModel.event(AddChatOrderEvent.PostOrder(postOrder))
                    }
                ) {
                    Icon(Icons.Default.Check, null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Create order")
                }
            }

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
        }
    }
}
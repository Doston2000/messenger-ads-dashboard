package uz.codingtech.messengerdashboard.presentation.post_order.add_post_order

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Upload
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import uz.codingtech.messengerdashboard.domain.models.PostOrderPostModel
import uz.codingtech.messengerdashboard.utils.calculateViews
import uz.codingtech.messengerdashboard.utils.uriToFile
import kotlin.String

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPostOrder(
    navController: NavController,
    addOrderViewModel: AddPostOrderViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        addOrderViewModel.navController = navController
    }

    val context = LocalContext.current

    val state by addOrderViewModel.state.collectAsState()

    if (state.success != null) {
        navController.popBackStack()
    }

    if (state.errorMessage != null) {
        Toast.makeText(context, "${state.errorMessage?.name}", Toast.LENGTH_SHORT).show()
    }

    var order_name by remember { mutableStateOf("") }
    var text by remember { mutableStateOf("") }
    var link by remember { mutableStateOf("") }
    var channelsStr by remember { mutableStateOf("") }
    var cpm by remember { mutableStateOf("") }
    var budget by remember { mutableStateOf("") }
    var maxViewsPerUser by remember { mutableStateOf(1) }
    var isActive by remember { mutableStateOf(true) }
    var selectedUri by remember { mutableStateOf<Uri?>(null) }
    var mediaError by remember { mutableStateOf<String?>(null) }
    var isVideo by remember { mutableStateOf(false) }

    val totalViews = remember(cpm, budget) {
        calculateViews(cpm, budget)
    }

    val VIDEO_SIZE_LIMIT = 10
    val PHOTO_SIZE_LIMIT = 2

    if (state.successUploadMedia != null) {
        selectedUri
    }

    if (state.errorUploadMedia != null) {
        mediaError = "There was an error loading content, please try again."
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            selectedUri = it

            val sizeInMb = getFileSizeInMb(context, it)
            val type = context.contentResolver.getType(it)

            isVideo = type?.startsWith("video") == true

            mediaError = when {
                isVideo && sizeInMb > VIDEO_SIZE_LIMIT -> "Video size should not exceed $VIDEO_SIZE_LIMIT MB"
                !isVideo && sizeInMb > PHOTO_SIZE_LIMIT -> "Photo size should not exceed $PHOTO_SIZE_LIMIT MB"
                else -> {
                    val file = uriToFile(context, it)
                    addOrderViewModel.event(AddPostOrderEvent.UploadMedia(file))
                    null
                }
            }
        }
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
                    "Media (photo or video)",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .clickable {
                            launcher.launch("image/* video/*")
                        },
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(
                        1.dp,
                        if (mediaError != null)
                            MaterialTheme.colorScheme.error
                        else
                            MaterialTheme.colorScheme.outline
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {

                        if (selectedUri == null) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(Icons.Default.Upload, null, modifier = Modifier.size(40.dp))
                                Text("Pick photo or video")
                                Text(
                                    "Photo: max $PHOTO_SIZE_LIMIT MB, Video: max $VIDEO_SIZE_LIMIT MB",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        } else {
                            if (isVideo) {
                                Icon(
                                    Icons.Default.PlayCircle,
                                    null,
                                    modifier = Modifier.size(60.dp)
                                )
                                Text("Picked Video")
                            } else {
                                AsyncImage(
                                    model = selectedUri,
                                    contentDescription = null,
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }
                }

                if (mediaError != null) {
                    Text(
                        text = mediaError!!,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                } else if (state.successUploadMedia != null) {
                    Text(
                        text = "successfully loaded",
                        color = Color.Green,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Text(
                    "Order information",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                OutlinedTextField(
                    value = order_name,
                    onValueChange = { order_name = it },
                    label = { Text("Order title example: Order 1") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    label = { Text("Full text of the advertising post.") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )

                OutlinedTextField(
                    value = link,
                    onValueChange = { link = it },
                    label = { Text("Your link: https://example.com") },
                    modifier = Modifier
                        .fillMaxWidth()
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
                    value = channelsStr,
                    onValueChange = { channelsStr = it },
                    label = { Text("Channels: news, world news, news24") },
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
                        val list = channelsStr.split(",")
                        val postOrder = PostOrderPostModel(
                            order_name = order_name,
                            text = text,
                            link = link,
                            channels = channelsStr,
                            spm = cpm,
                            budget = budget,
                            max_views_per_user = maxViewsPerUser,
                            is_active = isActive,
                            media_id = state.successUploadMedia?.id
                        )
                        addOrderViewModel.event(AddPostOrderEvent.PostOrderPost(postOrder))
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
                        .background(Color.Gray.copy(alpha = 0.3f))
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

fun getFileSizeInMb(context: Context, uri: Uri): Double {
    val cursor = context.contentResolver.query(uri, null, null, null, null)
    val sizeIndex = cursor?.getColumnIndex(OpenableColumns.SIZE) ?: -1
    cursor?.moveToFirst()
    val size = if (sizeIndex != -1) cursor?.getLong(sizeIndex) ?: 0 else 0
    cursor?.close()
    return size / (1024.0 * 1024.0)
}
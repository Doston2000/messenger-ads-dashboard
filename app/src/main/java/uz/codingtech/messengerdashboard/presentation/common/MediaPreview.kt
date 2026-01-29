package uz.codingtech.messengerdashboard.presentation.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import uz.codingtech.messengerdashboard.utils.isImageUrl
import uz.codingtech.messengerdashboard.utils.isVideoUrl

@Composable
fun MediaPreview(link: String, modifier: Modifier = Modifier) {
    val trimmed = link.trim()
    if (trimmed.isBlank()) return

    when {
        trimmed.isVideoUrl() -> {
            VideoPlayer(
                url = link,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .clip(RoundedCornerShape(14.dp))
            )
        }

        trimmed.isImageUrl() -> {
            AsyncImage(
                model = trimmed,
                contentDescription = "Order media",
                modifier = modifier
                    .fillMaxWidth()
                    .heightIn(min = 160.dp, max = 320.dp)
                    .clip(RoundedCornerShape(14.dp)),
                contentScale = ContentScale.Crop
            )
        }

        else -> {
            // Agar extension yo'q bo'lsa ham linkni ko'rsatib qo'yamiz
            Text("link: $trimmed")
        }
    }
}

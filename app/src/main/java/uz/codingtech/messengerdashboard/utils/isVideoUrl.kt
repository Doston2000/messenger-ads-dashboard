package uz.codingtech.messengerdashboard.utils

fun String.isVideoUrl(): Boolean {
    val url = lowercase()
    return url.endsWith(".mp4") || url.endsWith(".mkv") || url.endsWith(".webm") || url.endsWith(".m3u8")
}

fun String.isImageUrl(): Boolean {
    val url = lowercase()
    return url.endsWith(".jpg") || url.endsWith(".jpeg") || url.endsWith(".png") || url.endsWith(".webp") || url.endsWith(".gif")
}
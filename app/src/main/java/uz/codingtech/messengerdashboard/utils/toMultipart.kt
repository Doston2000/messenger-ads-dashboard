package uz.codingtech.messengerdashboard.utils

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import androidx.core.net.toUri
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

fun File.toMultipart(context: Context): MultipartBody.Part {
    val uri = this.toUri()
    val mimeType = getMimeType(context, uri)

    val requestBody = this.asRequestBody(mimeType.toMediaType())

    return MultipartBody.Part.createFormData(
        name = "file",
        filename = this.name,
        body = requestBody
    )
}

fun getMimeType(context: Context, uri: Uri): String {
    val resolver = context.contentResolver
    var type = resolver.getType(uri)

    if (type == null) {
        val extension = MimeTypeMap.getFileExtensionFromUrl(uri.toString())
        type = MimeTypeMap.getSingleton()
            .getMimeTypeFromExtension(extension.lowercase())
    }

    return type ?: "application/octet-stream"
}
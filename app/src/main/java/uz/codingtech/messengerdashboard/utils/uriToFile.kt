package uz.codingtech.messengerdashboard.utils

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import java.io.File
import java.io.FileOutputStream

fun uriToFile(context: Context, uri: Uri): File {
    val mimeType = getMimeType(context, uri)

    val extension = MimeTypeMap.getSingleton()
        .getExtensionFromMimeType(mimeType) ?: "jpg"

    val inputStream = context.contentResolver.openInputStream(uri)!!
    val file = File(context.cacheDir, "upload_${System.currentTimeMillis()}.$extension")
    val outputStream = FileOutputStream(file)

    inputStream.copyTo(outputStream)
    inputStream.close()
    outputStream.close()

    return file
}
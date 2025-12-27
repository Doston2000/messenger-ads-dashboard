package uz.codingtech.messengerdashboard.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

fun copy(str: String, context: Context){
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("copied_text", str)
    clipboard.setPrimaryClip(clip)
}
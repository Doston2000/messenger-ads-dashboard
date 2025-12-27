package uz.codingtech.messengerdashboard.utils

import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun formatDateTime(isoDate: String?): String {
    if (isoDate == null || isoDate.isEmpty()) {
        return ""
    }
    val dateTime = OffsetDateTime.parse(isoDate)

    val formatter = DateTimeFormatter.ofPattern(
        "yyyy-MM-dd HH:mm"
    )

    return dateTime.format(formatter)
}

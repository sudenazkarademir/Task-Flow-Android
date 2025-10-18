package tr.edu.bilimankara20307006.taskflow.data.model

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

/**
 * Yorum Data Modeli
 */
data class Comment(
    val id: String = UUID.randomUUID().toString(),
    val text: String,
    val author: User,
    val createdDate: Date = Date()
) {
    /**
     * Yorum tarihi formatlanmış
     */
    val formattedDate: String
        get() {
            val formatter = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
            return formatter.format(createdDate)
        }
}

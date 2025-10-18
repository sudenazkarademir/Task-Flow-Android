package tr.edu.bilimankara20307006.taskflow.data.model

/**
 * Proje Analizi Data Modeli
 */
data class ProjectAnalytics(
    val projectId: String,
    val taskCompletionRate: Int, // Yüzde
    val completionRateChange: Int, // Son 30 günde değişim (pozitif veya negatif)
    val completedTasks: Int,
    val inProgressTasks: Int,
    val pendingTasks: Int,
    val projectTimelineDays: Int,
    val timelineChange: Int, // Negatif = gecikme
    val weeklyData: List<WeekData>
) {
    companion object {
        /**
         * Örnek analytics verisi
         */
        fun sampleAnalytics(projectId: String): ProjectAnalytics {
            return ProjectAnalytics(
                projectId = projectId,
                taskCompletionRate = 85,
                completionRateChange = 10, // +10%
                completedTasks = 12,
                inProgressTasks = 5,
                pendingTasks = 3,
                projectTimelineDays = 45,
                timelineChange = -3, // 3 gün gecikme
                weeklyData = listOf(
                    WeekData(1, 65),
                    WeekData(2, 72),
                    WeekData(3, 80),
                    WeekData(4, 85)
                )
            )
        }
    }
}

/**
 * Haftalık veri
 */
data class WeekData(
    val week: Int,
    val value: Int
)

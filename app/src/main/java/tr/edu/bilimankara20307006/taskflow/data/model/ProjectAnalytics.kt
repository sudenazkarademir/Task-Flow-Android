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
                projectTimelineDays = 120,
                timelineChange = -15, // -15%
                weeklyData = listOf(
                    WeekData(week = 1, value = 45f),
                    WeekData(week = 2, value = 35f),
                    WeekData(week = 3, value = 28f),
                    WeekData(week = 4, value = 55f),
                    WeekData(week = 5, value = 30f)
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
    val value: Float
)

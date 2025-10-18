package tr.edu.bilimankara20307006.taskflow.data.model

import java.util.Date
import java.util.UUID

/**
 * Proje Durumu
 */
enum class ProjectStatus {
    TODO,        // Yapılacaklar
    IN_PROGRESS, // Devam Ediyor
    COMPLETED    // Tamamlandı
}

/**
 * Proje Data Modeli - iOS Project.swift ile aynı yapı
 */
data class Project(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String,
    val iconName: String = "folder",
    val iconColor: String = "blue",
    val createdDate: Date = Date(),
    val isCompleted: Boolean = false,
    val status: ProjectStatus = ProjectStatus.TODO,
    val tasksCount: Int = 0,
    val completedTasksCount: Int = 0
) {
    /**
     * Proje ilerleme yüzdesi
     */
    val progressPercentage: Double
        get() = if (tasksCount > 0) {
            completedTasksCount.toDouble() / tasksCount.toDouble()
        } else {
            0.0
        }
    
    companion object {
        /**
         * Örnek projeler - iOS'taki sampleProjects ile aynı
         */
        val sampleProjects = listOf(
            Project(
                title = "Proje Yönetimi Uygulaması",
                description = "Proje yönetimi uygulamasının tasarımı ve geliştirme süreci",
                iconName = "phone_android",
                iconColor = "mint",
                tasksCount = 15,
                completedTasksCount = 8
            ),
            Project(
                title = "E-ticaret Uygulaması",
                description = "E-ticaret uygulamasının tasarımı",
                iconName = "shopping_cart",
                iconColor = "orange",
                tasksCount = 12,
                completedTasksCount = 5
            ),
            Project(
                title = "Sosyal Medya Uygulaması",
                description = "Sosyal medya uygulamasının tasarımı",
                iconName = "forum",
                iconColor = "blue",
                tasksCount = 8,
                completedTasksCount = 3
            )
        )
    }
}

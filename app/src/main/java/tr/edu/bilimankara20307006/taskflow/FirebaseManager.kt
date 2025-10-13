package tr.edu.bilimankara20307006.taskflow

/**
 * FirebaseManager - iOS'taki FirebaseManager.swift'in karşılığı
 * Firebase işlemlerini yöneten singleton sınıf
 */
class FirebaseManager private constructor() {
    
    companion object {
        val shared = FirebaseManager()
    }
    
    fun configure() {
        // Firebase configuration will be added here once Firebase is installed
        println("Firebase configuration placeholder - Install Firebase SDK first")
    }
}
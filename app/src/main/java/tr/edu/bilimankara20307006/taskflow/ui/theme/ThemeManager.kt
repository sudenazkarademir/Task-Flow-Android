package tr.edu.bilimankara20307006.taskflow.ui.theme

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

/**
 * Tema Yöneticisi - iOS ThemeManager ile birebir aynı
 * Açık/Koyu tema kontrolü
 */
class ThemeManager(context: Context) : ViewModel() {
    private val prefs = context.getSharedPreferences("theme_prefs", Context.MODE_PRIVATE)
    
    var isDarkMode by mutableStateOf(prefs.getBoolean("isDarkMode", true))
        private set
    
    fun toggleTheme() {
        isDarkMode = !isDarkMode
        prefs.edit().putBoolean("isDarkMode", isDarkMode).apply()
    }
    
    companion object {
        @Volatile
        private var instance: ThemeManager? = null
        
        fun getInstance(context: Context): ThemeManager {
            return instance ?: synchronized(this) {
                instance ?: ThemeManager(context.applicationContext).also { instance = it }
            }
        }
    }
}

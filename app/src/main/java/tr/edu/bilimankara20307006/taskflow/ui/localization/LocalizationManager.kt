package tr.edu.bilimankara20307006.taskflow.ui.localization

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.util.Locale

/**
 * Lokalizasyon Yöneticisi - iOS LocalizationManager ile birebir aynı
 * Türkçe ve İngilizce dil desteği
 */
class LocalizationManager(context: Context) : ViewModel() {
    private val prefs = context.getSharedPreferences("locale_prefs", Context.MODE_PRIVATE)
    
    var currentLocale by mutableStateOf(
        prefs.getString("app_locale", "tr") ?: "tr"
    )
        private set
    
    fun setLocale(locale: String) {
        currentLocale = locale
        prefs.edit().putString("app_locale", locale).apply()
    }
    
    fun localizedString(key: String): String {
        return when (currentLocale) {
            "tr" -> getTurkishString(key)
            "en" -> getEnglishString(key)
            else -> getTurkishString(key)
        }
    }
    
    private fun getTurkishString(key: String): String {
        return when (key) {
            // Genel
            "Projects" -> "Projeler"
            "Notifications" -> "Bildirimler"
            "Settings" -> "Ayarlar"
            "Profile" -> "Profil"
            "SignOut" -> "Çıkış Yap"
            "Save" -> "Kaydet"
            "Cancel" -> "İptal"
            "Delete" -> "Sil"
            "Edit" -> "Düzenle"
            
            // Ayarlar
            "AppSettings" -> "Uygulama Ayarları"
            "DarkMode" -> "Koyu Tema"
            "Language" -> "Dil Ayarları"
            "Turkish" -> "Türkçe"
            "English" -> "İngilizce"
            "Help" -> "Yardım"
            "About" -> "Hakkında"
            "ProfileInformation" -> "Profil Bilgileri"
            
            // Bildirimler
            "NoNotificationsMessage" -> "Henüz bildiriminiz bulunmuyor."
            
            // Proje
            "MyProjects" -> "Projelerim"
            "ProjectDetails" -> "Proje Detayları"
            "NewProject" -> "Yeni Proje"
            
            // Filtreler
            "Sort" -> "Sırala"
            "Filter" -> "Filtrele"
            "FilterOptionAll" -> "Tümü"
            "FilterOptionActive" -> "Aktif"
            "FilterOptionCompleted" -> "Tamamlanan"
            "SortOptionDate" -> "Tarih"
            "SortOptionName" -> "İsim"
            "SortOptionProgress" -> "İlerleme"
            
            else -> key
        }
    }
    
    private fun getEnglishString(key: String): String {
        return when (key) {
            // General
            "Projects" -> "Projects"
            "Notifications" -> "Notifications"
            "Settings" -> "Settings"
            "Profile" -> "Profile"
            "SignOut" -> "Sign Out"
            "Save" -> "Save"
            "Cancel" -> "Cancel"
            "Delete" -> "Delete"
            "Edit" -> "Edit"
            
            // Settings
            "AppSettings" -> "App Settings"
            "DarkMode" -> "Dark Mode"
            "Language" -> "Language"
            "Turkish" -> "Turkish"
            "English" -> "English"
            "Help" -> "Help"
            "About" -> "About"
            "ProfileInformation" -> "Profile Information"
            
            // Notifications
            "NoNotificationsMessage" -> "You have no notifications yet."
            
            // Project
            "MyProjects" -> "My Projects"
            "ProjectDetails" -> "Project Details"
            "NewProject" -> "New Project"
            
            // Filters
            "Sort" -> "Sort"
            "Filter" -> "Filter"
            "FilterOptionAll" -> "All"
            "FilterOptionActive" -> "Active"
            "FilterOptionCompleted" -> "Completed"
            "SortOptionDate" -> "Date"
            "SortOptionName" -> "Name"
            "SortOptionProgress" -> "Progress"
            
            else -> key
        }
    }
    
    companion object {
        @Volatile
        private var instance: LocalizationManager? = null
        
        fun getInstance(context: Context): LocalizationManager {
            return instance ?: synchronized(this) {
                instance ?: LocalizationManager(context.applicationContext).also { instance = it }
            }
        }
    }
}

package tr.edu.bilimankara20307006.taskflow.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import tr.edu.bilimankara20307006.taskflow.ui.auth.AuthViewModel
import tr.edu.bilimankara20307006.taskflow.ui.project.ProjectListScreen
import tr.edu.bilimankara20307006.taskflow.ui.theme.ThemeManager
import tr.edu.bilimankara20307006.taskflow.ui.localization.LocalizationManager

/**
 * Ana Tab Ekranı - iOS CustomTabView ile birebir uyumlu
 * Projeler, Bildirimler ve Ayarlar sekmelerini içerir
 */
@Composable
fun MainTabScreen(
    authViewModel: AuthViewModel = viewModel(),
    onNavigateToLogin: () -> Unit
) {
    var selectedTab by remember { mutableStateOf(0) }
    
    val darkBackground = Color(0xFF1C1C1E)
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(darkBackground)
    ) {
        // Main content based on selected tab
        when (selectedTab) {
            0 -> ProjectListScreen()
            1 -> NotificationsScreen()
            2 -> SettingsScreen(
                authViewModel = authViewModel,
                onNavigateToLogin = onNavigateToLogin
            )
        }
        
        // Custom Bottom Navigation Bar - iOS style
        CustomBottomNavigationBar(
            selectedTab = selectedTab,
            onTabSelected = { selectedTab = it },
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

/**
 * Custom Bottom Navigation Bar - iOS tab bar stili
 */
@Composable
fun CustomBottomNavigationBar(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val tabBackground = Color(0xFF2C2C2E)
    val selectedColor = Color(0xFF007AFF)
    val unselectedColor = Color.Gray
    
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = tabBackground,
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Projeler tab
            TabBarItem(
                icon = Icons.Default.AssignmentTurnedIn,
                title = "Projeler",
                isSelected = selectedTab == 0,
                selectedColor = selectedColor,
                unselectedColor = unselectedColor,
                onClick = { onTabSelected(0) }
            )
            
            // Bildirimler tab
            TabBarItem(
                icon = Icons.Default.Notifications,
                title = "Bildirimler",
                isSelected = selectedTab == 1,
                selectedColor = selectedColor,
                unselectedColor = unselectedColor,
                onClick = { onTabSelected(1) }
            )
            
            // Ayarlar tab
            TabBarItem(
                icon = Icons.Default.Settings,
                title = "Ayarlar",
                isSelected = selectedTab == 2,
                selectedColor = selectedColor,
                unselectedColor = unselectedColor,
                onClick = { onTabSelected(2) }
            )
        }
    }
}

/**
 * Tab Bar Item - iOS tarzı tab butonu
 */
@Composable
fun TabBarItem(
    icon: ImageVector,
    title: String,
    isSelected: Boolean,
    selectedColor: Color,
    unselectedColor: Color,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier
            .padding(8.dp)
            .clickableWithoutRipple(onClick = onClick)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = if (isSelected) selectedColor else unselectedColor,
            modifier = Modifier.size(24.dp)
        )
        
        Text(
            text = title,
            fontSize = 10.sp,
            color = if (isSelected) selectedColor else unselectedColor
        )
    }
}

/**
 * Ripple effect olmadan clickable modifier
 */
@Composable
fun Modifier.clickableWithoutRipple(onClick: () -> Unit): Modifier {
    val interactionSource = remember { MutableInteractionSource() }
    return this.clickable(
        indication = null,
        interactionSource = interactionSource,
        onClick = onClick
    )
}

/**
 * Bildirimler Ekranı - iOS NotificationsView ile aynı
 */
@Composable
fun NotificationsScreen() {
    val darkBackground = Color(0xFF1C1C1E)
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(darkBackground),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                Icons.Default.Notifications,
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier.size(60.dp)
            )
            
            Text(
                text = "Bildirimler",
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
            
            Text(
                text = "Henüz bildiriminiz bulunmuyor.",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}

/**
 * Ayarlar Ekranı - iOS SettingsView ile aynı
 */
@Composable
fun SettingsScreen(
    authViewModel: AuthViewModel,
    onNavigateToLogin: () -> Unit
) {
    val context = LocalContext.current
    val authState by authViewModel.authState.collectAsState()
    val themeManager = remember { ThemeManager.getInstance(context) }
    val localizationManager = remember { LocalizationManager.getInstance(context) }
    var isDarkMode by remember { mutableStateOf(themeManager.isDarkMode) }
    var currentLanguage by remember { mutableStateOf(localizationManager.currentLocale) }
    
    val darkBackground = Color(0xFF1C1C1E)
    val cardBackground = Color(0xFF2C2C2E)
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(darkBackground)
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = localizationManager.localizedString("Settings"),
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // User Profile Section
        authState.user?.let { user ->
            Column(
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {
                Text(
                    text = localizationManager.localizedString("ProfileInformation"),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = cardBackground,
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // User avatar
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .background(
                                    Color(0xFF007AFF).copy(alpha = 0.2f),
                                    androidx.compose.foundation.shape.CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = (user.displayName?.firstOrNull()?.uppercase() ?: "U"),
                                fontSize = 22.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF007AFF)
                            )
                        }
                        
                        Column(
                            verticalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            Text(
                                text = user.displayName ?: "Kullanıcı",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            )
                            
                            Text(
                                text = user.email ?: "",
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        }
                        
                        Spacer(modifier = Modifier.weight(1f))
                        
                        Icon(
                            Icons.Default.ChevronRight,
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Settings Options
        Column(
            modifier = Modifier.padding(horizontal = 20.dp)
        ) {
            Text(
                text = localizationManager.localizedString("AppSettings"),
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = cardBackground,
                shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
            ) {
                Column {
                    SettingsRow(
                        icon = Icons.Default.Notifications,
                        title = localizationManager.localizedString("Notifications"),
                        color = Color(0xFFFF9500)
                    )
                    Divider(color = Color(0xFF3A3A3C), thickness = 0.5.dp)
                    
                    // Koyu Tema Toggle - iOS gibi
                    SettingsToggleRow(
                        icon = Icons.Default.DarkMode,
                        title = localizationManager.localizedString("DarkMode"),
                        color = Color(0xFFAF52DE),
                        isOn = isDarkMode,
                        onToggle = { 
                            isDarkMode = it
                            themeManager.toggleTheme()
                        }
                    )
                    Divider(color = Color(0xFF3A3A3C), thickness = 0.5.dp)
                    
                    // Dil Seçici - iOS gibi
                    LanguagePickerRow(
                        icon = Icons.Default.Language,
                        title = localizationManager.localizedString("Language"),
                        color = Color(0xFF007AFF),
                        currentLanguage = currentLanguage,
                        onLanguageChange = { lang ->
                            currentLanguage = lang
                            localizationManager.setLocale(lang)
                        }
                    )
                    Divider(color = Color(0xFF3A3A3C), thickness = 0.5.dp)
                    SettingsRow(
                        icon = Icons.Default.Help,
                        title = localizationManager.localizedString("Help"),
                        color = Color(0xFF34C759)
                    )
                    Divider(color = Color(0xFF3A3A3C), thickness = 0.5.dp)
                    SettingsRow(
                        icon = Icons.Default.Info,
                        title = localizationManager.localizedString("About"),
                        color = Color.Gray
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(20.dp))
        
        // Sign out section
        Button(
            onClick = {
                authViewModel.signOut()
                onNavigateToLogin()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = cardBackground
            ),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.ExitToApp,
                    contentDescription = null,
                    tint = Color.Red,
                    modifier = Modifier.size(24.dp)
                )
                
                Text(
                    text = localizationManager.localizedString("SignOut"),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Red
                )
            }
        }
        
        Spacer(modifier = Modifier.height(90.dp)) // Space for bottom nav
    }
}

/**
 * Settings Row Item
 */
@Composable
fun SettingsRow(
    icon: ImageVector,
    title: String,
    color: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickableWithoutRipple { /* Action */ }
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = color,
            modifier = Modifier.size(24.dp)
        )
        
        Text(
            text = title,
            fontSize = 16.sp,
            color = Color.White,
            modifier = Modifier.weight(1f)
        )
        
        Icon(
            Icons.Default.ChevronRight,
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier.size(20.dp)
        )
    }
}

/**
 * Settings Toggle Row - iOS gibi tema switcher için
 */
@Composable
fun SettingsToggleRow(
    icon: ImageVector,
    title: String,
    color: Color,
    isOn: Boolean,
    onToggle: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = color,
            modifier = Modifier.size(24.dp)
        )
        
        Text(
            text = title,
            fontSize = 16.sp,
            color = Color.White,
            modifier = Modifier.weight(1f)
        )
        
        Switch(
            checked = isOn,
            onCheckedChange = onToggle,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = Color(0xFFAF52DE),
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = Color.Gray
            )
        )
    }
}

/**
 * Language Picker Row - iOS gibi dil seçici
 */
@Composable
fun LanguagePickerRow(
    icon: ImageVector,
    title: String,
    color: Color,
    currentLanguage: String,
    onLanguageChange: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = color,
            modifier = Modifier.size(24.dp)
        )
        
        Text(
            text = title,
            fontSize = 16.sp,
            color = Color.White
        )
        
        Spacer(modifier = Modifier.weight(1f))
        
        // Language Toggle Button
        Row(
            modifier = Modifier
                .background(
                    Color(0xFF3A3A3C),
                    androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
                ),
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            // Turkish Button
            TextButton(
                onClick = { onLanguageChange("tr") },
                colors = ButtonDefaults.textButtonColors(
                    containerColor = if (currentLanguage == "tr") 
                        Color(0xFF007AFF) else Color.Transparent,
                    contentColor = Color.White
                ),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(6.dp),
                modifier = Modifier.height(36.dp)
            ) {
                Text(
                    text = "TR",
                    fontSize = 14.sp,
                    fontWeight = if (currentLanguage == "tr") 
                        FontWeight.SemiBold else FontWeight.Normal
                )
            }
            
            // English Button
            TextButton(
                onClick = { onLanguageChange("en") },
                colors = ButtonDefaults.textButtonColors(
                    containerColor = if (currentLanguage == "en") 
                        Color(0xFF007AFF) else Color.Transparent,
                    contentColor = Color.White
                ),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(6.dp),
                modifier = Modifier.height(36.dp)
            ) {
                Text(
                    text = "EN",
                    fontSize = 14.sp,
                    fontWeight = if (currentLanguage == "en") 
                        FontWeight.SemiBold else FontWeight.Normal
                )
            }
        }
    }
}

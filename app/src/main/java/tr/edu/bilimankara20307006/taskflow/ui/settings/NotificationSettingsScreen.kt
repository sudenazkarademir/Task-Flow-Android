package tr.edu.bilimankara20307006.taskflow.ui.settings

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import tr.edu.bilimankara20307006.taskflow.ui.localization.LocalizationManager

/**
 * Bildirim Ayarları Ekranı
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationSettingsScreen(
    onBackClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val localizationManager = remember { LocalizationManager.getInstance(context) }
    
    // Back button handling
    BackHandler(enabled = true) {
        onBackClick()
    }
    
    // Animation states
    var headerVisible by remember { mutableStateOf(false) }
    var contentVisible by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        delay(100)
        headerVisible = true
        delay(150)
        contentVisible = true
    }
    
    val headerAlpha by animateFloatAsState(
        targetValue = if (headerVisible) 1f else 0f,
        animationSpec = tween(durationMillis = 400, easing = FastOutSlowInEasing),
        label = "headerAlpha"
    )
    
    val contentAlpha by animateFloatAsState(
        targetValue = if (contentVisible) 1f else 0f,
        animationSpec = tween(durationMillis = 400, easing = FastOutSlowInEasing),
        label = "contentAlpha"
    )
    
    // Notification preferences
    var notificationPreference by remember { mutableStateOf("all") } // all, mentions, none
    var notificationSound by remember { mutableStateOf("ring_vibrate") } // ring_vibrate, vibrate_only
    var showDisableDialog by remember { mutableStateOf(false) }
    
    // Theme colors
    val darkBackground = MaterialTheme.colorScheme.background
    val cardBackground = MaterialTheme.colorScheme.surface
    val textColor = MaterialTheme.colorScheme.onBackground
    val textSecondaryColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (localizationManager.currentLocale == "tr")
                            "Bildirim Ayarları" else "Notification Settings",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = textColor,
                        modifier = Modifier.alpha(headerAlpha)
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier.alpha(headerAlpha)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = localizationManager.localizedString("Back"),
                            tint = textColor
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = darkBackground
                )
            )
        },
        containerColor = darkBackground
    ) { paddingValues ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp)
                .alpha(contentAlpha),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            contentPadding = PaddingValues(vertical = 20.dp)
        ) {
            // Notification Preference Section
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = if (localizationManager.currentLocale == "tr")
                            "Bildirim Tercihi" else "Notification Preference",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = textColor
                    )
                    
                    NotificationPreferenceItem(
                        title = if (localizationManager.currentLocale == "tr") 
                            "Tüm Bildirimler" else "All Notifications",
                        description = if (localizationManager.currentLocale == "tr")
                            "Tüm güncellemeler ve mesajlar" else "All updates and messages",
                        isSelected = notificationPreference == "all",
                        onClick = { notificationPreference = "all" }
                    )
                    
                    NotificationPreferenceItem(
                        title = if (localizationManager.currentLocale == "tr")
                            "Sadece Bahsedenler" else "Mentions Only",
                        description = if (localizationManager.currentLocale == "tr")
                            "Sadece sizi etiketleyen mesajlar" else "Only messages that mention you",
                        isSelected = notificationPreference == "mentions",
                        onClick = { notificationPreference = "mentions" }
                    )
                    
                    NotificationPreferenceItem(
                        title = if (localizationManager.currentLocale == "tr")
                            "Bildirimleri Kapat" else "Turn Off Notifications",
                        description = if (localizationManager.currentLocale == "tr")
                            "Hiç bildirim alma" else "No notifications at all",
                        isSelected = notificationPreference == "none",
                        onClick = { 
                            showDisableDialog = true
                        }
                    )
                }
            }
            
            // Sound Settings Section
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = if (localizationManager.currentLocale == "tr")
                            "Ses Ayarları" else "Sound Settings",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = textColor
                    )
                    
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = cardBackground,
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { notificationSound = "ring_vibrate" }
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = if (localizationManager.currentLocale == "tr")
                                        "Ses ve Titreşim" else "Sound & Vibration",
                                    fontSize = 16.sp,
                                    color = textColor
                                )
                                RadioButton(
                                    selected = notificationSound == "ring_vibrate",
                                    onClick = { notificationSound = "ring_vibrate" },
                                    colors = RadioButtonDefaults.colors(
                                        selectedColor = Color(0xFF4CAF50),
                                        unselectedColor = textSecondaryColor
                                    )
                                )
                            }
                            
                            Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                            
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { notificationSound = "vibrate_only" }
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = if (localizationManager.currentLocale == "tr")
                                        "Sadece Titreşim" else "Vibration Only",
                                    fontSize = 16.sp,
                                    color = textColor
                                )
                                RadioButton(
                                    selected = notificationSound == "vibrate_only",
                                    onClick = { notificationSound = "vibrate_only" },
                                    colors = RadioButtonDefaults.colors(
                                        selectedColor = Color(0xFF4CAF50),
                                        unselectedColor = textSecondaryColor
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
    
    // Disable Notifications Dialog
    if (showDisableDialog) {
        AlertDialog(
            onDismissRequest = { showDisableDialog = false },
            title = {
                Text(
                    text = if (localizationManager.currentLocale == "tr")
                        "Bildirimleri Kapat?" else "Turn Off Notifications?",
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    if (localizationManager.currentLocale == "tr")
                        "Hiçbir bildirim almayacaksınız. Önemli güncellemeleri kaçırabilirsiniz."
                    else "You won't receive any notifications. You might miss important updates."
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        notificationPreference = "none"
                        showDisableDialog = false
                    }
                ) {
                    Text(
                        text = if (localizationManager.currentLocale == "tr") "Kapat" else "Turn Off",
                        fontWeight = FontWeight.SemiBold
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDisableDialog = false }
                ) {
                    Text(
                        text = localizationManager.localizedString("Cancel"),
                        fontWeight = FontWeight.SemiBold
                    )
                }
            },
            containerColor = cardBackground,
            shape = RoundedCornerShape(16.dp)
        )
    }
}

@Composable
private fun NotificationPreferenceItem(
    title: String,
    description: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        color = if (isSelected) {
            Color(0xFF4CAF50).copy(alpha = 0.1f)
        } else {
            MaterialTheme.colorScheme.surface
        },
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = description,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
            
            RadioButton(
                selected = isSelected,
                onClick = onClick,
                colors = RadioButtonDefaults.colors(
                    selectedColor = Color(0xFF4CAF50),
                    unselectedColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            )
        }
    }
}

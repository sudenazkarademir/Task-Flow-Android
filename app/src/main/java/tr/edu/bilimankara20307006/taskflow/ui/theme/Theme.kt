package tr.edu.bilimankara20307006.taskflow.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// iOS style light theme colors (for login screen)
private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF1E88E5),
    onPrimary = Color.White,
    secondary = Color(0xFF42A5F5),
    onSecondary = Color.White,
    tertiary = Color(0xFF64B5F6),
    onTertiary = Color.White,
    background = Color(0xFFFFFBFE),
    onBackground = Color(0xFF1C1B1F),
    surface = Color(0xFFFFFBFE),
    onSurface = Color(0xFF1C1B1F),
)

// iOS style dark theme colors (for main app)
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF007AFF), // iOS blue
    onPrimary = Color.White,
    secondary = Color(0xFF5AC8FA), // iOS light blue
    onSecondary = Color.White,
    tertiary = Color(0xFF00C7BE), // iOS mint
    onTertiary = Color.White,
    background = Color(0xFF1C1C1E), // iOS dark background
    onBackground = Color.White,
    surface = Color(0xFF2C2C2E), // iOS card background
    onSurface = Color.White,
    surfaceVariant = Color(0xFF3A3A3C), // iOS search/input background
    onSurfaceVariant = Color.Gray,
    error = Color(0xFFFF3B30), // iOS red
    onError = Color.White,
)

@Composable
fun TaskFlowTheme(
    useDarkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (useDarkTheme) DarkColorScheme else LightColorScheme
    
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
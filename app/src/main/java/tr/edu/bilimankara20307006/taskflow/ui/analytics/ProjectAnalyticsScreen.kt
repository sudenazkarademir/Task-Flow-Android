package tr.edu.bilimankara20307006.taskflow.ui.analytics

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import tr.edu.bilimankara20307006.taskflow.data.model.ProjectAnalytics
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi

/**
 * Proje Analizi Ekranı
 * Ekran görüntüsündeki "Project Analytics" tasarımına birebir uygun
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ProjectAnalyticsScreen(
    onBackClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    // Renk tanımları
    val darkBackground = Color(0xFF1C1C1E)
    val cardBackground = Color(0xFF1E2A3A)
    val selectedTabColor = Color(0xFF0A84FF)
    val unselectedTabColor = Color(0xFF8E8E93)
    
    // Tab'lar
    val tabs = listOf("Overview", "Progress", "Team")
    var selectedTabIndex by remember { mutableStateOf(0) }
    val pagerState = rememberPagerState(pageCount = { tabs.size })
    val coroutineScope = rememberCoroutineScope()
    
    // Analytics verisi
    val analytics = remember { ProjectAnalytics.sampleAnalytics("project-1") }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Project Analytics",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Geri",
                            tint = Color.White
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
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Custom Tab Selector - iOS Style
            CustomTabSelector(
                tabs = tabs,
                selectedTabIndex = selectedTabIndex,
                onTabSelected = { 
                    selectedTabIndex = it
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(it)
                    }
                },
                selectedColor = selectedTabColor,
                unselectedColor = unselectedTabColor,
                modifier = Modifier.padding(20.dp)
            )
            
            // Horizontal Pager
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                when (page) {
                    0 -> OverviewTab(analytics = analytics)
                    1 -> ProgressTab()
                    2 -> TeamTab()
                }
            }
        }
    }
}

/**
 * Custom Tab Selector - iOS benzeri
 */
@Composable
private fun CustomTabSelector(
    tabs: List<String>,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
    selectedColor: Color,
    unselectedColor: Color,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        tabs.forEachIndexed { index, tab ->
            Text(
                text = tab,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { onTabSelected(index) }
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                fontSize = 16.sp,
                fontWeight = if (selectedTabIndex == index) FontWeight.SemiBold else FontWeight.Normal,
                color = if (selectedTabIndex == index) Color.White else unselectedColor
            )
        }
    }
}

/**
 * Overview Tab - Ana istatistikler
 */
@Composable
private fun OverviewTab(
    analytics: ProjectAnalytics,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        // Task Completion Rate Card
        item {
            TaskCompletionRateCard(analytics = analytics)
        }
        
        // Project Timeline Card
        item {
            ProjectTimelineCard(analytics = analytics)
        }
    }
}

/**
 * Progress Tab - Placeholder
 */
@Composable
private fun ProgressTab() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Progress Tab",
            fontSize = 18.sp,
            color = Color.Gray
        )
    }
}

/**
 * Team Tab - Placeholder
 */
@Composable
private fun TeamTab() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Team Tab",
            fontSize = 18.sp,
            color = Color.Gray
        )
    }
}

/**
 * Task Completion Rate Card
 */
@Composable
private fun TaskCompletionRateCard(
    analytics: ProjectAnalytics,
    modifier: Modifier = Modifier
) {
    val cardBackground = Color(0xFF1E2A3A)
    
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = cardBackground
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Task Completion Rate",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
            
            // Circular progress
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Progress stats
                Column {
                    Text(
                        text = "${analytics.taskCompletionRate}%",
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0A84FF)
                    )
                    Text(
                        text = "${analytics.completedTasks}/${analytics.completedTasks + analytics.inProgressTasks + analytics.pendingTasks} tasks completed",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }
            
            // Weekly Progress Chart
            WeeklyProgressChart(analytics = analytics)
        }
    }
}

/**
 * Haftalık ilerleme grafiği
 */
@Composable
private fun WeeklyProgressChart(
    analytics: ProjectAnalytics,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Weekly Progress",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White
        )
        
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            analytics.weeklyData.forEach { data ->
                WeeklyBar(
                    label = "W${data.week}",
                    value = data.value,
                    maxValue = 100,
                    color = Color(0xFF0A84FF),
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

/**
 * Haftalık bar
 */
@Composable
private fun WeeklyBar(
    label: String,
    value: Int,
    maxValue: Int,
    color: Color,
    modifier: Modifier = Modifier
) {
    val heightFraction = if (maxValue > 0) value.toFloat() / maxValue.toFloat() else 0f
    
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.BottomCenter
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(heightFraction.coerceIn(0.2f, 1f))
                    .background(color, RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
            )
        }
        
        // Label
        Text(
            text = label,
            fontSize = 12.sp,
            color = Color.White.copy(alpha = 0.6f),
            maxLines = 1
        )
    }
}

/**
 * Project Timeline Card
 */
@Composable
private fun ProjectTimelineCard(
    analytics: ProjectAnalytics,
    modifier: Modifier = Modifier
) {
    val cardBackground = Color(0xFF1E2A3A)
    
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = cardBackground
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Project Timeline",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TimelineItem(
                    label = "Start Date",
                    value = "Dec 1, 2024"
                )
                
                TimelineItem(
                    label = "Expected End",
                    value = "Jan 31, 2025"
                )
            }
        }
    }
}

/**
 * Timeline Item
 */
@Composable
private fun TimelineItem(
    label: String,
    value: String
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color.Gray
        )
        Text(
            text = value,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White
        )
    }
}

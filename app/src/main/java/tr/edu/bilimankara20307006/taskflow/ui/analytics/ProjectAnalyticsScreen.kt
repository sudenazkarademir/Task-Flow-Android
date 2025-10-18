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
            // Custom Tab Row
            CustomTabRow(
                tabs = tabs,
                selectedTabIndex = pagerState.currentPage,
                onTabSelected = { index ->
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)
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
 * Custom Tab Row - Ekran görüntüsündeki stile uygun
 */
@Composable
private fun CustomTabRow(
    tabs: List<String>,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val selectedColor = Color(0xFF0A84FF)
    val unselectedColor = Color(0xFF8E8E93)
    val tabBackground = Color(0xFF2C2C2E)
    
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(tabBackground, RoundedCornerShape(12.dp))
            .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        tabs.forEachIndexed { index, title ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .background(
                        color = if (selectedTabIndex == index) selectedColor else Color.Transparent,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { onTabSelected(index) }
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = title,
                    fontSize = 15.sp,
                    fontWeight = if (selectedTabIndex == index) FontWeight.SemiBold else FontWeight.Normal,
                    color = if (selectedTabIndex == index) Color.White else unselectedColor
                )
            }
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
            text = "Progress content here",
            color = Color.White,
            fontSize = 16.sp
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
            text = "Team content here",
            color = Color.White,
            fontSize = 16.sp
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
    val changeColor = if (analytics.completionRateChange >= 0) Color(0xFF32D74B) else Color(0xFFFF453A)
    
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = cardBackground
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "Task Completion Rate",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White.copy(alpha = 0.7f)
                )
                
                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "${analytics.taskCompletionRate}%",
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Last 30 Days",
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.5f)
                    )
                    
                    Text(
                        text = "${if (analytics.completionRateChange >= 0) "+" else ""}${analytics.completionRateChange}%",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = changeColor
                    )
                }
            }
            
            // Bar Chart
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                // Completed
                BarChartItem(
                    label = "Completed",
                    value = analytics.completedTasks,
                    maxValue = analytics.completedTasks + analytics.inProgressTasks + analytics.pendingTasks,
                    color = Color(0xFF0A84FF),
                    modifier = Modifier.weight(1f)
                )
                
                // In Progress
                BarChartItem(
                    label = "In Progress",
                    value = analytics.inProgressTasks,
                    maxValue = analytics.completedTasks + analytics.inProgressTasks + analytics.pendingTasks,
                    color = Color(0xFF0A84FF),
                    modifier = Modifier.weight(1f)
                )
                
                // Pending
                BarChartItem(
                    label = "Pending",
                    value = analytics.pendingTasks,
                    maxValue = analytics.completedTasks + analytics.inProgressTasks + analytics.pendingTasks,
                    color = Color(0xFF0A84FF).copy(alpha = 0.3f),
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

/**
 * Bar Chart Item
 */
@Composable
private fun BarChartItem(
    label: String,
    value: Int,
    maxValue: Int,
    color: Color,
    modifier: Modifier = Modifier
) {
    val heightFraction = if (maxValue > 0) (value.toFloat() / maxValue.toFloat()) else 0f
    
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
    val changeColor = if (analytics.timelineChange >= 0) Color(0xFF32D74B) else Color(0xFFFF453A)
    
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = cardBackground
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "Project Timeline",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White.copy(alpha = 0.7f)
                )
                
                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "${analytics.projectTimelineDays} days",
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Current Project",
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.5f)
                    )
                    
                    Text(
                        text = "${analytics.timelineChange}%",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = changeColor
                    )
                }
            }
            
            // Line Chart
            LineChart(
                data = analytics.weeklyData,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
            )
        }
    }
}

/**
 * Line Chart
 */
@Composable
private fun LineChart(
    data: List<tr.edu.bilimankara20307006.taskflow.data.model.WeekData>,
    modifier: Modifier = Modifier
) {
    val lineColor = Color(0xFF0A84FF)
    
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Chart
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            if (data.isEmpty()) return@Canvas
            
            val maxValue = data.maxOf { it.value }
            val minValue = data.minOf { it.value }
            val range = maxValue - minValue
            
            val stepX = size.width / (data.size - 1).coerceAtLeast(1)
            
            // Draw line
            val path = Path()
            data.forEachIndexed { index, weekData ->
                val x = index * stepX
                val y = size.height - ((weekData.value - minValue) / range * size.height)
                
                if (index == 0) {
                    path.moveTo(x, y)
                } else {
                    path.lineTo(x, y)
                }
            }
            
            drawPath(
                path = path,
                color = lineColor,
                style = androidx.compose.ui.graphics.drawscope.Stroke(width = 4f)
            )
        }
        
        // Week labels
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            data.forEach { weekData ->
                Text(
                    text = "Week ${weekData.week}",
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.5f)
                )
            }
        }
    }
}

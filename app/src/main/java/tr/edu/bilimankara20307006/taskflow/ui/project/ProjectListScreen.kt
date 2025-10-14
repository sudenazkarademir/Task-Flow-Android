package tr.edu.bilimankara20307006.taskflow.ui.project

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tr.edu.bilimankara20307006.taskflow.data.model.Project

/**
 * Proje Listeleme Ekranı - iOS ProjectListView ile birebir uyumlu
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectListScreen(
    modifier: Modifier = Modifier
) {
    var searchText by remember { mutableStateOf("") }
    var selectedSortOption by remember { mutableStateOf("Tarih") }
    var selectedFilterOption by remember { mutableStateOf("Tümü") }
    var projects by remember { mutableStateOf(Project.sampleProjects) }
    
    val sortOptions = listOf("Tarih", "İsim", "İlerleme")
    val filterOptions = listOf("Tümü", "Aktif", "Tamamlanan")
    
    // Filtreleme ve sıralama
    val filteredProjects = remember(searchText, selectedSortOption, selectedFilterOption, projects) {
        var filtered = projects
        
        // Arama filtresi
        if (searchText.isNotEmpty()) {
            filtered = filtered.filter { project ->
                project.title.contains(searchText, ignoreCase = true) ||
                project.description.contains(searchText, ignoreCase = true)
            }
        }
        
        // Durum filtresi
        filtered = when (selectedFilterOption) {
            "Aktif" -> filtered.filter { !it.isCompleted }
            "Tamamlanan" -> filtered.filter { it.isCompleted }
            else -> filtered
        }
        
        // Sıralama
        when (selectedSortOption) {
            "İsim" -> filtered.sortedBy { it.title }
            "İlerleme" -> filtered.sortedByDescending { it.progressPercentage }
            else -> filtered.sortedByDescending { it.createdDate }
        }
    }
    
    // iOS dark theme colors
    val darkBackground = Color(0xFF1C1C1E)
    val cardBackground = Color(0xFF2C2C2E)
    val searchBackground = Color(0xFF3A3A3C)
    
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(darkBackground)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header with title and add button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Projeler",
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                
                IconButton(
                    onClick = {
                        // Add new project
                        val newProject = Project(
                            title = "Yeni Proje ${projects.size + 1}",
                            description = "Yeni proje açıklaması",
                            iconName = "folder",
                            iconColor = "green"
                        )
                        projects = projects + newProject
                    },
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color(0xFF007AFF), CircleShape)
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Yeni Proje",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            
            // Search bar
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(top = 8.dp),
                placeholder = {
                    Text(
                        text = "Projelerde ara",
                        color = Color.Gray
                    )
                },
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "Ara",
                        tint = Color.Gray
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = searchBackground,
                    unfocusedContainerColor = searchBackground,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    cursorColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )
            
            // Sort and Filter buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Sort dropdown
                var sortExpanded by remember { mutableStateOf(false) }
                Box {
                    Button(
                        onClick = { sortExpanded = true },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = searchBackground,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Sırala", fontSize = 14.sp)
                        Icon(
                            Icons.Default.ArrowDropDown,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    DropdownMenu(
                        expanded = sortExpanded,
                        onDismissRequest = { sortExpanded = false }
                    ) {
                        sortOptions.forEach { option ->
                            DropdownMenuItem(
                                text = {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(option)
                                        if (selectedSortOption == option) {
                                            Icon(
                                                Icons.Default.Check,
                                                contentDescription = null,
                                                modifier = Modifier.size(20.dp)
                                            )
                                        }
                                    }
                                },
                                onClick = {
                                    selectedSortOption = option
                                    sortExpanded = false
                                }
                            )
                        }
                    }
                }
                
                // Filter dropdown
                var filterExpanded by remember { mutableStateOf(false) }
                Box {
                    Button(
                        onClick = { filterExpanded = true },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = searchBackground,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Filtrele", fontSize = 14.sp)
                        Icon(
                            Icons.Default.ArrowDropDown,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    DropdownMenu(
                        expanded = filterExpanded,
                        onDismissRequest = { filterExpanded = false }
                    ) {
                        filterOptions.forEach { option ->
                            DropdownMenuItem(
                                text = {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(option)
                                        if (selectedFilterOption == option) {
                                            Icon(
                                                Icons.Default.Check,
                                                contentDescription = null,
                                                modifier = Modifier.size(20.dp)
                                            )
                                        }
                                    }
                                },
                                onClick = {
                                    selectedFilterOption = option
                                    filterExpanded = false
                                }
                            )
                        }
                    }
                }
            }
            
            // Projects section header
            Text(
                text = "Projelerim",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
            )
            
            // Projects list
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 90.dp) // Space for bottom nav
            ) {
                items(filteredProjects) { project ->
                    ProjectCardView(project = project)
                }
            }
        }
    }
}

/**
 * Proje Kartı - iOS ProjectCardView ile aynı
 */
@Composable
fun ProjectCardView(
    project: Project,
    modifier: Modifier = Modifier
) {
    val cardBackground = Color(0xFF2C2C2E)
    
    // Icon color mapping
    val iconColor = when (project.iconColor) {
        "mint" -> Color(0xFF00C7BE)
        "orange" -> Color(0xFFFF9500)
        "blue" -> Color(0xFF007AFF)
        "green" -> Color(0xFF34C759)
        "purple" -> Color(0xFFAF52DE)
        "red" -> Color(0xFFFF3B30)
        else -> Color(0xFF007AFF)
    }
    
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                // Navigate to project detail
            },
        colors = CardDefaults.cardColors(
            containerColor = cardBackground
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Project icon
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(iconColor.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = when (project.iconName) {
                        "phone_android" -> Icons.Default.PhoneAndroid
                        "shopping_cart" -> Icons.Default.ShoppingCart
                        "forum" -> Icons.Default.Forum
                        else -> Icons.Default.Folder
                    },
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(28.dp)
                )
            }
            
            // Project info
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = project.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                
                Text(
                    text = project.description,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                
                // Progress bar (if has tasks)
                if (project.tasksCount > 0) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Progress bar
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(4.dp)
                                .clip(RoundedCornerShape(2.dp))
                                .background(Color(0xFF3A3A3C))
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth(project.progressPercentage.toFloat())
                                    .clip(RoundedCornerShape(2.dp))
                                    .background(Color(0xFF007AFF))
                            )
                        }
                        
                        Text(
                            text = "${project.completedTasksCount}/${project.tasksCount}",
                            fontSize = 12.sp,
                            color = Color.Gray,
                            modifier = Modifier.widthIn(min = 30.dp)
                        )
                    }
                }
            }
            
            // Chevron
            Icon(
                Icons.Default.ChevronRight,
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

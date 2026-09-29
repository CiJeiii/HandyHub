package com.example.handyhub.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.handyhub.data.model.JobPostDto
import com.example.handyhub.data.network.RetrofitClient
import com.example.handyhub.ui.components.AcceptedTaskCard
import com.example.handyhub.ui.components.EmployerStatsHeader
import com.example.handyhub.ui.components.JobCompletionRatingDialog
import com.example.handyhub.ui.components.LogOutConfirmationDialog
import com.example.handyhub.ui.components.ReportNoShowDialog
import com.example.handyhub.ui.theme.HandyHubTheme
import com.example.handyhub.ui.theme.LightPinkButton
import com.example.handyhub.ui.theme.MaroonPrimary
import com.example.handyhub.ui.theme.OffWhiteBackground
import com.example.handyhub.ui.theme.TextDark
import com.example.handyhub.ui.theme.TextSecondary
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun EmployerDashboardScreen(
    onPostJobClick: () -> Unit = {},
    onReviewApplicantsClick: (JobPostDto) -> Unit = {},
    onStalkProfileClick: (String) -> Unit = {},
    onSwitchRoleClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {}
) {
    val context = LocalContext.current
    var selectedTab by remember { mutableStateOf("OPEN_REQUESTS") }

    val jobPosts = remember { mutableStateListOf<JobPostDto>() }
    val activeTasks = remember { mutableStateListOf<JobPostDto>() }
    var isLoading by remember { mutableStateOf(true) }

    // Dialog & menu state
    var jobToRate by remember { mutableStateOf<JobPostDto?>(null) }
    var jobToReportNoShow by remember { mutableStateOf<JobPostDto?>(null) }
    var showAccountMenu by remember { mutableStateOf(false) }
    var showLogoutDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isLoading = true
        val posts = fetchEmployerJobs()
        jobPosts.clear()
        jobPosts.add(
            JobPostDto(
                jobId = 102,
                employerId = "EMP-OWNER-001",
                title = "Ceiling Fan & Outlet Electrical Repair",
                category = "ELECTRICAL",
                description = "Replace living room ceiling fan and fix loose wall outlet.",
                addressDistrict = "Brgy. Carmen, CDO",
                preferredDateTime = "Tomorrow, 10:00 AM",
                budgetPhp = 1500.0,
                status = "OPEN"
            )
        )
        jobPosts.addAll(posts.filter { it.status.uppercase() == "OPEN" && it.jobId != 102 })

        activeTasks.clear()
        activeTasks.add(
            JobPostDto(
                jobId = 101,
                employerId = "EMP-OWNER-001",
                title = "Kitchen Sink & Pipe Leak Repair",
                category = "PLUMBING",
                description = "Fix leaking PVC pipe under sink.",
                addressDistrict = "Brgy. Nazareth, CDO",
                preferredDateTime = "Today, 2:00 PM",
                budgetPhp = 1200.0,
                status = "IN_PROGRESS"
            )
        )
        isLoading = false
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(OffWhiteBackground)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header Banner with Profile Avatar Dropdown Menu
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaroonPrimary)
                    .padding(horizontal = 24.dp)
                    .padding(top = 48.dp, bottom = 20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Employer Hub",
                            color = Color.White,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Black
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Manage your job listings in Cagayan de Oro.",
                            color = Color.White.copy(alpha = 0.9f),
                            fontSize = 13.sp
                        )
                    }

                    Box {
                        IconButton(onClick = { showAccountMenu = true }) {
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(CircleShape)
                                    .background(Color.White),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = "Account Menu",
                                    tint = MaroonPrimary,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                        }

                        // Account Dropdown Menu
                        DropdownMenu(
                            expanded = showAccountMenu,
                            onDismissRequest = { showAccountMenu = false },
                            modifier = Modifier.background(Color.White)
                        ) {
                            // Header Item
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 10.dp)
                            ) {
                                Text(
                                    text = "Atty. Ricardo Dalisay",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextDark
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(LightPinkButton)
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = "EMPLOYER",
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Black,
                                        color = MaroonPrimary
                                    )
                                }
                            }

                            HorizontalDivider(color = Color(0xFFEEEEEE))

                            // Switch Role Action Item
                            DropdownMenuItem(
                                text = {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Default.SwapHoriz,
                                            contentDescription = "Switch Role",
                                            tint = MaroonPrimary,
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Text(
                                            text = "Switch to Worker Mode",
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = TextDark
                                        )
                                    }
                                },
                                onClick = {
                                    showAccountMenu = false
                                    Toast.makeText(context, "Switched to Worker Mode", Toast.LENGTH_SHORT).show()
                                    onSwitchRoleClick()
                                }
                            )

                            HorizontalDivider(color = Color(0xFFEEEEEE))

                            // Log Out Action Item
                            DropdownMenuItem(
                                text = {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Filled.Logout,
                                            contentDescription = "Log Out",
                                            tint = Color(0xFFD32F2F),
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Text(
                                            text = "Log Out",
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFFD32F2F)
                                        )
                                    }
                                },
                                onClick = {
                                    showAccountMenu = false
                                    showLogoutDialog = true
                                }
                            )
                        }
                    }
                }
            }

            // Tab / Toggle Section: Open Requests (1) vs Active Tasks (1)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                TabButton(
                    text = "Open Requests (${jobPosts.size})",
                    isSelected = selectedTab == "OPEN_REQUESTS",
                    onClick = { selectedTab = "OPEN_REQUESTS" },
                    modifier = Modifier.weight(1f)
                )

                TabButton(
                    text = "Active Tasks (${activeTasks.size})",
                    isSelected = selectedTab == "ACTIVE_TASKS",
                    onClick = { selectedTab = "ACTIVE_TASKS" },
                    modifier = Modifier.weight(1f)
                )
            }

            // Body List
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                if (isLoading) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = MaroonPrimary)
                    }
                } else {
                    if (selectedTab == "OPEN_REQUESTS") {
                        if (jobPosts.isEmpty()) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(24.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(16.dp),
                                    colors = CardDefaults.cardColors(containerColor = Color.White),
                                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(32.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Work,
                                            contentDescription = "No Jobs",
                                            tint = MaroonPrimary,
                                            modifier = Modifier.size(56.dp)
                                        )
                                        Spacer(modifier = Modifier.height(14.dp))
                                        Text(
                                            text = "No job requests posted yet. Tap '+' to create your first post!",
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = TextDark,
                                            textAlign = TextAlign.Center,
                                            lineHeight = 20.sp
                                        )
                                    }
                                }
                            }
                        } else {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 20.dp, vertical = 16.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                item {
                                    EmployerStatsHeader(
                                        employerName = "Atty. Ricardo Dalisay",
                                        jobsPostedCount = jobPosts.size + activeTasks.size,
                                        employerRating = 4.8
                                    )
                                }

                                items(jobPosts) { job ->
                                    EmployerJobCard(
                                        job = job,
                                        onReviewApplicantsClick = { onReviewApplicantsClick(job) }
                                    )
                                }

                                item {
                                    Spacer(modifier = Modifier.height(80.dp))
                                }
                            }
                        }
                    } else {
                        // ACTIVE TASKS TAB
                        if (activeTasks.isEmpty()) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "No active tasks in progress.",
                                    fontSize = 14.sp,
                                    color = TextSecondary
                                )
                            }
                        } else {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 20.dp, vertical = 16.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                items(activeTasks) { task ->
                                    AcceptedTaskCard(
                                        job = task,
                                        workerName = "Juan Dela Cruz",
                                        workerTrade = "Master Plumber",
                                        workerPhone = "+63 917 123 4567",
                                        onStalkProfileClick = {
                                            onStalkProfileClick("EMP-WORKER-001")
                                        },
                                        onCallClick = {
                                            Toast.makeText(context, "Calling Juan Dela Cruz...", Toast.LENGTH_SHORT).show()
                                        },
                                        onChatClick = {
                                            onStalkProfileClick("EMP-WORKER-001")
                                        },
                                        onMarkCompletedClick = {
                                            jobToRate = task
                                        },
                                        onReportNoShowClick = {
                                            jobToReportNoShow = task
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Floating Action Button (+)
        FloatingActionButton(
            onClick = onPostJobClick,
            containerColor = MaroonPrimary,
            contentColor = Color.White,
            shape = CircleShape,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp)
                .size(60.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Post New Job",
                modifier = Modifier.size(32.dp)
            )
        }

        // Job Completion & Rating Dialog
        if (jobToRate != null) {
            JobCompletionRatingDialog(
                workerName = "Juan Dela Cruz",
                onDismiss = { jobToRate = null },
                onSubmitReview = { rating, tags, _ ->
                    Toast.makeText(context, "Review submitted ($rating stars, ${tags.size} tags). Job marked COMPLETED!", Toast.LENGTH_LONG).show()
                    activeTasks.remove(jobToRate)
                    jobToRate = null
                }
            )
        }

        // Report No-Show Dialog
        if (jobToReportNoShow != null) {
            ReportNoShowDialog(
                workerName = "Juan Dela Cruz",
                onDismiss = { jobToReportNoShow = null },
                onConfirmNoShow = { reason ->
                    Toast.makeText(
                        context,
                        "No-show recorded ($reason). Penalty applied to worker profile. Job relisted under Open Requests.",
                        Toast.LENGTH_LONG
                    ).show()

                    val taskToRelist = jobToReportNoShow!!
                    taskToRelist.status = "OPEN"
                    activeTasks.remove(taskToRelist)
                    jobPosts.add(taskToRelist)
                    jobToReportNoShow = null
                    selectedTab = "OPEN_REQUESTS"
                }
            )
        }

        // Log Out Confirmation Dialog
        if (showLogoutDialog) {
            LogOutConfirmationDialog(
                onDismiss = { showLogoutDialog = false },
                onConfirmLogOut = {
                    showLogoutDialog = false
                    Toast.makeText(context, "Logged out successfully", Toast.LENGTH_SHORT).show()
                    onLogoutClick()
                }
            )
        }
    }
}

@Composable
fun TabButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(40.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(if (isSelected) MaroonPrimary else Color(0xFFEEEEEE))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = if (isSelected) Color.White else TextDark
        )
    }
}

@Composable
fun EmployerJobCard(
    job: JobPostDto,
    onReviewApplicantsClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(LightPinkButton)
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = job.category,
                        color = MaroonPrimary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFE8F5E9))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = job.status.uppercase(),
                        color = Color(0xFF2E7D32),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = job.title,
                color = TextDark,
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = job.description,
                color = TextSecondary,
                fontSize = 13.sp,
                maxLines = 2
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Location",
                    tint = MaroonPrimary,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = job.addressDistrict,
                    color = TextDark,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.CalendarMonth,
                        contentDescription = "Schedule",
                        tint = MaroonPrimary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = job.preferredDateTime,
                        color = TextDark,
                        fontSize = 12.sp
                    )
                }

                Text(
                    text = "₱${job.budgetPhp.toInt()}",
                    color = MaroonPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Black
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onReviewApplicantsClick,
                colors = ButtonDefaults.buttonColors(containerColor = MaroonPrimary),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(42.dp)
            ) {
                Text(
                    text = "2 Applicants (Review Pending)",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

private suspend fun fetchEmployerJobs(): List<JobPostDto> = withContext(Dispatchers.IO) {
    return@withContext try {
        val response = RetrofitClient.instance.getEmployerJobPosts("EMP-OWNER-001")
        if (response.isSuccessful && response.body() != null) {
            response.body()!!
        } else {
            emptyList()
        }
    } catch (_: Exception) {
        emptyList()
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EmployerDashboardScreenPreview() {
    HandyHubTheme {
        EmployerDashboardScreen()
    }
}

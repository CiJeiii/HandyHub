package com.example.handyhub.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Comment
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
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
import com.example.handyhub.data.model.AllowedCategories
import com.example.handyhub.data.model.JobPostDto
import com.example.handyhub.data.model.ThesisCategories
import com.example.handyhub.ui.components.ApplyJobBottomSheet
import com.example.handyhub.ui.components.JobAcceptedDialog
import com.example.handyhub.ui.components.JobListingCard
import com.example.handyhub.ui.components.LogOutConfirmationDialog
import com.example.handyhub.ui.theme.HandyHubTheme
import com.example.handyhub.ui.theme.LightPinkButton
import com.example.handyhub.ui.theme.MaroonPrimary
import com.example.handyhub.ui.theme.OffWhiteBackground
import com.example.handyhub.ui.theme.TextDark
import com.example.handyhub.ui.theme.TextSecondary
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployeeFeedScreen(
    onProfileClick: () -> Unit = {},
    onNotificationsClick: () -> Unit = {},
    onChatClick: (String) -> Unit = {},
    onSwitchRoleClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {}
) {
    val context = LocalContext.current

    var selectedTab by remember { mutableStateOf("OPEN_JOBS") } // "OPEN_JOBS" or "MY_APPLICATIONS"
    var selectedCategoryFilter by remember { mutableStateOf("ALL") }
    var selectedBarangayFilter by remember { mutableStateOf("All CDO Barangays") }
    var barangayDropdownExpanded by remember { mutableStateOf(false) }

    val jobFeed = remember { mutableStateMapOf<Int, JobPostDto>() }
    val appliedJobIds = remember { mutableStateMapOf<Int, Boolean>() }
    val acceptedJobIds = remember { mutableStateMapOf<Int, Boolean>() }
    var isLoading by remember { mutableStateOf(true) }

    // Bottom sheet & dialog state
    var jobToApply by remember { mutableStateOf<JobPostDto?>(null) }
    var showJobAcceptedDialog by remember { mutableStateOf(false) }
    var showAccountMenu by remember { mutableStateOf(false) }
    var showLogoutDialog by remember { mutableStateOf(false) }

    val cdoBarangays = listOf(
        "All CDO Barangays",
        "Brgy. Carmen",
        "Brgy. Nazareth",
        "Brgy. Kauswagan",
        "Brgy. Lapasan",
        "Brgy. Puerto"
    )

    LaunchedEffect(selectedCategoryFilter, selectedBarangayFilter) {
        isLoading = true
        val jobs = fetchMockOpenJobs(selectedCategoryFilter, selectedBarangayFilter)
        jobFeed.clear()
        jobs.forEach { jobFeed[it.jobId] = it }
        acceptedJobIds[102] = true
        appliedJobIds[102] = true
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
            // Header Banner with Notification Bell, Account Dropdown Menu & Logout
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaroonPrimary)
                    .padding(horizontal = 20.dp)
                    .padding(top = 44.dp, bottom = 16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Worker Job Feed",
                            color = Color.White,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Black
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Find open job requests in Cagayan de Oro",
                            color = Color.White.copy(alpha = 0.9f),
                            fontSize = 12.sp
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // Notification Bell with Badge
                        Box {
                            IconButton(onClick = onNotificationsClick) {
                                Icon(
                                    imageVector = Icons.Default.Notifications,
                                    contentDescription = "Notifications",
                                    tint = Color.White
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .size(16.dp)
                                    .align(Alignment.TopEnd)
                                    .padding(top = 4.dp, end = 4.dp)
                                    .clip(CircleShape)
                                    .background(Color.Red),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "1",
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }

                        // Account Profile Dropdown Menu Button
                        Box {
                            IconButton(onClick = { showAccountMenu = true }) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(CircleShape)
                                        .background(Color.White),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Person,
                                        contentDescription = "Account Menu",
                                        tint = MaroonPrimary,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }

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
                                        text = "Juan Dela Cruz",
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
                                            text = "WORKER",
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Black,
                                            color = MaroonPrimary
                                        )
                                    }
                                }

                                HorizontalDivider(color = Color(0xFFEEEEEE))

                                // "My Profile" Action Item
                                DropdownMenuItem(
                                    text = {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(
                                                imageVector = Icons.Default.Person,
                                                contentDescription = "My Profile",
                                                tint = MaroonPrimary,
                                                modifier = Modifier.size(18.dp)
                                            )
                                            Spacer(modifier = Modifier.width(10.dp))
                                            Text(
                                                text = "My Profile",
                                                fontSize = 13.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = TextDark
                                            )
                                        }
                                    },
                                    onClick = {
                                        showAccountMenu = false
                                        onProfileClick()
                                    }
                                )

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
                                                text = "Switch to Employer Mode",
                                                fontSize = 13.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = TextDark
                                            )
                                        }
                                    },
                                    onClick = {
                                        showAccountMenu = false
                                        Toast.makeText(context, "Switched to Employer Mode", Toast.LENGTH_SHORT).show()
                                        onSwitchRoleClick()
                                    }
                                )

                                HorizontalDivider(color = Color(0xFFEEEEEE))

                                // Log Out Action Item
                                DropdownMenuItem(
                                    text = {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(
                                                imageVector = Icons.AutoMirrored.Filled.ExitToApp,
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
            }

            // Tab Toggle: Open Jobs Feed vs My Applications (1 Accepted)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                EmployeeTabButton(
                    text = "Open Jobs Feed",
                    isSelected = selectedTab == "OPEN_JOBS",
                    onClick = { selectedTab = "OPEN_JOBS" },
                    modifier = Modifier.weight(1f)
                )

                EmployeeTabButton(
                    text = "My Applications (${acceptedJobIds.size} Accepted)",
                    isSelected = selectedTab == "MY_APPLICATIONS",
                    onClick = { selectedTab = "MY_APPLICATIONS" },
                    modifier = Modifier.weight(1.2f)
                )
            }

            if (selectedTab == "OPEN_JOBS") {
                // Top Filter Bar: Barangay Dropdown
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    ExposedDropdownMenuBox(
                        expanded = barangayDropdownExpanded,
                        onExpandedChange = { barangayDropdownExpanded = !barangayDropdownExpanded },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = selectedBarangayFilter,
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = barangayDropdownExpanded) },
                            modifier = Modifier
                                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable, true)
                                .fillMaxWidth()
                                .height(56.dp)
                                .background(Color(0xFFF5F5F7), RoundedCornerShape(10.dp)),
                            shape = RoundedCornerShape(10.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = Color(0xFFF5F5F7),
                                unfocusedContainerColor = Color(0xFFF5F5F7),
                                focusedBorderColor = MaroonPrimary,
                                unfocusedBorderColor = Color(0xFFDDDDDD),
                                focusedTextColor = TextDark,
                                unfocusedTextColor = TextDark
                            )
                        )

                        ExposedDropdownMenu(
                            expanded = barangayDropdownExpanded,
                            onDismissRequest = { barangayDropdownExpanded = false },
                            modifier = Modifier.background(Color.White)
                        ) {
                            cdoBarangays.forEach { barangay ->
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = barangay,
                                            fontSize = 13.sp,
                                            color = if (barangay == selectedBarangayFilter) MaroonPrimary else TextDark,
                                            fontWeight = if (barangay == selectedBarangayFilter) FontWeight.Bold else FontWeight.Normal
                                        )
                                    },
                                    onClick = {
                                        selectedBarangayFilter = barangay
                                        barangayDropdownExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }

                // Category Horizontal Scroll Chips
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val filters = listOf("ALL") + AllowedCategories.CATEGORIES

                    items(filters) { category ->
                        val isSelected = category.equals(selectedCategoryFilter, ignoreCase = true)
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(
                                    if (isSelected) MaroonPrimary else Color(0xFFF5F5F7)
                                )
                                .border(
                                    width = 1.dp,
                                    color = if (isSelected) MaroonPrimary else Color(0xFFDDDDDD),
                                    shape = RoundedCornerShape(20.dp)
                                )
                                .clickable { selectedCategoryFilter = category }
                                .padding(horizontal = 14.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = category,
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) Color.White else TextDark
                            )
                        }
                    }
                }
            }

            // Main List Area
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
                    if (selectedTab == "OPEN_JOBS") {
                        val filteredJobs = jobFeed.values.filter { job ->
                            val jobDisplayName = ThesisCategories.getDisplayName(job.category)
                            val matchesCategory = (selectedCategoryFilter.equals("ALL", ignoreCase = true) ||
                                jobDisplayName.equals(selectedCategoryFilter, ignoreCase = true) ||
                                job.category.equals(selectedCategoryFilter, ignoreCase = true))

                            val cleanBarangayFilter = selectedBarangayFilter.replace("Brgy. ", "", ignoreCase = true)
                            val matchesBarangay = (selectedBarangayFilter == "All CDO Barangays" ||
                                job.addressDistrict.contains(cleanBarangayFilter, ignoreCase = true))

                            matchesCategory && matchesBarangay
                        }

                        if (filteredJobs.isEmpty()) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(32.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Work,
                                    contentDescription = "No Jobs",
                                    tint = Color.LightGray,
                                    modifier = Modifier.size(56.dp)
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    text = "No jobs posted in $selectedBarangayFilter for $selectedCategoryFilter",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextDark,
                                    textAlign = TextAlign.Center
                                )
                            }
                        } else {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 16.dp, vertical = 12.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                items(filteredJobs) { job ->
                                    JobListingCard(
                                        job = job,
                                        employerName = when (job.jobId) {
                                            102 -> "Maria Clara"
                                            103 -> "Atty. Ricardo Dalisay"
                                            else -> "Don Juan Dela Cruz"
                                        },
                                        hasApplied = appliedJobIds[job.jobId] ?: false,
                                        onApplyClick = {
                                            jobToApply = job
                                        }
                                    )
                                }

                                item {
                                    Spacer(modifier = Modifier.height(24.dp))
                                }
                            }
                        }
                    } else {
                        // MY APPLICATIONS TAB
                        val acceptedJobs = jobFeed.values.filter { acceptedJobIds[it.jobId] == true }

                        if (acceptedJobs.isEmpty()) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "No accepted applications yet.",
                                    fontSize = 14.sp,
                                    color = TextSecondary
                                )
                            }
                        } else {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 16.dp, vertical = 12.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                items(acceptedJobs) { job ->
                                    AcceptedApplicationCard(
                                        job = job,
                                        employerName = "Maria Clara",
                                        employerPhone = "+63 917 888 9999",
                                        onCallClick = {
                                            Toast.makeText(context, "Calling Maria Clara (+63 917 888 9999)...", Toast.LENGTH_SHORT).show()
                                        },
                                        onChatClick = {
                                            onChatClick("EMP-01")
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Modal Bottom Sheet for Proposal Submission
        if (jobToApply != null) {
            ApplyJobBottomSheet(
                job = jobToApply!!,
                onDismiss = { jobToApply = null },
                onSubmitProposal = { _ ->
                    val appliedJob = jobToApply!!
                    appliedJobIds[appliedJob.jobId] = true
                    Toast.makeText(context, "Proposal submitted successfully for ${appliedJob.title}!", Toast.LENGTH_LONG).show()
                    jobToApply = null
                    showJobAcceptedDialog = true
                }
            )
        }

        // Job Acceptance Notification Dialog Demo
        if (showJobAcceptedDialog) {
            JobAcceptedDialog(
                employerName = "Maria Clara",
                jobTitle = "Ceiling Fan & Outlet Repair",
                budget = "₱1,500",
                employerPhone = "+63 917 888 9999",
                onDismiss = { showJobAcceptedDialog = false },
                onCallClientClick = {
                    Toast.makeText(context, "Calling Maria Clara (+63 917 888 9999)...", Toast.LENGTH_SHORT).show()
                },
                onOpenChatClick = {
                    showJobAcceptedDialog = false
                    onChatClick("EMP-01")
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
fun AcceptedApplicationCard(
    job: JobPostDto,
    employerName: String,
    employerPhone: String,
    onCallClick: () -> Unit,
    onChatClick: () -> Unit
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
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFE8F5E9))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "Application Accepted",
                        color = Color(0xFF2E7D32),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black
                    )
                }

                Text(
                    text = "₱${job.budgetPhp.toInt()}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Black,
                    color = MaroonPrimary
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = job.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                color = TextDark
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Client: $employerName · ${job.addressDistrict}",
                fontSize = 12.sp,
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(12.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFE8F5E9))
                    .border(1.dp, Color(0xFFA5D6A7), RoundedCornerShape(8.dp))
                    .padding(12.dp)
            ) {
                Column {
                    Text(
                        text = "UNLOCKED EMPLOYER CONTACT",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2E7D32)
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "$employerName · 🔓 $employerPhone",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF1B5E20)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = onCallClick,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32)),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(42.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = "Call",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Call", color = Color.White, fontSize = 13.sp)
                }

                Button(
                    onClick = onChatClick,
                    colors = ButtonDefaults.buttonColors(containerColor = MaroonPrimary),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .weight(1.2f)
                        .height(42.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Comment,
                        contentDescription = "Chat",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Chat with Client", color = Color.White, fontSize = 13.sp)
                }
            }
        }
    }
}

@Composable
fun EmployeeTabButton(
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
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = if (isSelected) Color.White else TextDark
        )
    }
}

private suspend fun fetchMockOpenJobs(categoryFilter: String, barangayFilter: String): List<JobPostDto> = withContext(Dispatchers.IO) {
    return@withContext try {
        val allMockJobs = listOf(
            JobPostDto(
                jobId = 102,
                employerId = "EMP-01",
                title = "Ceiling Fan & Outlet Electrical Repair",
                category = ThesisCategories.ELECTRICAL,
                description = "Replace living room ceiling fan and fix loose wall outlet in bedroom.",
                addressDistrict = "Brgy. Carmen, CDO",
                preferredDateTime = "Today, 2:00 PM",
                budgetPhp = 1500.0,
                status = "OPEN"
            ),
            JobPostDto(
                jobId = 103,
                employerId = "EMP-02",
                title = "Kitchen Sink Pipe & Drain Leak Repair",
                category = ThesisCategories.PLUMBING,
                description = "Steady water leak under kitchen sink main PVC line.",
                addressDistrict = "Brgy. Nazareth, CDO",
                preferredDateTime = "Tomorrow, 9:00 AM",
                budgetPhp = 1200.0,
                status = "OPEN"
            ),
            JobPostDto(
                jobId = 104,
                employerId = "EMP-03",
                title = "Inverter Washing Machine Motor Servicing",
                category = ThesisCategories.APPLIANCE_SERVICING,
                description = "Automatic washing machine not spinning during rinse cycle.",
                addressDistrict = "Brgy. Kauswagan, CDO",
                preferredDateTime = "As Soon As Possible",
                budgetPhp = 1800.0,
                status = "OREN"
            )
        )

        allMockJobs.filter { job ->
            val jobDisplayName = ThesisCategories.getDisplayName(job.category)
            val matchesCategory = (categoryFilter.equals("ALL", ignoreCase = true) ||
                jobDisplayName.equals(categoryFilter, ignoreCase = true) ||
                job.category.equals(categoryFilter, ignoreCase = true))

            val cleanBarangayFilter = barangayFilter.replace("Brgy. ", "", ignoreCase = true)
            val matchesBarangay = (barangayFilter == "All CDO Barangays" ||
                job.addressDistrict.contains(cleanBarangayFilter, ignoreCase = true))

            matchesCategory && matchesBarangay
        }
    } catch (_: Exception) {
        emptyList()
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EmployeeFeedScreenPreview() {
    HandyHubTheme {
        EmployeeFeedScreen()
    }
}

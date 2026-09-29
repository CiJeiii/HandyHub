package com.example.handyhub.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.handyhub.ui.components.AutoPortfolioGallery
import com.example.handyhub.ui.components.CategoryMasteryCard
import com.example.handyhub.ui.components.CategoryMasteryData
import com.example.handyhub.ui.components.ClientReview
import com.example.handyhub.ui.components.ClientReviewCard
import com.example.handyhub.ui.components.PortfolioProject
import com.example.handyhub.ui.components.TrustAndVerificationCard
import com.example.handyhub.ui.theme.HandyHubTheme
import com.example.handyhub.ui.theme.LightPinkButton
import com.example.handyhub.ui.theme.MaroonPrimary
import com.example.handyhub.ui.theme.OffWhiteBackground
import com.example.handyhub.ui.theme.TextDark
import com.example.handyhub.ui.theme.TextSecondary

@Composable
fun WorkerProfileScreen(
    workerName: String = "Juan Dela Cruz",
    primaryTrade: String = "Master Plumber & Electrician",
    workerPhone: String = "+63 917 888 9999",
    avatarUrl: String? = null,
    onBackClick: () -> Unit = {}
) {
    val context = LocalContext.current
    var showEditDialog by remember { mutableStateOf(false) }

    // Editable state for worker self-profile
    var currentName by remember { mutableStateOf(workerName) }
    var currentPhone by remember { mutableStateOf(workerPhone) }
    var currentTrade by remember { mutableStateOf(primaryTrade) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(OffWhiteBackground)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Scrollable Content
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Header Top Bar with Edit Profile IconButton (pencil icon)
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaroonPrimary)
                            .padding(horizontal = 20.dp)
                            .padding(top = 44.dp, bottom = 48.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                IconButton(onClick = onBackClick) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = "Back",
                                        tint = Color.White
                                    )
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "My Worker Profile",
                                    color = Color.White,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Black
                                )
                            }

                            // Edit Profile Pencil Icon
                            IconButton(onClick = { showEditDialog = true }) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Edit Profile",
                                    tint = Color.White
                                )
                            }
                        }
                    }
                }

                // Profile Header Card
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(20.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(80.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFFEEEEEE))
                                        .border(2.dp, MaroonPrimary, CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (!avatarUrl.isNullOrEmpty()) {
                                        AsyncImage(
                                            model = avatarUrl,
                                            contentDescription = currentName,
                                            modifier = Modifier.fillMaxSize(),
                                            contentScale = ContentScale.Crop
                                        )
                                    } else {
                                        Icon(
                                            imageVector = Icons.Default.Person,
                                            contentDescription = "Avatar",
                                            tint = Color.Gray,
                                            modifier = Modifier.size(45.dp)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(12.dp))

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = currentName,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Black,
                                        color = TextDark
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Icon(
                                        imageVector = Icons.Default.Verified,
                                        contentDescription = "Verified Pro",
                                        tint = Color(0xFF1976D2),
                                        modifier = Modifier.size(18.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.height(4.dp))

                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(LightPinkButton)
                                        .padding(horizontal = 10.dp, vertical = 4.dp)
                                ) {
                                    Text(
                                        text = currentTrade.uppercase(),
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaroonPrimary
                                    )
                                }

                                Spacer(modifier = Modifier.height(10.dp))

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = "Rating",
                                        tint = Color(0xFFFFB300),
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "4.9 · 98% Recommended by CDO Clients",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = TextSecondary
                                    )
                                }
                            }
                        }
                    }
                }

                // Phone Section: ALWAYS real unlocked phone number for worker self-view
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(14.dp))
                                .background(Color(0xFFE8F5E9))
                                .border(1.dp, Color(0xFFA5D6A7), RoundedCornerShape(14.dp))
                                .padding(16.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.LockOpen,
                                    contentDescription = "Unlocked",
                                    tint = Color(0xFF2E7D32),
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = "MY VERIFIED MOBILE NUMBER (UNLOCKED)",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF2E7D32)
                                    )
                                    Text(
                                        text = currentPhone,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = Color(0xFF1B5E20)
                                    )
                                }
                            }
                        }
                    }
                }

                // Trust & Verification Section (Worker Owner POV with Upload button)
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                    ) {
                        TrustAndVerificationCard(
                            onUploadCredentialsClick = {
                                Toast.makeText(context, "Opening credentials upload manager...", Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                }

                // Dynamic Skill Mastery Breakdown
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                    ) {
                        Text(
                            text = "CATEGORY MASTERY BREAKDOWN",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextSecondary,
                            letterSpacing = 0.5.sp
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        val masteryList = listOf(
                            CategoryMasteryData("PLUMBING", "Plumbing Services", "Master Level 5", 0.95f, 42, 4.9),
                            CategoryMasteryData("ELECTRICAL", "Electrical Services", "Expert Level 4", 0.85f, 20, 4.8)
                        )

                        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            masteryList.forEach { data ->
                                CategoryMasteryCard(data = data)
                            }
                        }
                    }
                }

                // Auto-Indexed Portfolio Gallery
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                    ) {
                        AutoPortfolioGallery(
                            projects = listOf(
                                PortfolioProject("1", "Kitchen Water Pipe Replacement", "Plumbing", "", "Sept 20, 2026", 5.0),
                                PortfolioProject("2", "Circuit Breaker Upgrade", "Electrical", "", "Sept 15, 2026", 4.8)
                            )
                        )
                    }
                }

                // Client Reviews & Badges
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                    ) {
                        Text(
                            text = "CLIENT REVIEWS & FEEDBACK",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextSecondary,
                            letterSpacing = 0.5.sp
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        val reviews = listOf(
                            ClientReview("1", "Maria Santos", 5, "Yesterday", "Very professional and cleaned up the workstation after repairing our kitchen sink. Highly recommended!", listOf("Punctual", "Detail-Oriented", "Clean Worksite")),
                            ClientReview("2", "Atty. Ricardo Dalisay", 5, "3 days ago", "Fixed our bathroom plumbing rapidly without any hassle.", listOf("Punctual", "Efficient"))
                        )

                        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            reviews.forEach { review ->
                                ClientReviewCard(review = review)
                            }
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(30.dp))
                }
            }
        }

        // Edit Profile AlertDialog
        if (showEditDialog) {
            var tempName by remember { mutableStateOf(currentName) }
            var tempPhone by remember { mutableStateOf(currentPhone) }
            var tempTrade by remember { mutableStateOf(currentTrade) }

            AlertDialog(
                onDismissRequest = { showEditDialog = false },
                title = { Text("Edit Worker Profile", fontWeight = FontWeight.Bold, color = MaroonPrimary) },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        OutlinedTextField(
                            value = tempName,
                            onValueChange = { tempName = it },
                            label = { Text("Full Name") },
                            singleLine = true,
                            colors = textFieldColors()
                        )
                        OutlinedTextField(
                            value = tempPhone,
                            onValueChange = { tempPhone = it },
                            label = { Text("Phone Number") },
                            singleLine = true,
                            colors = textFieldColors()
                        )
                        OutlinedTextField(
                            value = tempTrade,
                            onValueChange = { tempTrade = it },
                            label = { Text("Primary Trade / Specialization") },
                            singleLine = true,
                            colors = textFieldColors()
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            currentName = tempName
                            currentPhone = tempPhone
                            currentTrade = tempTrade
                            showEditDialog = false
                            Toast.makeText(context, "Profile updated successfully!", Toast.LENGTH_SHORT).show()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaroonPrimary)
                    ) {
                        Text("Save Changes", color = Color.White)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showEditDialog = false }) {
                        Text("Cancel", color = TextSecondary)
                    }
                }
            )
        }
    }
}

@Composable
private fun textFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedContainerColor = Color.White,
    unfocusedContainerColor = Color.White,
    focusedBorderColor = MaroonPrimary,
    unfocusedBorderColor = Color(0xFFCCCCCC),
    focusedTextColor = TextDark,
    unfocusedTextColor = TextDark
)

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WorkerProfileScreenPreview() {
    HandyHubTheme {
        WorkerProfileScreen()
    }
}

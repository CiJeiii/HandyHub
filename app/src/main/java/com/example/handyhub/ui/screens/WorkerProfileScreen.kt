package com.example.handyhub.ui.screens

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import com.example.handyhub.ui.theme.HandyHubTheme
import com.example.handyhub.ui.theme.LightPinkButton
import com.example.handyhub.ui.theme.MaroonPrimary
import com.example.handyhub.ui.theme.OffWhiteBackground
import com.example.handyhub.ui.theme.TextDark
import com.example.handyhub.ui.theme.TextSecondary

@Composable
fun WorkerProfileScreen(
    workerName: String = "Juan Dela Cruz",
    primaryTrade: String = "Master Plumber",
    avatarUrl: String? = null,
    onBackClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(OffWhiteBackground)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header Banner & Profile Card
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaroonPrimary)
                        .padding(horizontal = 20.dp)
                        .padding(top = 44.dp, bottom = 48.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = onBackClick) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Worker Profile & Mastery",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Black
                        )
                    }
                }
            }

            // Profile Header Card (Overlapping header)
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
                                        contentDescription = workerName,
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
                                    text = workerName,
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
                                    text = primaryTrade.uppercase(),
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

            // Dynamic Skill Mastery Section (All 5 Categories)
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                ) {
                    Text(
                        text = "DYNAMIC SKILL MASTERY TIERS",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextSecondary,
                        letterSpacing = 0.5.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    val masteryList = listOf(
                        CategoryMasteryData("PLUMBING", "Plumbing Services", "Master", 0.95f, 42, 4.9),
                        CategoryMasteryData("CARPENTRY", "Carpentry & Woodwork", "Expert", 0.80f, 18, 4.8),
                        CategoryMasteryData("ELECTRICAL", "Electrical Services", "Proficient", 0.65f, 12, 4.7),
                        CategoryMasteryData("HOUSE_CLEANING", "House Cleaning", "Proficient", 0.60f, 10, 4.8),
                        CategoryMasteryData("APPLIANCE_SERVICING", "Appliance Servicing", "Novice", 0.35f, 5, 4.6)
                    )

                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        masteryList.forEach { data ->
                            CategoryMasteryCard(data = data)
                        }
                    }
                }
            }

            // Auto-Indexed Dynamic Portfolio Gallery
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                ) {
                    AutoPortfolioGallery(
                        projects = listOf(
                            PortfolioProject("1", "Kitchen Pipe Repair", "Plumbing", "", "Sept 20, 2026", 5.0),
                            PortfolioProject("2", "Custom Cabinet Hinges", "Carpentry", "", "Sept 15, 2026", 4.9),
                            PortfolioProject("3", "Main Breaker Check", "Electrical", "", "Sept 10, 2026", 4.8)
                        )
                    )
                }
            }

            // Client Reviews & Feedback List
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
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WorkerProfileScreenPreview() {
    HandyHubTheme {
        WorkerProfileScreen()
    }
}

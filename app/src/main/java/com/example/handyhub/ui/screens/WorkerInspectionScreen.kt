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
import androidx.compose.material.icons.automirrored.filled.Comment
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
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
import com.example.handyhub.ui.theme.HandyHubTheme
import com.example.handyhub.ui.theme.LightPinkButton
import com.example.handyhub.ui.theme.MaroonPrimary
import com.example.handyhub.ui.theme.OffWhiteBackground
import com.example.handyhub.ui.theme.TextDark
import com.example.handyhub.ui.theme.TextSecondary

@Composable
fun WorkerInspectionScreen(
    workerId: String = "EMP-01",
    workerName: String = "Roberto \"Bert\" Flores",
    primaryTrade: String = "Master Electrician",
    avatarUrl: String? = null,
    initialAccepted: Boolean = false,
    onBackClick: () -> Unit = {},
    onChatClick: (String) -> Unit = {}
) {
    val context = LocalContext.current
    var isAccepted by remember { mutableStateOf(initialAccepted) }

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
                // Header Top Bar
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
                                text = "Worker Inspection Profile",
                                color = Color.White,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Black
                            )
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
                                        text = "4.9 · 96% Recommended by CDO Clients",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = TextSecondary
                                    )
                                }
                            }
                        }
                    }
                }

                // Contact Section Logic (Privacy Protected vs Unlocked)
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
                                .background(if (isAccepted) Color(0xFFE8F5E9) else Color(0xFFFFF8E1))
                                .border(
                                    1.dp,
                                    if (isAccepted) Color(0xFFA5D6A7) else Color(0xFFFFE082),
                                    RoundedCornerShape(14.dp)
                                )
                                .padding(16.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = if (isAccepted) Icons.Default.LockOpen else Icons.Default.Lock,
                                        contentDescription = "Contact Status",
                                        tint = if (isAccepted) Color(0xFF2E7D32) else Color(0xFFF57F17),
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column {
                                        Text(
                                            text = if (isAccepted) "🔓 UNLOCKED CONTACT" else "🔒 PRIVACY PROTECTED",
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = if (isAccepted) Color(0xFF2E7D32) else Color(0xFFF57F17)
                                        )
                                        Text(
                                            text = if (isAccepted) "+63 917 888 9999" else "+63 917 **** ***",
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.ExtraBold,
                                            color = if (isAccepted) Color(0xFF1B5E20) else TextDark
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // Category Mastery Breakdown
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
                            CategoryMasteryData("ELECTRICAL", "Electrical Services", "Expert Level 4", 0.90f, 38, 4.9),
                            CategoryMasteryData("APPLIANCE_SERVICING", "Appliance Servicing", "Proficient", 0.70f, 14, 4.7)
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
                                PortfolioProject("1", "Main Circuit Breaker Upgrade", "Electrical", "", "Sept 22, 2026", 5.0),
                                PortfolioProject("2", "Ceiling Fan & Light Fixture", "Electrical", "", "Sept 18, 2026", 4.9)
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
                            ClientReview("1", "Atty. Ricardo Dalisay", 5, "2 days ago", "Roberto is an exceptional electrician. Diagnosed our panel box issue instantly.", listOf("Punctual", "Detail-Oriented", "Clean Worksite")),
                            ClientReview("2", "Maria Santos", 5, "Last week", "Very polite and professional service in Carmen.", listOf("Polite", "Fair Pricing"))
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

            // Sticky Bottom Action Bar (Employer POV)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .border(width = 1.dp, color = Color(0xFFE0E0E0))
                    .padding(16.dp)
            ) {
                if (isAccepted) {
                    // ACCEPTED: Call & Chat
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Button(
                            onClick = { Toast.makeText(context, "Calling $workerName (+63 917 888 9999)...", Toast.LENGTH_SHORT).show() },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32)),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Phone, contentDescription = "Call", tint = Color.White)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Call", color = Color.White, fontWeight = FontWeight.Bold)
                        }

                        Button(
                            onClick = { onChatClick(workerId) },
                            colors = ButtonDefaults.buttonColors(containerColor = MaroonPrimary),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .weight(1.5f)
                                .height(48.dp)
                        ) {
                            Icon(imageVector = Icons.AutoMirrored.Filled.Comment, contentDescription = "Chat", tint = Color.White)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Chat with Worker", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }
                } else {
                    // PENDING / REJECTED: Decline & Accept Application
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedButton(
                            onClick = {
                                Toast.makeText(context, "Declined application for $workerName", Toast.LENGTH_SHORT).show()
                                onBackClick()
                            },
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                        ) {
                            Text("Decline", color = Color(0xFFC62828), fontWeight = FontWeight.Bold)
                        }

                        Button(
                            onClick = {
                                isAccepted = true
                                Toast.makeText(context, "Application Accepted! Contact details unlocked.", Toast.LENGTH_LONG).show()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = MaroonPrimary),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .weight(1.5f)
                                .height(48.dp)
                        ) {
                            Text("Accept Application", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WorkerInspectionScreenPreview() {
    HandyHubTheme {
        WorkerInspectionScreen()
    }
}

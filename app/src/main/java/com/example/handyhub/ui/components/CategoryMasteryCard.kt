package com.example.handyhub.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.ElectricalServices
import androidx.compose.material.icons.filled.HomeRepairService
import androidx.compose.material.icons.filled.Plumbing
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.handyhub.ui.theme.HandyHubTheme
import com.example.handyhub.ui.theme.LightPinkButton
import com.example.handyhub.ui.theme.MaroonPrimary
import com.example.handyhub.ui.theme.TextDark
import com.example.handyhub.ui.theme.TextSecondary

data class CategoryMasteryData(
    val categoryKey: String,
    val categoryName: String,
    val tierBadge: String, // "Novice", "Proficient", "Expert", "Master"
    val progress: Float, // 0.0f to 1.0f
    val completedJobsCount: Int,
    val averageRating: Double,
)

@Composable
fun CategoryMasteryCard(
    data: CategoryMasteryData,
    modifier: Modifier = Modifier,
) {
    val icon = getCategoryIcon(data.categoryKey)

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(LightPinkButton),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = data.categoryName,
                            tint = MaroonPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column {
                        Text(
                            text = data.categoryName,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextDark
                        )
                        Text(
                            text = "${data.completedJobsCount} Jobs Completed",
                            fontSize = 11.sp,
                            color = TextSecondary
                        )
                    }
                }

                // Mastery Tier Badge
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            when (data.tierBadge.uppercase()) {
                                "MASTER" -> Color(0xFFE8F5E9)
                                "EXPERT" -> Color(0xFFE3F2FD)
                                "PROFICIENT" -> Color(0xFFFFF3E0)
                                else -> Color(0xFFEEEEEE)
                            }
                        )
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = data.tierBadge.uppercase(),
                        color = when (data.tierBadge.uppercase()) {
                            "MASTER" -> Color(0xFF2E7D32)
                            "EXPERT" -> Color(0xFF1565C0)
                            "PROFICIENT" -> Color(0xFFE65100)
                            else -> Color(0xFF616161)
                        },
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Progress Bar & Rating
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                LinearProgressIndicator(
                    progress = { data.progress },
                    modifier = Modifier
                        .weight(1f)
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    color = MaroonPrimary,
                    trackColor = Color(0xFFEEEEEE)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Rating",
                        tint = Color(0xFFFFB300),
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = data.averageRating.toString(),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDark
                    )
                }
            }
        }
    }
}

private fun getCategoryIcon(key: String): ImageVector {
    return when (key.uppercase()) {
        "PLUMBING" -> Icons.Default.Plumbing
        "CARPENTRY" -> Icons.Default.HomeRepairService
        "ELECTRICAL", "ELECTRICAL_SERVICES" -> Icons.Default.ElectricalServices
        "HOUSE_CLEANING" -> Icons.Default.CleaningServices
        else -> Icons.Default.Build
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryMasteryCardPreview() {
    HandyHubTheme {
        CategoryMasteryCard(
            data = CategoryMasteryData(
                categoryKey = "PLUMBING",
                categoryName = "Plumbing Services",
                tierBadge = "Expert",
                progress = 0.85f,
                completedJobsCount = 24,
                averageRating = 4.9
            )
        )
    }
}

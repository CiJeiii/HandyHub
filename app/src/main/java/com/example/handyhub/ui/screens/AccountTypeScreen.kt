package com.example.handyhub.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.handyhub.ui.theme.CardGreyPlaceholder
import com.example.handyhub.ui.theme.HandyHubTheme
import com.example.handyhub.ui.theme.MaroonPrimary
import com.example.handyhub.ui.theme.OffWhiteBackground
import com.example.handyhub.ui.theme.TextDark
import com.example.handyhub.ui.theme.TextSecondary

enum class Role {
    EMPLOYEE,
    EMPLOYER
}

@Composable
fun AccountTypeScreen(
    onSelectRole: (Role) -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(OffWhiteBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(44.dp))

            // Navigation Row with Back Arrow
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.size(36.dp)
                ) {
                    Canvas(modifier = Modifier.size(20.dp)) {
                        val stroke = 2.5.dp.toPx()
                        val color = TextDark
                        val w = size.width
                        val h = size.height

                        drawLine(
                            color = color,
                            start = Offset(w, h / 2f),
                            end = Offset(0f, h / 2f),
                            strokeWidth = stroke,
                            cap = StrokeCap.Round
                        )
                        val arrowHead = Path().apply {
                            moveTo(w * 0.35f, h * 0.15f)
                            lineTo(0f, h / 2f)
                            lineTo(w * 0.35f, h * 0.85f)
                        }
                        drawPath(
                            path = arrowHead,
                            color = color,
                            style = Stroke(width = stroke, cap = StrokeCap.Round, join = StrokeJoin.Round)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Title section matching Image 2
            Text(
                text = "How will you use",
                color = TextDark,
                fontSize = 32.sp,
                fontWeight = FontWeight.Black,
                lineHeight = 36.sp
            )
            Text(
                text = "HandyHub?",
                color = MaroonPrimary,
                fontSize = 34.sp,
                fontWeight = FontWeight.Black,
                lineHeight = 38.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Choose your role to get started. You can switch anytime.",
                color = TextSecondary,
                fontSize = 13.sp,
                fontWeight = FontWeight.Normal,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Card 1: Employee ("I want to work")
            RoleSelectionCard(
                title = "I want to work",
                subtitle = "Browse job listings and earn with your skills.",
                actionText = "Continue as Employee",
                onClick = { onSelectRole(Role.EMPLOYEE) },
                iconDrawer = { WorkerIconBox(it) }
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Card 2: Employer ("I want to hire")
            RoleSelectionCard(
                title = "I want to hire",
                subtitle = "Post jobs and find skilled tradespeople in CDO",
                actionText = "Continue as Employer",
                onClick = { onSelectRole(Role.EMPLOYER) },
                iconDrawer = { EmployerIconBox(it) }
            )
        }
    }
}

@Composable
fun RoleSelectionCard(
    title: String,
    subtitle: String,
    actionText: String,
    onClick: () -> Unit,
    iconDrawer: @Composable (Modifier) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            // Placeholder Box
            iconDrawer(
                Modifier
                    .width(85.dp)
                    .height(52.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Card Title
            Text(
                text = title,
                color = TextDark,
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Card Subtitle
            Text(
                text = subtitle,
                color = TextSecondary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Action Text
            Text(
                text = actionText,
                color = MaroonPrimary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { onClick() }
            )
        }
    }
}

@Composable
private fun WorkerIconBox(modifier: Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(CardGreyPlaceholder),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(32.dp)) {
            val w = size.width
            val h = size.height
            val color = Color(0xFF757575)

            // Hammer / tool outline
            val path = Path().apply {
                moveTo(w * 0.3f, h * 0.7f)
                lineTo(w * 0.7f, h * 0.3f)
            }
            drawPath(
                path = path,
                color = color,
                style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
            )
            drawRoundRect(
                color = color,
                topLeft = Offset(w * 0.58f, h * 0.18f),
                size = Size(w * 0.28f, h * 0.20f),
                cornerRadius = CornerRadius(4.dp.toPx())
            )
        }
    }
}

@Composable
private fun EmployerIconBox(modifier: Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(CardGreyPlaceholder),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(32.dp)) {
            val w = size.width
            val h = size.height
            val color = Color(0xFF757575)

            // Briefcase / job post outline
            drawRoundRect(
                color = color,
                topLeft = Offset(w * 0.2f, h * 0.35f),
                size = Size(w * 0.6f, h * 0.45f),
                cornerRadius = CornerRadius(4.dp.toPx()),
                style = Stroke(width = 2.5.dp.toPx())
            )
            val handlePath = Path().apply {
                moveTo(w * 0.38f, h * 0.35f)
                lineTo(w * 0.38f, h * 0.22f)
                lineTo(w * 0.62f, h * 0.22f)
                lineTo(w * 0.62f, h * 0.35f)
            }
            drawPath(
                path = handlePath,
                color = color,
                style = Stroke(width = 2.5.dp.toPx(), cap = StrokeCap.Round)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AccountTypeScreenPreview() {
    HandyHubTheme {
        AccountTypeScreen()
    }
}

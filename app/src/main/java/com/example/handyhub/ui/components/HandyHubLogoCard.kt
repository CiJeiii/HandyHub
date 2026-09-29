package com.example.handyhub.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.handyhub.ui.theme.LogoCardBg
import com.example.handyhub.ui.theme.MaroonPrimary

@Composable
fun HandyHubLogoCard(
    modifier: Modifier = Modifier,
    cardSize: Dp = 150.dp,
    logoColor: Color = MaroonPrimary,
    backgroundColor: Color = LogoCardBg
) {
    Box(
        modifier = modifier
            .size(cardSize)
            .clip(RoundedCornerShape(28.dp))
            .background(backgroundColor)
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Canvas(modifier = Modifier.size(72.dp)) {
                val w = size.width
                val h = size.height
                val strokePx = 3.5.dp.toPx()

                // 1. House Outline
                val housePath = Path().apply {
                    // Start at left roof overhang
                    moveTo(w * 0.18f, h * 0.44f)
                    // Peak
                    lineTo(w * 0.48f, h * 0.18f)
                    // Right roof overhang
                    lineTo(w * 0.78f, h * 0.44f)

                    // Right wall
                    moveTo(w * 0.70f, h * 0.44f)
                    lineTo(w * 0.70f, h * 0.82f)
                    // Bottom wall
                    lineTo(w * 0.26f, h * 0.82f)
                    // Left wall
                    lineTo(w * 0.26f, h * 0.44f)
                }

                drawPath(
                    path = housePath,
                    color = logoColor,
                    style = Stroke(width = strokePx, cap = StrokeCap.Round, join = StrokeJoin.Round)
                )

                // 2. Wrench on roof right side
                val wrenchPath = Path().apply {
                    moveTo(w * 0.60f, h * 0.16f)
                    lineTo(w * 0.86f, h * 0.38f)
                }
                drawPath(
                    path = wrenchPath,
                    color = logoColor,
                    style = Stroke(width = strokePx + 1f, cap = StrokeCap.Round)
                )

                // Wrench open jaws at top left of wrench
                val wrenchHeadPath = Path().apply {
                    moveTo(w * 0.55f, h * 0.18f)
                    lineTo(w * 0.60f, h * 0.12f)
                    lineTo(w * 0.68f, h * 0.18f)
                }
                drawPath(
                    path = wrenchHeadPath,
                    color = logoColor,
                    style = Stroke(width = strokePx, cap = StrokeCap.Round, join = StrokeJoin.Round)
                )

                // 3. 4-pane Window grid inside upper house
                val windowSize = w * 0.16f
                val windowLeft = w * 0.48f - windowSize / 2f
                val windowTop = h * 0.30f
                drawRect(
                    color = logoColor,
                    topLeft = Offset(windowLeft, windowTop),
                    size = Size(windowSize, windowSize),
                    style = Stroke(width = 2.dp.toPx())
                )
                // Window cross lines
                drawLine(
                    color = logoColor,
                    start = Offset(windowLeft + windowSize / 2f, windowTop),
                    end = Offset(windowLeft + windowSize / 2f, windowTop + windowSize),
                    strokeWidth = 1.5.dp.toPx()
                )
                drawLine(
                    color = logoColor,
                    start = Offset(windowLeft, windowTop + windowSize / 2f),
                    end = Offset(windowLeft + windowSize, windowTop + windowSize / 2f),
                    strokeWidth = 1.5.dp.toPx()
                )

                // 4. Checkmark inside lower house
                val checkPath = Path().apply {
                    moveTo(w * 0.35f, h * 0.60f)
                    lineTo(w * 0.46f, h * 0.72f)
                    lineTo(w * 0.62f, h * 0.52f)
                }
                drawPath(
                    path = checkPath,
                    color = logoColor,
                    style = Stroke(width = strokePx + 1f, cap = StrokeCap.Round, join = StrokeJoin.Round)
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "HandyHub",
                color = logoColor,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

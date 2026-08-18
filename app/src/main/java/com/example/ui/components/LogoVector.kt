package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.DarkBlue
import com.example.ui.theme.PrimaryBlue
import com.example.ui.theme.PrimaryGreen
import com.example.ui.theme.SecondaryGreen

/**
 * Student 1: Custom Vector Logo for SymbioLink
 * Represents two interconnected partnership links (Blue) with a green sustainability sprout leaf (SDG 17).
 */
@Composable
fun SymbioLinkLogo(
    modifier: Modifier = Modifier,
    size: Dp = 64.dp,
    showText: Boolean = false,
    textColor: Color = Color.Unspecified
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(size)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFFE3F2FD), Color(0xFFE8F5E9))
                    ),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.size(size * 0.65f)) {
                val w = this.size.width
                val h = this.size.height
                val strokeWidth = w * 0.12f

                // Left loop (Blue - Business)
                drawOval(
                    color = PrimaryBlue,
                    topLeft = Offset(0f, h * 0.2f),
                    size = Size(w * 0.58f, h * 0.6f),
                    style = Stroke(width = strokeWidth)
                )

                // Right loop (Deep Blue - Technology/Trust)
                drawOval(
                    color = DarkBlue,
                    topLeft = Offset(w * 0.42f, h * 0.2f),
                    size = Size(w * 0.58f, h * 0.6f),
                    style = Stroke(width = strokeWidth)
                )

                // Central sprout leaf (Green - Growth & SDG 17)
                val leafPath = Path().apply {
                    moveTo(w * 0.5f, h * 0.5f)
                    cubicTo(w * 0.55f, h * 0.28f, w * 0.72f, h * 0.18f, w * 0.75f, h * 0.08f)
                    cubicTo(w * 0.62f, h * 0.12f, w * 0.48f, h * 0.28f, w * 0.5f, h * 0.5f)
                    close()
                }
                drawPath(path = leafPath, color = PrimaryGreen, style = Fill)

                // Small secondary green leaf node
                val smallLeaf = Path().apply {
                    moveTo(w * 0.5f, h * 0.4f)
                    cubicTo(w * 0.42f, h * 0.32f, w * 0.32f, h * 0.28f, w * 0.28f, h * 0.22f)
                    cubicTo(w * 0.36f, h * 0.24f, w * 0.46f, h * 0.34f, w * 0.5f, h * 0.4f)
                    close()
                }
                drawPath(path = smallLeaf, color = SecondaryGreen, style = Fill)
            }
        }

        if (showText) {
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "SymbioLink",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = (size.value * 0.38f).sp,
                    color = if (textColor != Color.Unspecified) textColor else PrimaryBlue
                )
            )
        }
    }
}

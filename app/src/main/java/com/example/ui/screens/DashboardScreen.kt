package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.AddBusiness
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AppState
import com.example.ui.components.OpportunityCard
import com.example.ui.components.StatCard
import com.example.ui.theme.AppBackground
import com.example.ui.theme.MainText
import com.example.ui.theme.PrimaryBlue
import com.example.ui.theme.PrimaryGreen
import com.example.ui.theme.SecondaryText

/**
 * Student 2: Dashboard Screen
 * Main overview displaying partnership score, quick action cards, and activity metrics.
 * Refined with a clear Quick Actions heading and optimized card spacing for better UX.
 */
@Composable
fun DashboardScreen(
    onNavigateToMarketplace: () -> Unit,
    onNavigateToCreateRequirement: () -> Unit,
    onNavigateToCreateServiceOffer: () -> Unit,
    onNavigateToMyProposals: () -> Unit,
    onNavigateToRequirementDetails: (String) -> Unit,
    onNavigateToSubmitProposal: (String) -> Unit
) {
    val user = AppState.currentUser
    val requirements = AppState.requirements
    val proposals = AppState.proposals

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
            .testTag("dashboard_screen"),
        contentPadding = PaddingValues(bottom = 100.dp)
    ) {
        // Dashboard Welcome Header Section
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF0D47A1), // Deep Blue
                                Color(0xFF1565C0), // Primary Blue
                                Color(0xFF1E6E38)  // Forest Green
                            )
                        ),
                        shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
                    )
                    .padding(horizontal = 24.dp, vertical = 32.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Good Morning,",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = Color(0xFFE3F2FD),
                                    fontSize = 14.sp
                                )
                            )
                            Text(
                                text = user.fullName,
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    fontSize = 26.sp
                                )
                            )
                        }

                        // User Avatar
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .background(Color.White.copy(alpha = 0.2f), shape = CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = user.fullName.take(2).uppercase(),
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Business Info Tag
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = user.businessName,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Color(0xFFBBDEFB),
                                fontWeight = FontWeight.Medium
                            )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .background(Color(0xFF2E7D32), shape = RoundedCornerShape(6.dp))
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = user.accountType.displayName,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(28.dp))

                    // Partnership Score Section
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(42.dp)
                                            .background(Color(0xFFE8F5E9), shape = CircleShape),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Handshake,
                                            contentDescription = null,
                                            tint = PrimaryGreen,
                                            modifier = Modifier.size(24.dp)
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column {
                                        Text(
                                            text = "Partnership Score",
                                            style = MaterialTheme.typography.titleMedium.copy(
                                                fontWeight = FontWeight.Bold,
                                                color = MainText
                                            )
                                        )
                                        Text(
                                            text = "Ready for SDG 17 partnerships",
                                            style = MaterialTheme.typography.bodySmall.copy(color = SecondaryText)
                                        )
                                    }
                                }
                                Text(
                                    text = "${user.partnershipScore}%",
                                    style = MaterialTheme.typography.headlineSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = PrimaryGreen
                                    )
                                )
                            }
                            Spacer(modifier = Modifier.height(14.dp))
                            LinearProgressIndicator(
                                progress = { user.partnershipScore / 100f },
                                modifier = Modifier.fillMaxWidth().height(8.dp),
                                color = PrimaryGreen,
                                trackColor = Color(0xFFDCFCE7),
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "Your profile is optimized for new collaborations",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = PrimaryBlue,
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        }
                    }
                }
            }
        }

        // Quick Actions Section
        item {
            Spacer(modifier = Modifier.height(40.dp))
            Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.GridView,
                        contentDescription = null,
                        tint = PrimaryBlue,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Quick Actions",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = MainText,
                            fontSize = 20.sp
                        )
                    )
                }
                
                Spacer(modifier = Modifier.height(20.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    QuickActionCard(
                        title = "Browse\nOpportunities",
                        icon = Icons.Default.Search,
                        iconBg = Color(0xFFE3F2FD),
                        iconTint = PrimaryBlue,
                        modifier = Modifier.weight(1f),
                        onClick = onNavigateToMarketplace
                    )
                    QuickActionCard(
                        title = "Create\nRequirement",
                        icon = Icons.Default.AddBusiness,
                        iconBg = Color(0xFFE8F5E9),
                        iconTint = PrimaryGreen,
                        modifier = Modifier.weight(1f),
                        onClick = onNavigateToCreateRequirement
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    QuickActionCard(
                        title = "Create\nService Offer",
                        icon = Icons.Default.Work,
                        iconBg = Color(0xFFFFF3E0),
                        iconTint = Color(0xFFE65100),
                        modifier = Modifier.weight(1f),
                        onClick = onNavigateToCreateServiceOffer
                    )
                    QuickActionCard(
                        title = "My\nProposals",
                        icon = Icons.Default.Description,
                        iconBg = Color(0xFFF3E5F5),
                        iconTint = Color(0xFF7B1FA2),
                        modifier = Modifier.weight(1f),
                        onClick = onNavigateToMyProposals
                    )
                }
            }
        }

        // Stats Overview Section
        item {
            Spacer(modifier = Modifier.height(36.dp))
            Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                Text(
                    text = "Activity Overview",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = MainText,
                        fontSize = 20.sp
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    StatCard(
                        title = "Active Requirements",
                        value = "${user.activeRequirementsCount}",
                        icon = Icons.Default.Assignment,
                        iconColor = PrimaryBlue,
                        iconBgColor = Color(0xFFE3F2FD),
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        title = "Service Offers",
                        value = "${user.serviceOffersCount}",
                        icon = Icons.Default.Work,
                        iconColor = PrimaryGreen,
                        iconBgColor = Color(0xFFE8F5E9),
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    StatCard(
                        title = "Submitted Proposals",
                        value = "${proposals.size}",
                        icon = Icons.Default.Description,
                        iconColor = Color(0xFFE65100),
                        iconBgColor = Color(0xFFFFF3E0),
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        title = "Partnerships",
                        value = "${user.partnershipsCount}",
                        icon = Icons.Default.Handshake,
                        iconColor = Color(0xFF2E7D32),
                        iconBgColor = Color(0xFFE8F5E9),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // Recent Opportunities Heading
        item {
            Spacer(modifier = Modifier.height(40.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.TrendingUp,
                            contentDescription = null,
                            tint = PrimaryBlue,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Recent Opportunities",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = MainText,
                                fontSize = 20.sp
                            )
                        )
                    }
                    Text(
                        text = "Tailored matches for your business profile",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = SecondaryText
                        )
                    )
                }

                Text(
                    text = "View All",
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = PrimaryBlue,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .clickable { onNavigateToMarketplace() }
                        .padding(vertical = 4.dp)
                        .testTag("dashboard_view_all_opportunities")
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Opportunity List
        items(requirements.take(3)) { requirement ->
            Box(modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)) {
                OpportunityCard(
                    requirement = requirement,
                    onClick = { onNavigateToRequirementDetails(requirement.id) },
                    onApplyClick = { onNavigateToSubmitProposal(requirement.id) },
                    onToggleSave = { AppState.toggleSaveRequirement(requirement.id) }
                )
            }
        }
    }
}

@Composable
private fun QuickActionCard(
    title: String,
    icon: ImageVector,
    iconBg: Color,
    iconTint: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .background(iconBg, shape = RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(22.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    lineHeight = 18.sp,
                    color = MainText
                )
            )
        }
    }
}

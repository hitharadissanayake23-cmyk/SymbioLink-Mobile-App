package com.example.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.navigation.BottomNavItem
import com.example.ui.theme.PrimaryBlue
import com.example.ui.theme.SecondaryText

/**
 * Student 1: Main Container Screen with 5-Tab Navigation Bar
 * Houses Dashboard, Marketplace, Requirements, Analytics, and Profile tabs.
 */
@Composable
fun MainContainerScreen(
    onNavigateToCreateRequirement: () -> Unit,
    onNavigateToCreateServiceOffer: () -> Unit,
    onNavigateToRequirementDetails: (String) -> Unit,
    onNavigateToSubmitProposal: (String) -> Unit,
    onNavigateToMyProposals: () -> Unit,
    onNavigateToResourceHub: () -> Unit,
    onNavigateToEditProfile: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onLogout: () -> Unit
) {
    var currentTab by remember { mutableStateOf(BottomNavItem.HOME) }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 8.dp,
                modifier = Modifier.testTag("bottom_navigation_bar")
            ) {
                BottomNavItem.values().forEach { item ->
                    val selected = currentTab == item
                    NavigationBarItem(
                        selected = selected,
                        onClick = { currentTab = item },
                        icon = {
                            Icon(
                                imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
                                contentDescription = item.title
                            )
                        },
                        label = {
                            Text(
                                text = item.title,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
                                    fontSize = 11.sp
                                )
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = PrimaryBlue,
                            selectedTextColor = PrimaryBlue,
                            unselectedIconColor = SecondaryText,
                            unselectedTextColor = SecondaryText,
                            indicatorColor = PrimaryBlue.copy(alpha = 0.12f)
                        ),
                        modifier = Modifier.testTag("nav_item_${item.route}")
                    )
                }
            }
        }
    ) { paddingValues ->
        // Direct rendering based on selected bottom tab
        androidx.compose.foundation.layout.Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = paddingValues.calculateBottomPadding())
        ) {
            when (currentTab) {
                BottomNavItem.HOME -> {
                    DashboardScreen(
                        onNavigateToMarketplace = { currentTab = BottomNavItem.MARKETPLACE },
                        onNavigateToCreateRequirement = onNavigateToCreateRequirement,
                        onNavigateToCreateServiceOffer = onNavigateToCreateServiceOffer,
                        onNavigateToMyProposals = onNavigateToMyProposals,
                        onNavigateToRequirementDetails = onNavigateToRequirementDetails,
                        onNavigateToSubmitProposal = onNavigateToSubmitProposal
                    )
                }
                BottomNavItem.MARKETPLACE -> {
                    MarketplaceScreen(
                        onNavigateToRequirementDetails = onNavigateToRequirementDetails,
                        onNavigateToSubmitProposal = onNavigateToSubmitProposal,
                        onNavigateToCreateRequirement = onNavigateToCreateRequirement,
                        onNavigateToCreateServiceOffer = onNavigateToCreateServiceOffer
                    )
                }
                BottomNavItem.REQUIREMENTS -> {
                    MyRequirementsScreen(
                        onNavigateToCreateRequirement = onNavigateToCreateRequirement,
                        onNavigateToRequirementDetails = onNavigateToRequirementDetails
                    )
                }
                BottomNavItem.ANALYTICS -> {
                    AnalyticsScreen()
                }
                BottomNavItem.PROFILE -> {
                    ProfileScreen(
                        onNavigateToEditProfile = onNavigateToEditProfile,
                        onNavigateToMyProposals = onNavigateToMyProposals,
                        onNavigateToMyRequirements = { currentTab = BottomNavItem.REQUIREMENTS },
                        onNavigateToResourceHub = onNavigateToResourceHub,
                        onNavigateToSettings = onNavigateToSettings,
                        onLogout = onLogout
                    )
                }
            }
        }
    }
}

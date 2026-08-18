package com.example.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material.icons.outlined.Assignment
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Storefront
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Student 1: Navigation Destinations for SymbioLink
 */
sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Register : Screen("register")
    object Main : Screen("main")
    
    // Sub-screens & CUJs
    object RequirementDetails : Screen("requirement_details/{requirementId}") {
        fun createRoute(requirementId: String) = "requirement_details/$requirementId"
    }
    object CreateRequirement : Screen("create_requirement")
    object MyRequirements : Screen("my_requirements")
    object CreateServiceOffer : Screen("create_service_offer")
    object SubmitProposal : Screen("submit_proposal/{requirementId}") {
        fun createRoute(requirementId: String) = "submit_proposal/$requirementId"
    }
    object MyProposals : Screen("my_proposals")
    object ResourceHub : Screen("resource_hub")
    object EditProfile : Screen("edit_profile")
    object Settings : Screen("settings")
}

/**
 * 5 Bottom Navigation Tabs as specified in Section 10
 */
enum class BottomNavItem(
    val route: String,
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
) {
    HOME("home_tab", "Home", Icons.Filled.Home, Icons.Outlined.Home),
    MARKETPLACE("marketplace_tab", "Marketplace", Icons.Filled.Storefront, Icons.Outlined.Storefront),
    REQUIREMENTS("requirements_tab", "Requirements", Icons.Filled.Assignment, Icons.Outlined.Assignment),
    ANALYTICS("analytics_tab", "Analytics", Icons.Filled.Analytics, Icons.Outlined.Analytics),
    PROFILE("profile_tab", "Profile", Icons.Filled.Person, Icons.Outlined.Person)
}

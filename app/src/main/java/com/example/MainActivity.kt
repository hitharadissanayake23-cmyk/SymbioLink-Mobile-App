package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.data.AppState
import com.example.navigation.Screen
import com.example.ui.screens.AnalyticsScreen
import com.example.ui.screens.DashboardScreen
import com.example.ui.screens.EditProfileScreen
import com.example.ui.screens.LoginScreen
import com.example.ui.screens.MainContainerScreen
import com.example.ui.screens.MarketplaceScreen
import com.example.ui.screens.MyProposalsScreen
import com.example.ui.screens.MyRequirementsScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.ProposalScreen
import com.example.ui.screens.RegisterScreen
import com.example.ui.screens.RequirementDetailsScreen
import com.example.ui.screens.RequirementScreen
import com.example.ui.screens.ResourceHubScreen
import com.example.ui.screens.ServiceOfferScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.screens.SplashScreen
import com.example.ui.theme.SymbioLinkTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SymbioLinkTheme {
                SymbioLinkApp()
            }
        }
    }
}

/**
 * Main application navigation graph connecting all university project modules.
 */
@Composable
fun SymbioLinkApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
        modifier = Modifier.fillMaxSize()
    ) {
        // 1. Splash Screen
        composable(Screen.Splash.route) {
            SplashScreen(
                onTimeout = {
                    val target = if (AppState.isLoggedIn) Screen.Main.route else Screen.Login.route
                    navController.navigate(target) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        // 2. Authentication: Login
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                }
            )
        }

        // 3. Authentication: Register
        composable(Screen.Register.route) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        // 4. Main 5-Tab Dashboard Container
        composable(Screen.Main.route) {
            MainContainerScreen(
                onNavigateToCreateRequirement = {
                    navController.navigate(Screen.CreateRequirement.route)
                },
                onNavigateToCreateServiceOffer = {
                    navController.navigate(Screen.CreateServiceOffer.route)
                },
                onNavigateToRequirementDetails = { requirementId ->
                    navController.navigate(Screen.RequirementDetails.createRoute(requirementId))
                },
                onNavigateToSubmitProposal = { requirementId ->
                    navController.navigate(Screen.SubmitProposal.createRoute(requirementId))
                },
                onNavigateToMyProposals = {
                    navController.navigate(Screen.MyProposals.route)
                },
                onNavigateToResourceHub = {
                    navController.navigate(Screen.ResourceHub.route)
                },
                onNavigateToEditProfile = {
                    navController.navigate(Screen.EditProfile.route)
                },
                onNavigateToSettings = {
                    navController.navigate(Screen.Settings.route)
                },
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Main.route) { inclusive = true }
                    }
                }
            )
        }

        // 5. Requirement Details Screen
        composable(
            route = Screen.RequirementDetails.route,
            arguments = listOf(navArgument("requirementId") { type = NavType.StringType })
        ) { backStackEntry ->
            val reqId = backStackEntry.arguments?.getString("requirementId") ?: ""
            RequirementDetailsScreen(
                requirementId = reqId,
                onNavigateBack = { navController.popBackStack() },
                onSubmitProposalClick = { id ->
                    navController.navigate(Screen.SubmitProposal.createRoute(id))
                }
            )
        }

        // 6. Submit Proposal Screen
        composable(
            route = Screen.SubmitProposal.route,
            arguments = listOf(navArgument("requirementId") { type = NavType.StringType })
        ) { backStackEntry ->
            val reqId = backStackEntry.arguments?.getString("requirementId") ?: ""
            ProposalScreen(
                requirementId = reqId,
                onNavigateBack = { navController.popBackStack() },
                onProposalSubmitted = { navController.popBackStack() }
            )
        }

        // 7. Create Requirement Screen
        composable(Screen.CreateRequirement.route) {
            RequirementScreen(
                onNavigateBack = { navController.popBackStack() },
                onRequirementPublished = { navController.popBackStack() }
            )
        }

        // 8. My Requirements Screen (Standalone route if opened outside container)
        composable(Screen.MyRequirements.route) {
            MyRequirementsScreen(
                onNavigateToCreateRequirement = {
                    navController.navigate(Screen.CreateRequirement.route)
                },
                onNavigateToRequirementDetails = { requirementId ->
                    navController.navigate(Screen.RequirementDetails.createRoute(requirementId))
                }
            )
        }

        // 9. Create Service Offer Screen
        composable(Screen.CreateServiceOffer.route) {
            ServiceOfferScreen(
                onNavigateBack = { navController.popBackStack() },
                onServiceOfferPublished = { navController.popBackStack() }
            )
        }

        // 10. My Proposals Screen
        composable(Screen.MyProposals.route) {
            MyProposalsScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToRequirement = { requirementId ->
                    navController.navigate(Screen.RequirementDetails.createRoute(requirementId))
                }
            )
        }

        // 11. Resource Hub Screen (UN SDG 17 & Guidance)
        composable(Screen.ResourceHub.route) {
            ResourceHubScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // 12. Edit Profile Screen
        composable(Screen.EditProfile.route) {
            EditProfileScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // 13. Settings Screen
        composable(Screen.Settings.route) {
            SettingsScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}


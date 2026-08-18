package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AppState
import com.example.ui.components.AppTopBar
import com.example.ui.theme.AccentOrange
import com.example.ui.theme.AppBackground
import com.example.ui.theme.CardBackground
import com.example.ui.theme.CardBorder
import com.example.ui.theme.MainText
import com.example.ui.theme.PrimaryBlue
import com.example.ui.theme.PrimaryGreen
import com.example.ui.theme.SecondaryText

/**
 * Student 1: Settings Screen
 * Manages preferences, notifications, theme toggles, and University project credits.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit
) {
    var showProjectCreditsDialog by remember { mutableStateOf(false) }
    var showSdgDialog by remember { mutableStateOf(false) }
    var showPrivacyDialog by remember { mutableStateOf(false) }
    var languageExpanded by remember { mutableStateOf(false) }

    val languages = listOf("English", "Sinhala (සිංහල)", "Tamil (தமிழ்)")

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Settings & About",
                onBackClick = onNavigateBack
            )
        },
        containerColor = AppBackground
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            // Preferences Section
            Text(
                text = "App Preferences",
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = MainText
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, CardBorder, RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = CardBackground)
            ) {
                Column {
                    // Push Notifications
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(PrimaryBlue.copy(alpha = 0.12f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = null,
                                tint = PrimaryBlue,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(14.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Push Notifications",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    color = MainText
                                )
                            )
                            Text(
                                text = "Alerts on proposals, RFPs, and partnerships",
                                style = MaterialTheme.typography.bodySmall.copy(color = SecondaryText)
                            )
                        }
                        Switch(
                            checked = AppState.notificationsEnabled,
                            onCheckedChange = { AppState.notificationsEnabled = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = PrimaryBlue
                            ),
                            modifier = Modifier.testTag("toggle_notifications")
                        )
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .height(1.dp)
                            .background(CardBorder.copy(alpha = 0.5f))
                    )

                    // Dark Mode Toggle
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color(0xFF673AB7).copy(alpha = 0.12f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.DarkMode,
                                contentDescription = null,
                                tint = Color(0xFF673AB7),
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(14.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Dark Mode (Preview)",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    color = MainText
                                )
                            )
                            Text(
                                text = "Optimized contrast for low-light environments",
                                style = MaterialTheme.typography.bodySmall.copy(color = SecondaryText)
                            )
                        }
                        Switch(
                            checked = AppState.darkModeEnabled,
                            onCheckedChange = { AppState.darkModeEnabled = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = PrimaryBlue
                            ),
                            modifier = Modifier.testTag("toggle_dark_mode")
                        )
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .height(1.dp)
                            .background(CardBorder.copy(alpha = 0.5f))
                    )

                    // Language Selector
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(PrimaryGreen.copy(alpha = 0.12f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Language,
                                contentDescription = null,
                                tint = PrimaryGreen,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(14.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Language",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    color = MainText
                                )
                            )
                            Text(
                                text = AppState.selectedLanguage,
                                style = MaterialTheme.typography.bodySmall.copy(color = PrimaryGreen, fontWeight = FontWeight.Bold)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Project & University Credits
            Text(
                text = "About & University Project",
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = MainText
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, CardBorder, RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = CardBackground)
            ) {
                Column {
                    SettingsItemRow(
                        title = "Team & Project Credits",
                        subtitle = "Developed by a 3-Student Engineering Team",
                        icon = Icons.Default.Groups,
                        iconColor = PrimaryBlue,
                        onClick = { showProjectCreditsDialog = true }
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .height(1.dp)
                            .background(CardBorder.copy(alpha = 0.5f))
                    )
                    SettingsItemRow(
                        title = "UN SDG 17 Partnership Framework",
                        subtitle = "Global Goals for Sustainable Development",
                        icon = Icons.Default.Public,
                        iconColor = AccentOrange,
                        onClick = { showSdgDialog = true }
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .height(1.dp)
                            .background(CardBorder.copy(alpha = 0.5f))
                    )
                    SettingsItemRow(
                        title = "Privacy & Data Security",
                        subtitle = "Secure B2B quotation protocols",
                        icon = Icons.Default.Security,
                        iconColor = PrimaryGreen,
                        onClick = { showPrivacyDialog = true }
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // App Version Badge
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "SymbioLink v1.0.0",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = SecondaryText
                    )
                )
                Text(
                    text = "Connecting Businesses. Building Partnerships.",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = SecondaryText.copy(alpha = 0.7f),
                        fontSize = 11.sp
                    )
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    // Credits Dialog
    if (showProjectCreditsDialog) {
        AlertDialog(
            onDismissRequest = { showProjectCreditsDialog = false },
            title = { Text("SymbioLink Development Team", fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    Text(
                        text = "University Project developed by a team of 3 students:",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "• Student 1: Core Navigation, Authentication, Profile Management & SDG Analytics",
                        style = MaterialTheme.typography.bodySmall.copy(color = MainText)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "• Student 2: B2B Marketplace, Requirement Publishing & Service Catalog Architecture",
                        style = MaterialTheme.typography.bodySmall.copy(color = MainText)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "• Student 3: Quotation & Proposal Engine, Submission Workflows & SDG 17 Knowledge Hub",
                        style = MaterialTheme.typography.bodySmall.copy(color = MainText)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Built with Kotlin, Jetpack Compose, Material 3, and Coroutines.",
                        style = MaterialTheme.typography.labelSmall.copy(color = PrimaryBlue, fontWeight = FontWeight.Bold)
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = { showProjectCreditsDialog = false }) {
                    Text("Close", color = PrimaryBlue)
                }
            },
            shape = RoundedCornerShape(18.dp),
            containerColor = Color.White
        )
    }

    // SDG Dialog
    if (showSdgDialog) {
        AlertDialog(
            onDismissRequest = { showSdgDialog = false },
            title = { Text("UN Sustainable Development Goal 17", fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    Text(
                        text = "Goal 17: Partnerships for the Goals",
                        style = MaterialTheme.typography.titleSmall.copy(
                            color = PrimaryGreen,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "SymbioLink is engineered to foster multi-stakeholder partnerships between large commercial enterprises and local micro, small, and medium enterprises (MSMEs). By streamlining procurement, knowledge exchange, and sustainable supply chains, SymbioLink helps advance inclusive economic growth.",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MainText,
                            lineHeight = 20.sp
                        )
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = { showSdgDialog = false }) {
                    Text("Understood", color = PrimaryGreen)
                }
            },
            shape = RoundedCornerShape(18.dp),
            containerColor = Color.White
        )
    }

    // Privacy Dialog
    if (showPrivacyDialog) {
        AlertDialog(
            onDismissRequest = { showPrivacyDialog = false },
            title = { Text("Security & Confidentiality", fontWeight = FontWeight.Bold) },
            text = {
                Text(
                    text = "All submitted commercial quotations, contact phone numbers, and RFP attachments are encrypted and shared strictly between verified business representatives to maintain fair competitive integrity.",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MainText,
                        lineHeight = 20.sp
                    )
                )
            },
            confirmButton = {
                TextButton(onClick = { showPrivacyDialog = false }) {
                    Text("OK", color = PrimaryBlue)
                }
            },
            shape = RoundedCornerShape(18.dp),
            containerColor = Color.White
        )
    }
}

@Composable
fun SettingsItemRow(
    title: String,
    subtitle: String,
    icon: ImageVector,
    iconColor: Color,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(iconColor.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(20.dp)
            )
        }
        Spacer(modifier = Modifier.width(14.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = MainText
                )
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall.copy(color = SecondaryText)
            )
        }
        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = SecondaryText.copy(alpha = 0.6f),
            modifier = Modifier.size(20.dp)
        )
    }
}

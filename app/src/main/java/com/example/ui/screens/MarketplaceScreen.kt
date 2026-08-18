package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AppState
import com.example.model.ServiceOffer
import com.example.ui.components.OpportunityCard
import com.example.ui.theme.AppBackground
import com.example.ui.theme.ChipSelectedBg
import com.example.ui.theme.ChipUnselectedBg
import com.example.ui.theme.MainText
import com.example.ui.theme.PrimaryBlue
import com.example.ui.theme.PrimaryGreen
import com.example.ui.theme.SecondaryText

/**
 * Student 2: Marketplace Screen
 * Allows users to search and discover both Enterprise Requirements and MSME Service Offers.
 */
@Composable
fun MarketplaceScreen(
    onNavigateToRequirementDetails: (String) -> Unit,
    onNavigateToSubmitProposal: (String) -> Unit,
    onNavigateToCreateRequirement: () -> Unit,
    onNavigateToCreateServiceOffer: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedTabIndex by remember { mutableIntStateOf(0) } // 0: Requirements, 1: Services
    var selectedCategory by remember { mutableStateOf("All") }

    val categories = listOf("All", "IT", "Marketing", "Logistics", "Design", "Consulting")

    val requirements = AppState.requirements
    val serviceOffers = AppState.serviceOffers

    val filteredRequirements = requirements.filter { req ->
        val matchesSearch = req.title.contains(searchQuery, ignoreCase = true) ||
                req.companyName.contains(searchQuery, ignoreCase = true) ||
                req.description.contains(searchQuery, ignoreCase = true)
        val matchesCategory = if (selectedCategory == "All") true else {
            req.category.contains(selectedCategory, ignoreCase = true)
        }
        matchesSearch && matchesCategory
    }

    val filteredServices = serviceOffers.filter { srv ->
        val matchesSearch = srv.serviceName.contains(searchQuery, ignoreCase = true) ||
                srv.businessName.contains(searchQuery, ignoreCase = true) ||
                srv.description.contains(searchQuery, ignoreCase = true)
        val matchesCategory = if (selectedCategory == "All") true else {
            srv.category.contains(selectedCategory, ignoreCase = true)
        }
        matchesSearch && matchesCategory
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
            .testTag("marketplace_screen"),
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
        // Header Section
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 20.dp, vertical = 20.dp)
            ) {
                Text(
                    text = "Marketplace",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MainText,
                        fontSize = 24.sp
                    )
                )

                Text(
                    text = "Discover opportunities and business services",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = SecondaryText,
                        fontSize = 14.sp
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Search Bar
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search opportunities or services...") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = PrimaryBlue
                        )
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Clear Search",
                                    tint = SecondaryText
                                )
                            }
                        }
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryBlue,
                        unfocusedContainerColor = AppBackground,
                        focusedContainerColor = AppBackground
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("marketplace_search_bar")
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Filter Chips Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    categories.forEach { category ->
                        val isSelected = selectedCategory == category
                        Box(
                            modifier = Modifier
                                .background(
                                    color = if (isSelected) ChipSelectedBg else ChipUnselectedBg,
                                    shape = RoundedCornerShape(20.dp)
                                )
                                .clickable { selectedCategory = category }
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .testTag("chip_$category")
                        ) {
                            Text(
                                text = category,
                                style = MaterialTheme.typography.labelMedium.copy(
                                    color = if (isSelected) Color.White else MainText,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    fontSize = 13.sp
                                )
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Tabs: Requirements & Services
                TabRow(
                    selectedTabIndex = selectedTabIndex,
                    containerColor = Color.Transparent,
                    contentColor = PrimaryBlue,
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                            color = if (selectedTabIndex == 0) PrimaryBlue else PrimaryGreen,
                            height = 3.dp
                        )
                    }
                ) {
                    Tab(
                        selected = selectedTabIndex == 0,
                        onClick = { selectedTabIndex = 0 },
                        text = {
                            Text(
                                text = "Requirements (${filteredRequirements.size})",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = if (selectedTabIndex == 0) FontWeight.Bold else FontWeight.Normal,
                                    color = if (selectedTabIndex == 0) PrimaryBlue else SecondaryText
                                )
                            )
                        },
                        modifier = Modifier.testTag("tab_requirements")
                    )

                    Tab(
                        selected = selectedTabIndex == 1,
                        onClick = { selectedTabIndex = 1 },
                        text = {
                            Text(
                                text = "Services (${filteredServices.size})",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = if (selectedTabIndex == 1) FontWeight.Bold else FontWeight.Normal,
                                    color = if (selectedTabIndex == 1) PrimaryGreen else SecondaryText
                                )
                            )
                        },
                        modifier = Modifier.testTag("tab_services")
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
        }

        // Requirements Tab Content
        if (selectedTabIndex == 0) {
            if (filteredRequirements.isEmpty()) {
                item {
                    EmptyStateCard(
                        message = "No requirements match your criteria.",
                        buttonText = "Publish a Requirement",
                        onButtonClick = onNavigateToCreateRequirement
                    )
                }
            } else {
                items(filteredRequirements) { requirement ->
                    Box(modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)) {
                        OpportunityCard(
                            requirement = requirement,
                            onClick = { onNavigateToRequirementDetails(requirement.id) },
                            onApplyClick = { onNavigateToSubmitProposal(requirement.id) },
                            onToggleSave = { AppState.toggleSaveRequirement(requirement.id) }
                        )
                    }
                }
            }
        } else {
            // Services Tab Content
            if (filteredServices.isEmpty()) {
                item {
                    EmptyStateCard(
                        message = "No service offers match your criteria.",
                        buttonText = "Publish a Service Offer",
                        onButtonClick = onNavigateToCreateServiceOffer
                    )
                }
            } else {
                items(filteredServices) { service ->
                    Box(modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)) {
                        ServiceOfferCard(
                            service = service,
                            onContactClick = {
                                // Contact action
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ServiceOfferCard(
    service: ServiceOffer,
    onContactClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("service_card_${service.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Category & Rating Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .background(Color(0xFFE8F5E9), shape = RoundedCornerShape(8.dp))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = service.category,
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = PrimaryGreen,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = Color(0xFFFFB300),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${service.rating} (${service.completedProjects} jobs)",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = MainText
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = service.serviceName,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = MainText
                )
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Verified,
                    contentDescription = null,
                    tint = PrimaryBlue,
                    modifier = Modifier.size(15.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = service.businessName,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = SecondaryText,
                        fontWeight = FontWeight.Medium
                    )
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = service.description,
                style = MaterialTheme.typography.bodySmall.copy(color = SecondaryText),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Pricing and Delivery Specs
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Starting at",
                        style = MaterialTheme.typography.bodySmall.copy(color = SecondaryText, fontSize = 11.sp)
                    )
                    Text(
                        text = service.startingPrice,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = PrimaryGreen
                        )
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Schedule,
                        contentDescription = null,
                        tint = SecondaryText,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = service.deliveryTime,
                        style = MaterialTheme.typography.bodySmall.copy(color = SecondaryText)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Pricing Tier Pills
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TierBadge(service.basicTier.name, service.basicTier.price, Modifier.weight(1f))
                TierBadge(service.standardTier.name, service.standardTier.price, Modifier.weight(1f))
                TierBadge(service.premiumTier.name, service.premiumTier.price, Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun TierBadge(tierName: String, price: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(Color(0xFFF8FAFC), shape = RoundedCornerShape(8.dp))
            .padding(vertical = 6.dp, horizontal = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = tierName,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontSize = 10.sp,
                    color = SecondaryText
                )
            )
            Text(
                text = price,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = MainText
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun EmptyStateCard(
    message: String,
    buttonText: String,
    onButtonClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 24.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "No Items Found",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MainText
                )
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium.copy(color = SecondaryText)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onButtonClick,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
            ) {
                Text(buttonText)
            }
        }
    }
}

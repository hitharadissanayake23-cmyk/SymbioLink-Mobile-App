package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.HourglassEmpty
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.RateReview
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AppState
import com.example.model.Proposal
import com.example.model.ProposalStatus
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
 * Student 3: My Proposals Screen
 * Displays submitted quotations/proposals with status filtering and details.
 */
@Composable
fun MyProposalsScreen(
    onNavigateBack: () -> Unit,
    onNavigateToRequirement: (String) -> Unit
) {
    var selectedFilter by remember { mutableStateOf<ProposalStatus?>(null) }
    var selectedProposalForDetails by remember { mutableStateOf<Proposal?>(null) }

    val allProposals = AppState.proposals
    val filteredProposals = remember(selectedFilter, allProposals.size) {
        if (selectedFilter == null) {
            allProposals
        } else {
            allProposals.filter { it.status == selectedFilter }
        }
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "My Submitted Proposals",
                onBackClick = onNavigateBack
            )
        },
        containerColor = AppBackground
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Filter Chips
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    FilterChip(
                        selected = selectedFilter == null,
                        onClick = { selectedFilter = null },
                        label = { Text("All (${allProposals.size})") },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = PrimaryBlue,
                            selectedLabelColor = Color.White
                        )
                    )
                }
                ProposalStatus.values().forEach { status ->
                    val count = allProposals.count { it.status == status }
                    item {
                        FilterChip(
                            selected = selectedFilter == status,
                            onClick = { selectedFilter = if (selectedFilter == status) null else status },
                            label = { Text("${status.label} ($count)") },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = when (status) {
                                    ProposalStatus.ACCEPTED -> PrimaryGreen
                                    ProposalStatus.PENDING -> PrimaryBlue
                                    ProposalStatus.REVIEWED -> AccentOrange
                                    ProposalStatus.REJECTED -> Color(0xFFE53935)
                                },
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }
            }

            if (filteredProposals.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.Description,
                            contentDescription = null,
                            tint = SecondaryText.copy(alpha = 0.5f),
                            modifier = Modifier.size(64.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "No Proposals Found",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MainText
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = if (selectedFilter != null) "No proposals matching status '${selectedFilter?.label}'" else "Explore requirements in the marketplace to submit quotations",
                            style = MaterialTheme.typography.bodyMedium.copy(color = SecondaryText)
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(filteredProposals, key = { it.id }) { proposal ->
                        ProposalCard(
                            proposal = proposal,
                            onClick = { selectedProposalForDetails = proposal }
                        )
                    }
                }
            }
        }
    }

    // Detailed Proposal Dialog
    selectedProposalForDetails?.let { proposal ->
        AlertDialog(
            onDismissRequest = { selectedProposalForDetails = null },
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Proposal Details",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    StatusBadge(status = proposal.status)
                }
            },
            text = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = proposal.requirementTitle,
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = MainText
                        )
                    )
                    Text(
                        text = "Submitted to: ${proposal.enterpriseName}",
                        style = MaterialTheme.typography.bodySmall.copy(color = SecondaryText)
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = "Quotation",
                                style = MaterialTheme.typography.labelSmall.copy(color = SecondaryText)
                            )
                            Text(
                                text = proposal.quotation,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = PrimaryGreen
                                )
                            )
                        }
                        Column {
                            Text(
                                text = "Timeline",
                                style = MaterialTheme.typography.labelSmall.copy(color = SecondaryText)
                            )
                            Text(
                                text = proposal.estimatedTimeline,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MainText
                                )
                            )
                        }
                        Column {
                            Text(
                                text = "Date",
                                style = MaterialTheme.typography.labelSmall.copy(color = SecondaryText)
                            )
                            Text(
                                text = proposal.submittedDate,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Medium,
                                    color = SecondaryText
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Proposal Message:",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MainText
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = proposal.proposalMessage,
                        style = MaterialTheme.typography.bodySmall.copy(color = MainText)
                    )

                    proposal.attachedDocumentName?.let { docName ->
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Description,
                                contentDescription = null,
                                tint = PrimaryBlue,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Attachment: $docName",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = PrimaryBlue,
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val reqId = proposal.requirementId
                        selectedProposalForDetails = null
                        onNavigateToRequirement(reqId)
                    }
                ) {
                    Text("View Requirement", color = PrimaryBlue)
                }
            },
            dismissButton = {
                TextButton(onClick = { selectedProposalForDetails = null }) {
                    Text("Close", color = SecondaryText)
                }
            },
            shape = RoundedCornerShape(18.dp),
            containerColor = Color.White
        )
    }
}

@Composable
fun ProposalCard(
    proposal: Proposal,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, CardBorder, RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .testTag("proposal_card_${proposal.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = proposal.requirementTitle,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MainText
                        ),
                        maxLines = 1
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Business,
                            contentDescription = null,
                            tint = SecondaryText,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = proposal.enterpriseName,
                            style = MaterialTheme.typography.bodySmall.copy(color = SecondaryText)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                StatusBadge(status = proposal.status)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = proposal.proposalMessage,
                style = MaterialTheme.typography.bodySmall.copy(color = SecondaryText),
                maxLines = 2
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.MonetizationOn,
                        contentDescription = null,
                        tint = PrimaryGreen,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = proposal.quotation,
                        style = MaterialTheme.typography.bodyMedium.copy(
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
                        text = proposal.estimatedTimeline,
                        style = MaterialTheme.typography.bodySmall.copy(color = SecondaryText)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = proposal.submittedDate,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = SecondaryText.copy(alpha = 0.7f),
                            fontSize = 11.sp
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun StatusBadge(status: ProposalStatus) {
    val (backgroundColor, textColor, icon) = when (status) {
        ProposalStatus.PENDING -> Triple(
            PrimaryBlue.copy(alpha = 0.12f),
            PrimaryBlue,
            Icons.Default.HourglassEmpty
        )
        ProposalStatus.REVIEWED -> Triple(
            AccentOrange.copy(alpha = 0.15f),
            AccentOrange,
            Icons.Default.RateReview
        )
        ProposalStatus.ACCEPTED -> Triple(
            PrimaryGreen.copy(alpha = 0.15f),
            PrimaryGreen,
            Icons.Default.CheckCircle
        )
        ProposalStatus.REJECTED -> Triple(
            Color(0xFFFFEBEE),
            Color(0xFFD32F2F),
            Icons.Default.Close
        )
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = textColor,
                modifier = Modifier.size(12.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = status.label,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = textColor,
                    fontSize = 11.sp
                )
            )
        }
    }
}

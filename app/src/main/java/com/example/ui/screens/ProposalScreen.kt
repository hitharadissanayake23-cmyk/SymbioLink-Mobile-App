package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AppState
import com.example.model.Proposal
import com.example.model.ProposalStatus
import com.example.ui.components.AppPrimaryButton
import com.example.ui.components.AppTopBar
import com.example.ui.theme.AppBackground
import com.example.ui.theme.CardBackground
import com.example.ui.theme.CardBorder
import com.example.ui.theme.MainText
import com.example.ui.theme.PrimaryBlue
import com.example.ui.theme.PrimaryGreen
import com.example.ui.theme.SecondaryText
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Student 3: Proposal Submission Screen
 * Allows MSMEs to submit formal quotations and partnership proposals for enterprise requirements.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProposalScreen(
    requirementId: String,
    onNavigateBack: () -> Unit,
    onProposalSubmitted: () -> Unit
) {
    val user = AppState.currentUser
    val requirement = AppState.requirements.find { it.id == requirementId }

    var quotation by remember { mutableStateOf("") }
    var timeline by remember { mutableStateOf("") }
    var proposalMessage by remember { mutableStateOf("") }
    var contactName by remember { mutableStateOf(user.fullName) }
    var contactEmail by remember { mutableStateOf(user.email) }
    var contactPhone by remember { mutableStateOf(user.phone) }
    var attachedDocName by remember { mutableStateOf<String?>("${user.businessName}_Proposal.pdf") }

    // Validation errors
    var quotationError by remember { mutableStateOf<String?>(null) }
    var timelineError by remember { mutableStateOf<String?>(null) }
    var proposalMessageError by remember { mutableStateOf<String?>(null) }
    var contactEmailError by remember { mutableStateOf<String?>(null) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Submit Proposal",
                onBackClick = onNavigateBack
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = AppBackground
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .imePadding()
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            // Requirement Brief Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, CardBorder, RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = CardBackground)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Responding To Requirement",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = PrimaryBlue,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = requirement?.title ?: "Enterprise Requirement",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MainText
                        )
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Business,
                            contentDescription = null,
                            tint = SecondaryText,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = requirement?.companyName ?: "Enterprise",
                            style = MaterialTheme.typography.bodySmall.copy(color = SecondaryText)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Icon(
                            imageVector = Icons.Default.MonetizationOn,
                            contentDescription = null,
                            tint = PrimaryGreen,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = requirement?.budget ?: "Budget Stated",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = PrimaryGreen,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Quotation & Timeline",
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = MainText
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Quotation Amount & Timeline in a Row
            Row(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = quotation,
                    onValueChange = {
                        quotation = it
                        if (quotationError != null) quotationError = null
                    },
                    label = { Text("Quotation (LKR) *") },
                    placeholder = { Text("e.g. 240,000") },
                    isError = quotationError != null,
                    supportingText = { quotationError?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryBlue,
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .testTag("proposal_quotation_input")
                )

                Spacer(modifier = Modifier.width(10.dp))

                OutlinedTextField(
                    value = timeline,
                    onValueChange = {
                        timeline = it
                        if (timelineError != null) timelineError = null
                    },
                    label = { Text("Timeline *") },
                    placeholder = { Text("e.g. 3 Weeks") },
                    isError = timelineError != null,
                    supportingText = { timelineError?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryBlue,
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .testTag("proposal_timeline_input")
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Proposal Pitch Message
            OutlinedTextField(
                value = proposalMessage,
                onValueChange = {
                    proposalMessage = it
                    if (proposalMessageError != null) proposalMessageError = null
                },
                label = { Text("Proposal Details & Pitch *") },
                placeholder = { Text("Explain your solution, past track record, methodology, and why your team is the best fit for this project...") },
                isError = proposalMessageError != null,
                supportingText = { proposalMessageError?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
                minLines = 4,
                maxLines = 8,
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PrimaryBlue,
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("proposal_message_input")
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Contact & Representative",
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = MainText
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Contact Name
            OutlinedTextField(
                value = contactName,
                onValueChange = { contactName = it },
                label = { Text("Contact Name *") },
                singleLine = true,
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PrimaryBlue,
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Email and Phone
            Row(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = contactEmail,
                    onValueChange = {
                        contactEmail = it
                        if (contactEmailError != null) contactEmailError = null
                    },
                    label = { Text("Email *") },
                    isError = contactEmailError != null,
                    supportingText = { contactEmailError?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryBlue,
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White
                    ),
                    modifier = Modifier
                        .weight(1.1f)
                        .testTag("proposal_email_input")
                )

                Spacer(modifier = Modifier.width(10.dp))

                OutlinedTextField(
                    value = contactPhone,
                    onValueChange = { contactPhone = it },
                    label = { Text("Phone") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone, imeAction = ImeAction.Done),
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryBlue,
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White
                    ),
                    modifier = Modifier.weight(0.9f)
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Attachment Document Selector
            Text(
                text = "Supporting Documentation (Optional)",
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = MainText
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color.White)
                    .border(1.dp, CardBorder, RoundedCornerShape(14.dp))
                    .clickable {
                        attachedDocName = "${user.businessName}_B2B_Portfolio_Proposal.pdf"
                        scope.launch {
                            snackbarHostState.showSnackbar("Attached: $attachedDocName")
                        }
                    }
                    .padding(14.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = if (attachedDocName != null) Icons.Default.CheckCircle else Icons.Default.AttachFile,
                        contentDescription = null,
                        tint = if (attachedDocName != null) PrimaryGreen else PrimaryBlue,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = attachedDocName ?: "Attach Proposal / Portfolio PDF",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Medium,
                                color = if (attachedDocName != null) MainText else SecondaryText
                            )
                        )
                        Text(
                            text = if (attachedDocName != null) "Ready for submission" else "Tap to attach company capabilities file",
                            style = MaterialTheme.typography.bodySmall.copy(color = SecondaryText)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(26.dp))

            // Submit Button
            AppPrimaryButton(
                text = "Send Proposal",
                onClick = {
                    focusManager.clearFocus()
                    var isValid = true

                    if (quotation.isBlank()) {
                        quotationError = "Quotation amount is required"
                        isValid = false
                    }
                    if (timeline.isBlank()) {
                        timelineError = "Timeline is required"
                        isValid = false
                    }
                    if (proposalMessage.isBlank()) {
                        proposalMessageError = "Proposal details are required"
                        isValid = false
                    }
                    if (contactEmail.isBlank()) {
                        contactEmailError = "Contact email is required"
                        isValid = false
                    }

                    if (isValid) {
                        val formattedQuotation = if (!quotation.startsWith("LKR")) "LKR $quotation" else quotation
                        val newProposal = Proposal(
                            id = "prop_${System.currentTimeMillis()}",
                            requirementId = requirementId,
                            requirementTitle = requirement?.title ?: "Enterprise Requirement",
                            enterpriseName = requirement?.companyName ?: "Enterprise Partner",
                            quotation = formattedQuotation,
                            estimatedTimeline = timeline,
                            proposalMessage = proposalMessage,
                            contactName = contactName,
                            contactEmail = contactEmail,
                            contactPhone = contactPhone,
                            attachedDocumentName = attachedDocName,
                            submittedDate = "18 Aug 2026",
                            status = ProposalStatus.PENDING
                        )
                        AppState.addProposal(newProposal)
                        scope.launch {
                            snackbarHostState.showSnackbar("Proposal submitted successfully to ${requirement?.companyName ?: "enterprise"}")
                            delay(500)
                            onProposalSubmitted()
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                testTag = "submit_proposal_btn"
            )

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

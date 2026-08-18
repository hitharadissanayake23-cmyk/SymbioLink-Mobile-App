package com.example.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AppState
import com.example.model.Requirement
import com.example.model.RequirementStatus
import com.example.ui.components.AppPrimaryButton
import com.example.ui.components.AppTopBar
import com.example.ui.theme.AppBackground
import com.example.ui.theme.MainText
import com.example.ui.theme.PrimaryBlue
import com.example.ui.theme.SecondaryText
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Student 2: Create Requirement Screen
 * Allows enterprise users to publish service requirements.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequirementScreen(
    onNavigateBack: () -> Unit,
    onRequirementPublished: () -> Unit
) {
    val user = AppState.currentUser

    var title by remember { mutableStateOf("") }
    var companyName by remember { mutableStateOf(user.businessName) }
    var category by remember { mutableStateOf("IT & Software") }
    var description by remember { mutableStateOf("") }
    var budget by remember { mutableStateOf("") }
    var volume by remember { mutableStateOf("") }
    var deadline by remember { mutableStateOf("") }
    var location by remember { mutableStateOf(user.location) }
    var contactEmail by remember { mutableStateOf(user.email) }

    var categoryExpanded by remember { mutableStateOf(false) }

    // Validation errors
    var titleError by remember { mutableStateOf<String?>(null) }
    var descriptionError by remember { mutableStateOf<String?>(null) }
    var budgetError by remember { mutableStateOf<String?>(null) }
    var deadlineError by remember { mutableStateOf<String?>(null) }
    var contactEmailError by remember { mutableStateOf<String?>(null) }

    val categories = listOf(
        "IT & Software",
        "Marketing",
        "Logistics",
        "Manufacturing",
        "Consulting",
        "Design",
        "Accounting",
        "Construction",
        "Other"
    )

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Create Requirement",
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
            Text(
                text = "Publish your business requirement to receive competitive proposals from verified MSMEs.",
                style = MaterialTheme.typography.bodyMedium.copy(color = SecondaryText)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Requirement Title
            OutlinedTextField(
                value = title,
                onValueChange = {
                    title = it
                    if (titleError != null) titleError = null
                },
                label = { Text("Requirement Title *") },
                placeholder = { Text("e.g. Corporate Website Development") },
                isError = titleError != null,
                supportingText = { titleError?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
                singleLine = true,
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PrimaryBlue,
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("requirement_title_input")
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Company Name
            OutlinedTextField(
                value = companyName,
                onValueChange = { companyName = it },
                label = { Text("Company Name *") },
                singleLine = true,
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PrimaryBlue,
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Category Dropdown
            ExposedDropdownMenuBox(
                expanded = categoryExpanded,
                onExpandedChange = { categoryExpanded = !categoryExpanded },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = category,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Category *") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoryExpanded) },
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryBlue,
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White
                    ),
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = categoryExpanded,
                    onDismissRequest = { categoryExpanded = false }
                ) {
                    categories.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(item) },
                            onClick = {
                                category = item
                                categoryExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Description
            OutlinedTextField(
                value = description,
                onValueChange = {
                    description = it
                    if (descriptionError != null) descriptionError = null
                },
                label = { Text("Description & Requirements *") },
                placeholder = { Text("Describe the project scope, technical expectations, and deliverable milestones...") },
                isError = descriptionError != null,
                supportingText = { descriptionError?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
                minLines = 3,
                maxLines = 6,
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PrimaryBlue,
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("requirement_desc_input")
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Budget & Volume in a Row
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = budget,
                    onValueChange = {
                        budget = it
                        if (budgetError != null) budgetError = null
                    },
                    label = { Text("Budget (LKR) *") },
                    placeholder = { Text("e.g. LKR 250,000") },
                    isError = budgetError != null,
                    supportingText = { budgetError?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryBlue,
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .testTag("requirement_budget_input")
                )

                Spacer(modifier = Modifier.width(8.dp))

                OutlinedTextField(
                    value = volume,
                    onValueChange = { volume = it },
                    label = { Text("Volume / Qty") },
                    placeholder = { Text("e.g. 1 Project") },
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryBlue,
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White
                    ),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Deadline & Location in a Row
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = deadline,
                    onValueChange = {
                        deadline = it
                        if (deadlineError != null) deadlineError = null
                    },
                    label = { Text("Deadline *") },
                    placeholder = { Text("e.g. 30 Sep 2026") },
                    isError = deadlineError != null,
                    supportingText = { deadlineError?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryBlue,
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .testTag("requirement_deadline_input")
                )

                Spacer(modifier = Modifier.width(8.dp))

                OutlinedTextField(
                    value = location,
                    onValueChange = { location = it },
                    label = { Text("Location") },
                    placeholder = { Text("e.g. Colombo") },
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryBlue,
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White
                    ),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Contact Email
            OutlinedTextField(
                value = contactEmail,
                onValueChange = {
                    contactEmail = it
                    if (contactEmailError != null) contactEmailError = null
                },
                label = { Text("Contact Email *") },
                placeholder = { Text("procurement@company.lk") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Done),
                isError = contactEmailError != null,
                supportingText = { contactEmailError?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
                singleLine = true,
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PrimaryBlue,
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("requirement_email_input")
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Publish Button
            AppPrimaryButton(
                text = "Publish Requirement",
                onClick = {
                    focusManager.clearFocus()
                    var isValid = true

                    if (title.isBlank()) {
                        titleError = "Title is required"
                        isValid = false
                    }
                    if (description.isBlank()) {
                        descriptionError = "Description is required"
                        isValid = false
                    }
                    if (budget.isBlank()) {
                        budgetError = "Budget is required"
                        isValid = false
                    }
                    if (deadline.isBlank()) {
                        deadlineError = "Deadline date is required"
                        isValid = false
                    }
                    if (contactEmail.isBlank()) {
                        contactEmailError = "Contact email is required"
                        isValid = false
                    }

                    if (isValid) {
                        val formattedBudget = if (!budget.startsWith("LKR")) "LKR $budget" else budget
                        val newReq = Requirement(
                            id = "req_${System.currentTimeMillis()}",
                            title = title,
                            companyName = if (companyName.isNotBlank()) companyName else user.businessName,
                            category = category,
                            description = description,
                            budget = formattedBudget,
                            volume = if (volume.isNotBlank()) volume else "1 Project",
                            deadline = deadline,
                            location = if (location.isNotBlank()) location else "Colombo, Sri Lanka",
                            contactEmail = contactEmail,
                            postedDate = "18 Aug 2026",
                            proposalsCount = 0,
                            status = RequirementStatus.ACTIVE
                        )
                        AppState.addRequirement(newReq)
                        scope.launch {
                            snackbarHostState.showSnackbar("Requirement published successfully")
                            delay(500)
                            onRequirementPublished()
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                testTag = "publish_requirement_btn"
            )

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AppState
import com.example.model.ServiceOffer
import com.example.model.ServiceTier
import com.example.ui.components.AppPrimaryButton
import com.example.ui.components.AppTopBar
import com.example.ui.theme.AppBackground
import com.example.ui.theme.MainText
import com.example.ui.theme.PrimaryBlue
import com.example.ui.theme.PrimaryGreen
import com.example.ui.theme.SecondaryText
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Student 2: Service Offer Screen
 * Allows MSMEs and Service Providers to publish their business services with 3 pricing tiers.
 * Updated with helper text and improved spacing for better readability.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceOfferScreen(
    onNavigateBack: () -> Unit,
    onServiceOfferPublished: () -> Unit
) {
    val user = AppState.currentUser

    // Form data states
    var serviceName by remember { mutableStateOf("") }
    var businessName by remember { mutableStateOf(user.businessName) }
    var category by remember { mutableStateOf("IT & Software") }
    var description by remember { mutableStateOf("") }
    var startingPrice by remember { mutableStateOf("") }
    var deliveryTime by remember { mutableStateOf("") }
    var experience by remember { mutableStateOf("3+ Years") }
    var contactEmail by remember { mutableStateOf(user.email) }

    // Service Tiers states
    var basicPrice by remember { mutableStateOf("") }
    var basicDesc by remember { mutableStateOf("") }
    var standardPrice by remember { mutableStateOf("") }
    var standardDesc by remember { mutableStateOf("") }
    var premiumPrice by remember { mutableStateOf("") }
    var premiumDesc by remember { mutableStateOf("") }

    var categoryExpanded by remember { mutableStateOf(false) }

    // Validation states
    var serviceNameError by remember { mutableStateOf<String?>(null) }
    var descriptionError by remember { mutableStateOf<String?>(null) }
    var startingPriceError by remember { mutableStateOf<String?>(null) }
    var deliveryTimeError by remember { mutableStateOf<String?>(null) }
    var contactEmailError by remember { mutableStateOf<String?>(null) }

    val categories = listOf(
        "IT & Software", "Design", "Marketing", "Logistics",
        "Consulting", "Accounting", "Manufacturing", "Other"
    )

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Create Service Offer",
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
                .padding(horizontal = 24.dp, vertical = 20.dp)
        ) {
            // Screen Header
            Text(
                text = "Publish Your Business Service",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = MainText
                )
            )
            
            // New Helper Text
            Text(
                text = "Describe the service your business can provide",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = SecondaryText,
                    fontSize = 14.sp
                )
            )

            Spacer(modifier = Modifier.height(28.dp))

            // Service Details Group
            OutlinedTextField(
                value = serviceName,
                onValueChange = {
                    serviceName = it
                    if (serviceNameError != null) serviceNameError = null
                },
                label = { Text("Service Name *") },
                placeholder = { Text("e.g. Graphic Design Services") },
                isError = serviceNameError != null,
                supportingText = { serviceNameError?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PrimaryBlue,
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White
                ),
                modifier = Modifier.fillMaxWidth().testTag("service_name_input")
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = businessName,
                onValueChange = { businessName = it },
                label = { Text("Business Name *") },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PrimaryBlue,
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

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
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryBlue,
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White
                    ),
                    modifier = Modifier.menuAnchor().fillMaxWidth()
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

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = description,
                onValueChange = {
                    description = it
                    if (descriptionError != null) descriptionError = null
                },
                label = { Text("Description *") },
                placeholder = { Text("Provide details about what you offer...") },
                isError = descriptionError != null,
                supportingText = { descriptionError?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
                minLines = 3,
                maxLines = 6,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PrimaryBlue,
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White
                ),
                modifier = Modifier.fillMaxWidth().testTag("service_desc_input")
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Pricing Group
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = startingPrice,
                    onValueChange = {
                        startingPrice = it
                        if (startingPriceError != null) startingPriceError = null
                    },
                    label = { Text("Starting Price *") },
                    placeholder = { Text("LKR") },
                    isError = startingPriceError != null,
                    supportingText = { startingPriceError?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryBlue,
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White
                    ),
                    modifier = Modifier.weight(1f).testTag("service_price_input")
                )

                OutlinedTextField(
                    value = deliveryTime,
                    onValueChange = {
                        deliveryTime = it
                        if (deliveryTimeError != null) deliveryTimeError = null
                    },
                    label = { Text("Delivery Time *") },
                    placeholder = { Text("Days") },
                    isError = deliveryTimeError != null,
                    supportingText = { deliveryTimeError?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryBlue,
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White
                    ),
                    modifier = Modifier.weight(1f).testTag("service_delivery_input")
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Contact Info Group
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = experience,
                    onValueChange = { experience = it },
                    label = { Text("Experience") },
                    placeholder = { Text("e.g. 5 Years") },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryBlue,
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White
                    ),
                    modifier = Modifier.weight(1f)
                )

                OutlinedTextField(
                    value = contactEmail,
                    onValueChange = {
                        contactEmail = it
                        if (contactEmailError != null) contactEmailError = null
                    },
                    label = { Text("Contact Email *") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Done),
                    isError = contactEmailError != null,
                    supportingText = { contactEmailError?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryBlue,
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White
                    ),
                    modifier = Modifier.weight(1.5f)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Pricing Tiers Group
            Text(
                text = "Pricing Tiers",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MainText
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            TierInputCard(
                tierName = "Basic Tier",
                price = basicPrice,
                onPriceChange = { basicPrice = it },
                description = basicDesc,
                onDescChange = { basicDesc = it },
                defaultPricePlaceholder = "Basic Price"
            )

            Spacer(modifier = Modifier.height(12.dp))

            TierInputCard(
                tierName = "Standard Tier",
                price = standardPrice,
                onPriceChange = { standardPrice = it },
                description = standardDesc,
                onDescChange = { standardDesc = it },
                defaultPricePlaceholder = "Standard Price"
            )

            Spacer(modifier = Modifier.height(12.dp))

            TierInputCard(
                tierName = "Premium Tier",
                price = premiumPrice,
                onPriceChange = { premiumPrice = it },
                description = premiumDesc,
                onDescChange = { premiumDesc = it },
                defaultPricePlaceholder = "Premium Price"
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Submit Button
            AppPrimaryButton(
                text = "Publish Service Offer",
                onClick = {
                    focusManager.clearFocus()
                    var isValid = true

                    if (serviceName.isBlank()) {
                        serviceNameError = "Service name is required"
                        isValid = false
                    }
                    if (description.isBlank()) {
                        descriptionError = "Description is required"
                        isValid = false
                    }
                    if (startingPrice.isBlank()) {
                        startingPriceError = "Starting price is required"
                        isValid = false
                    }
                    if (deliveryTime.isBlank()) {
                        deliveryTimeError = "Delivery time is required"
                        isValid = false
                    }
                    if (contactEmail.isBlank()) {
                        contactEmailError = "Contact email is required"
                        isValid = false
                    }

                    if (isValid) {
                        val formattedPrice = if (!startingPrice.startsWith("LKR")) "LKR $startingPrice" else startingPrice
                        val newService = ServiceOffer(
                            id = "srv_${System.currentTimeMillis()}",
                            serviceName = serviceName,
                            businessName = businessName.ifBlank { user.businessName },
                            category = category,
                            description = description,
                            startingPrice = formattedPrice,
                            deliveryTime = deliveryTime,
                            experience = experience.ifBlank { "3+ Years" },
                            contactEmail = contactEmail,
                            basicTier = ServiceTier(
                                "Basic",
                                basicPrice.ifBlank { formattedPrice },
                                basicDesc.ifBlank { "Basic deliverables" }
                            ),
                            standardTier = ServiceTier(
                                "Standard",
                                standardPrice.ifBlank { "LKR 120,000" },
                                standardDesc.ifBlank { "Standard full deliverables" }
                            ),
                            premiumTier = ServiceTier(
                                "Premium",
                                premiumPrice.ifBlank { "LKR 250,000" },
                                premiumDesc.ifBlank { "Full enterprise support" }
                            )
                        )
                        AppState.addServiceOffer(newService)
                        scope.launch {
                            snackbarHostState.showSnackbar("Service offer published successfully")
                            delay(500)
                            onServiceOfferPublished()
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                testTag = "publish_service_btn"
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun TierInputCard(
    tierName: String,
    price: String,
    onPriceChange: (String) -> Unit,
    description: String,
    onDescChange: (String) -> Unit,
    defaultPricePlaceholder: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = tierName,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = PrimaryBlue
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedTextField(
                    value = price,
                    onValueChange = onPriceChange,
                    label = { Text("Price") },
                    placeholder = { Text(defaultPricePlaceholder) },
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.weight(1f)
                )

                OutlinedTextField(
                    value = description,
                    onValueChange = onDescChange,
                    label = { Text("Details") },
                    placeholder = { Text("Package details...") },
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.weight(1.5f)
                )
            }
        }
    }
}

package com.example.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.model.AccountType
import com.example.model.Proposal
import com.example.model.ProposalStatus
import com.example.model.Requirement
import com.example.model.Resource
import com.example.model.ServiceOffer
import com.example.model.User

/**
 * Shared App State Manager (Student 1, 2, & 3)
 * Holds live observable data that updates reactively across all screens.
 */
object AppState {

    // Current User profile state
    var currentUser by mutableStateOf(SampleData.currentUser)

    // User authentication status
    var isLoggedIn by mutableStateOf(false)
    var rememberMe by mutableStateOf(true)

    // Reactive lists
    val requirements = mutableStateListOf<Requirement>().apply {
        addAll(SampleData.sampleRequirements)
    }

    val serviceOffers = mutableStateListOf<ServiceOffer>().apply {
        addAll(SampleData.sampleServiceOffers)
    }

    val proposals = mutableStateListOf<Proposal>().apply {
        addAll(SampleData.sampleProposals)
    }

    val resources = mutableStateListOf<Resource>().apply {
        addAll(SampleData.sampleResources)
    }

    // App Preferences
    var notificationsEnabled by mutableStateOf(true)
    var darkModeEnabled by mutableStateOf(false)
    var selectedLanguage by mutableStateOf("English")

    // --- Actions ---

    fun login(email: String) {
        isLoggedIn = true
        currentUser = currentUser.copy(email = email)
    }

    fun register(
        fullName: String,
        businessName: String,
        email: String,
        phone: String,
        accountType: AccountType,
        industry: String,
        location: String
    ) {
        currentUser = User(
            id = "user_${System.currentTimeMillis()}",
            fullName = fullName,
            businessName = businessName,
            email = email,
            phone = phone,
            accountType = accountType,
            industry = if (industry.isNotBlank()) industry else "General Business",
            location = if (location.isNotBlank()) location else "Sri Lanka",
            memberSince = "August 2026",
            about = "$businessName provides specialized services committed to SDG 17 partnerships.",
            partnershipScore = 90,
            activeRequirementsCount = 1,
            serviceOffersCount = 1,
            submittedProposalsCount = 0,
            partnershipsCount = 0
        )
        isLoggedIn = true
    }

    fun logout() {
        isLoggedIn = false
    }

    fun updateProfile(
        fullName: String,
        businessName: String,
        email: String,
        phone: String,
        industry: String,
        location: String,
        about: String
    ) {
        currentUser = currentUser.copy(
            fullName = fullName,
            businessName = businessName,
            email = email,
            phone = phone,
            industry = industry,
            location = location,
            about = about
        )
    }

    fun addRequirement(requirement: Requirement) {
        requirements.add(0, requirement)
        currentUser = currentUser.copy(
            activeRequirementsCount = currentUser.activeRequirementsCount + 1
        )
    }

    fun deleteRequirement(requirementId: String) {
        val index = requirements.indexOfFirst { it.id == requirementId }
        if (index != -1) {
            requirements.removeAt(index)
            currentUser = currentUser.copy(
                activeRequirementsCount = maxOf(0, currentUser.activeRequirementsCount - 1)
            )
        }
    }

    fun toggleSaveRequirement(requirementId: String) {
        val index = requirements.indexOfFirst { it.id == requirementId }
        if (index != -1) {
            val current = requirements[index]
            requirements[index] = current.copy(isSaved = !current.isSaved)
        }
    }

    fun addServiceOffer(offer: ServiceOffer) {
        serviceOffers.add(0, offer)
        currentUser = currentUser.copy(
            serviceOffersCount = currentUser.serviceOffersCount + 1
        )
    }

    fun addProposal(proposal: Proposal) {
        proposals.add(0, proposal)
        // Increment proposals count on the target requirement
        val reqIndex = requirements.indexOfFirst { it.id == proposal.requirementId }
        if (reqIndex != -1) {
            val req = requirements[reqIndex]
            requirements[reqIndex] = req.copy(proposalsCount = req.proposalsCount + 1)
        }
        currentUser = currentUser.copy(
            submittedProposalsCount = currentUser.submittedProposalsCount + 1
        )
    }
}

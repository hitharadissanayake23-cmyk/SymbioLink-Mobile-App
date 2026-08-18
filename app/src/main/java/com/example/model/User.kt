package com.example.model

/**
 * Student 3: User Model
 * Represents the authenticated business user in SymbioLink.
 */
data class User(
    val id: String = "user_001",
    val fullName: String = "Kasun Fernando",
    val businessName: String = "GreenTech Solutions",
    val email: String = "kasun@greentech.lk",
    val phone: String = "+94 77 123 4567",
    val accountType: AccountType = AccountType.MSME,
    val industry: String = "Sustainable Technology & IT",
    val location: String = "Colombo, Sri Lanka",
    val memberSince: String = "January 2026",
    val about: String = "GreenTech Solutions provides sustainable technology, clean IT infrastructure, and digital transformation services for growing businesses in Sri Lanka.",
    val partnershipScore: Int = 85,
    val activeRequirementsCount: Int = 2,
    val serviceOffersCount: Int = 3,
    val submittedProposalsCount: Int = 5,
    val partnershipsCount: Int = 4
)

enum class AccountType(val displayName: String) {
    ENTERPRISE("Enterprise"),
    MSME("MSME"),
    SERVICE_PROVIDER("Service Provider")
}

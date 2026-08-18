package com.example.model

/**
 * Student 2: Service Offer Model
 * Represents a business service offered by an MSME or Service Provider.
 */
data class ServiceOffer(
    val id: String,
    val serviceName: String,
    val businessName: String,
    val category: String,
    val description: String,
    val startingPrice: String,
    val deliveryTime: String,
    val experience: String,
    val contactEmail: String,
    val rating: Double = 4.8,
    val completedProjects: Int = 14,
    val basicTier: ServiceTier = ServiceTier("Basic", "LKR 50,000", "Initial consultation & setup"),
    val standardTier: ServiceTier = ServiceTier("Standard", "LKR 120,000", "Complete standard implementation"),
    val premiumTier: ServiceTier = ServiceTier("Premium", "LKR 250,000", "Full enterprise package with maintenance")
)

data class ServiceTier(
    val name: String,
    val price: String,
    val description: String
)

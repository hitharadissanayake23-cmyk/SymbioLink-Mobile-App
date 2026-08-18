package com.example.model

/**
 * Student 2: Requirement Model
 * Represents a business service requirement published by an Enterprise.
 */
data class Requirement(
    val id: String,
    val title: String,
    val companyName: String,
    val category: String,
    val description: String,
    val budget: String,
    val volume: String = "1 Project",
    val deadline: String,
    val location: String = "Colombo, Sri Lanka",
    val contactEmail: String,
    val postedDate: String = "18 Aug 2026",
    val proposalsCount: Int = 0,
    val status: RequirementStatus = RequirementStatus.ACTIVE,
    val isSaved: Boolean = false
)

enum class RequirementStatus(val label: String) {
    ACTIVE("Active"),
    CLOSED("Closed"),
    DRAFT("Draft")
}

package com.example.model

/**
 * Student 3: Proposal Model
 * Represents a business quotation/proposal submitted to an Enterprise requirement.
 */
data class Proposal(
    val id: String,
    val requirementId: String,
    val requirementTitle: String,
    val enterpriseName: String,
    val quotation: String,
    val estimatedTimeline: String,
    val proposalMessage: String,
    val contactName: String,
    val contactEmail: String,
    val contactPhone: String,
    val attachedDocumentName: String? = null,
    val submittedDate: String = "18 Aug 2026",
    val status: ProposalStatus = ProposalStatus.PENDING
)

enum class ProposalStatus(val label: String) {
    PENDING("Pending"),
    REVIEWED("Reviewed"),
    ACCEPTED("Accepted"),
    REJECTED("Rejected")
}

package com.example.model

/**
 * Student 3: Resource Model
 * Represents a knowledge base guide/article supporting UN SDG 17 & business growth.
 */
data class Resource(
    val id: String,
    val title: String,
    val category: String,
    val description: String,
    val fullContent: String,
    val readTime: String = "4 min read",
    val iconType: String = "book"
)

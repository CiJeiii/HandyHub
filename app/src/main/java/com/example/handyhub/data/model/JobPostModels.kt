package com.example.handyhub.data.model

import com.google.gson.annotations.SerializedName

/**
 * Request payload for POST /api/jobs to insert a record into the JOB_POSTS table.
 */
data class JobPostRequest(
    @SerializedName("employer_id")
    val employerId: String = "EMP-OWNER-001",

    @SerializedName("title")
    val title: String,

    @SerializedName("category")
    val category: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("address_district")
    val addressDistrict: String,

    @SerializedName("preferred_date_time")
    val preferredDateTime: String,

    @SerializedName("budget_php")
    val budgetPhp: Double,

    @SerializedName("status")
    val status: String = "OPEN",

    @SerializedName("problem_photos_base64")
    val problemPhotosBase64: List<String> = emptyList()
)

/**
 * Data Transfer Object representing a row in the MySQL JOB_POSTS table.
 */
data class JobPostDto(
    @SerializedName("job_id")
    val jobId: Int,

    @SerializedName("employer_id")
    val employerId: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("category")
    val category: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("address_district")
    val addressDistrict: String,

    @SerializedName("preferred_date_time")
    val preferredDateTime: String,

    @SerializedName("budget_php")
    val budgetPhp: Double,

    @SerializedName("status")
    var status: String = "OPEN",

    @SerializedName("employer_phone")
    val employerPhone: String? = null,

    @SerializedName("employer_address")
    val employerAddress: String? = null,

    @SerializedName("problem_photo_urls")
    val problemPhotoUrls: List<String> = emptyList(),

    @SerializedName("created_at")
    val createdAt: String = ""
)

/**
 * Thesis standardized 5 categories
 */
object ThesisCategories {
    const val CARPENTRY = "CARPENTRY"
    const val PLUMBING = "PLUMBING"
    const val ELECTRICAL = "ELECTRICAL"
    const val HOUSE_CLEANING = "HOUSE_CLEANING"
    const val APPLIANCE_SERVICING = "APPLIANCE_SERVICING"

    val CATEGORY_LIST = listOf(
        CARPENTRY,
        PLUMBING,
        ELECTRICAL,
        HOUSE_CLEANING,
        APPLIANCE_SERVICING
    )

    fun getDisplayName(category: String): String {
        return when (category.uppercase()) {
            CARPENTRY, "CARPENTRY SERVICES" -> "Carpentry"
            PLUMBING, "PLUMBING SERVICES" -> "Plumbing"
            ELECTRICAL, "ELECTRIC", "ELECTRICAL_SERVICES", "ELECTRICAL SERVICES" -> "Electrical Services"
            HOUSE_CLEANING, "HOUSE CLEANING" -> "House Cleaning"
            APPLIANCE_SERVICING, "APPLIANCE_SERVICING_MAINTENANCE", "APPLIANCE SERVICING", "APPLIANCE SERVICING & MAINTENANCE" -> "Appliance Servicing & Maintenance"
            else -> category
        }
    }
}

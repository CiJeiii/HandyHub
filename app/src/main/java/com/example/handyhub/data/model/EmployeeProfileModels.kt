package com.example.handyhub.data.model

import com.google.gson.annotations.SerializedName

/**
 * Request payload for creating/updating an Employee Profile in MySQL tables:
 * - EMPLOYEE_PROFILES (employee_id, bio, profile_photo_url)
 * - EMPLOYEE_SKILLS (employee_id, skill_category)
 * - PORTFOLIO (employee_id, image_url)
 */
data class EmployeeProfileRequest(
    @SerializedName("employee_id")
    val employeeId: String = "EMP-" + System.currentTimeMillis().toString().takeLast(6),

    @SerializedName("bio")
    val bio: String,

    @SerializedName("skills")
    val selectedSkills: List<String>,

    @SerializedName("profile_photo_base64")
    val profilePhotoBase64: String? = null,

    @SerializedName("portfolio_images_base64")
    val portfolioImagesBase64: List<String> = emptyList()
)

/**
 * Server response after saving employee profile to MySQL
 */
data class EmployeeProfileResponse(
    @SerializedName("status")
    val status: String,

    @SerializedName("message")
    val message: String,

    @SerializedName("employee_id")
    val employeeId: String? = null
)

/**
 * Skill entity matching EMPLOYEE_SKILLS table
 */
data class EmployeeSkillDto(
    @SerializedName("skill_id")
    val skillId: Int? = null,

    @SerializedName("employee_id")
    val employeeId: String,

    @SerializedName("category_name")
    val categoryName: String
)

/**
 * Portfolio item matching PORTFOLIO table
 */
data class PortfolioItemDto(
    @SerializedName("portfolio_id")
    val portfolioId: Int? = null,

    @SerializedName("employee_id")
    val employeeId: String,

    @SerializedName("image_url")
    val imageUrl: String
)

/**
 * Strict set of allowed skill categories for HandyHub Employees
 */
object AllowedCategories {
    val CATEGORIES = listOf(
        "Carpentry",
        "Plumbing",
        "Electrical Services",
        "House Cleaning",
        "Appliance Servicing & Maintenance"
    )
}

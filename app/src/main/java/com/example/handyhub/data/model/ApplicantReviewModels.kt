package com.example.handyhub.data.model

import com.google.gson.annotations.SerializedName

data class ApplicantDto(
    @SerializedName("application_id")
    val applicationId: Int,

    @SerializedName("job_id")
    val jobId: Int,

    @SerializedName("employee_id")
    val employeeId: String,

    @SerializedName("full_name")
    val fullName: String,

    @SerializedName("profile_photo_url")
    val profilePhotoUrl: String? = null,

    @SerializedName("star_rating")
    val starRating: Double = 4.9,

    @SerializedName("is_verified")
    val isVerified: Boolean = true,

    @SerializedName("proposal_message")
    val proposalMessage: String,

    @SerializedName("status")
    var status: String = "PENDING",

    @SerializedName("unmasked_phone")
    var unmaskedPhone: String? = null,

    @SerializedName("portfolio_image_urls")
    val portfolioImageUrls: List<String> = emptyList(),

    @SerializedName("skills")
    val skills: List<String> = emptyList()
)

data class AcceptApplicationRequest(
    @SerializedName("application_id")
    val applicationId: Int,

    @SerializedName("employer_id")
    val employerId: String = "EMP-OWNER-001",

    @SerializedName("job_id")
    val jobId: Int
)

data class AcceptApplicationResponse(
    @SerializedName("status")
    val status: String,

    @SerializedName("message")
    val message: String,

    @SerializedName("unmasked_phone")
    val unmaskedPhone: String,

    @SerializedName("unlock_id")
    val unlockId: Int? = null
)

package com.example.handyhub.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.handyhub.data.model.ApplicantDto
import com.example.handyhub.data.network.RetrofitClient
import com.example.handyhub.ui.components.ApplicantReviewCard
import com.example.handyhub.ui.theme.HandyHubTheme
import com.example.handyhub.ui.theme.MaroonPrimary
import com.example.handyhub.ui.theme.OffWhiteBackground
import com.example.handyhub.ui.theme.TextDark
import com.example.handyhub.ui.theme.TextSecondary
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun ReviewApplicantsScreen(
    jobId: Int = 101,
    jobTitle: String = "Ceiling Fan & Outlet Electrical Repair",
    jobCategory: String = "ELECTRICAL",
    onBackClick: () -> Unit = {},
    onViewProfileClick: (ApplicantDto, Boolean) -> Unit = { _, _ -> },
    onChatClick: (String) -> Unit = {}
) {
    val context = LocalContext.current
    val applicants = remember { mutableStateListOf<ApplicantDto>() }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(jobId) {
        isLoading = true
        val result = fetchApplicantsForJob(jobId)
        applicants.clear()
        applicants.addAll(result)
        isLoading = false
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(OffWhiteBackground)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header Banner
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaroonPrimary)
                    .padding(horizontal = 20.dp)
                    .padding(top = 44.dp, bottom = 24.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                    Column(modifier = Modifier.padding(start = 8.dp)) {
                        Text(
                            text = "Review Applicants (2)",
                            color = Color.White,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Black
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "$jobTitle ($jobCategory)",
                            color = Color.White.copy(alpha = 0.9f),
                            fontSize = 12.sp,
                            maxLines = 1
                        )
                    }
                }
            }

            // Body
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                if (isLoading) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = MaroonPrimary)
                    }
                } else if (applicants.isEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "No Applicants",
                            tint = Color.LightGray,
                            modifier = Modifier.size(64.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "No applicants yet for this job",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextDark
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp, vertical = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        item {
                            Text(
                                text = "APPLICANTS (${applicants.size})",
                                color = TextSecondary,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.5.sp
                            )
                        }

                        items(applicants) { applicant ->
                            ApplicantReviewCard(
                                applicant = applicant,
                                onStalkProfileClick = {
                                    val isAccepted = applicant.status.uppercase() == "ACCEPTED"
                                    onViewProfileClick(applicant, isAccepted)
                                },
                                onAcceptClick = {
                                    applicant.status = "ACCEPTED"
                                    applicant.unmaskedPhone = "917-888-9999"
                                    Toast.makeText(context, "Worker accepted! You can now chat or call directly.", Toast.LENGTH_LONG).show()
                                },
                                onDeclineClick = {
                                    applicant.status = "REJECTED"
                                    Toast.makeText(context, "Application declined for ${applicant.fullName}", Toast.LENGTH_SHORT).show()
                                },
                                onCallClick = {
                                    Toast.makeText(context, "Calling ${applicant.fullName} (+63 917 888 9999)...", Toast.LENGTH_SHORT).show()
                                },
                                onChatClick = {
                                    onChatClick(applicant.employeeId)
                                }
                            )
                        }

                        item {
                            Spacer(modifier = Modifier.height(24.dp))
                        }
                    }
                }
            }
        }
    }
}

private suspend fun fetchApplicantsForJob(jobId: Int): List<ApplicantDto> = withContext(Dispatchers.IO) {
    return@withContext try {
        val response = RetrofitClient.instance.getJobApplications(jobId)
        if (response.isSuccessful && response.body() != null) {
            response.body()!!
        } else {
            getMockApplicants(jobId)
        }
    } catch (_: Exception) {
        getMockApplicants(jobId)
    }
}

private fun getMockApplicants(jobId: Int): List<ApplicantDto> {
    return listOf(
        ApplicantDto(
            applicationId = 301,
            jobId = jobId,
            employeeId = "EMP-WORKER-001",
            fullName = "Roberto \"Bert\" Flores",
            starRating = 4.9,
            isVerified = true,
            proposalMessage = "I have 6 years of residential electrical experience in Carmen. Available today.",
            status = "PENDING",
            unmaskedPhone = null,
            skills = listOf("Electrical Services")
        ),
        ApplicantDto(
            applicationId = 302,
            jobId = jobId,
            employeeId = "EMP-WORKER-002",
            fullName = "Mario \"Mayong\" Santos",
            starRating = 2.3,
            isVerified = false,
            proposalMessage = "I can do fan repair cheaply. Contact me.",
            status = "REJECTED",
            unmaskedPhone = null,
            skills = listOf("Electrical Services")
        )
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ReviewApplicantsScreenPreview() {
    HandyHubTheme {
        ReviewApplicantsScreen()
    }
}

package com.example.handyhub.data.network

import com.example.handyhub.data.model.AcceptApplicationRequest
import com.example.handyhub.data.model.AcceptApplicationResponse
import com.example.handyhub.data.model.ApplicantDto
import com.example.handyhub.data.model.JobPostDto
import com.example.handyhub.data.model.JobPostRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @POST("api/jobs")
    suspend fun createJobPost(
        @Body request: JobPostRequest
    ): Response<JobPostDto>

    @GET("api/jobs/employer/{employerId}")
    suspend fun getEmployerJobPosts(
        @Path("employerId") employerId: String
    ): Response<List<JobPostDto>>

    @GET("api/jobs/feed")
    suspend fun getJobFeed(
        @Query("employee_id") employeeId: String? = null,
        @Query("category") category: String? = null
    ): Response<List<JobPostDto>>

    @GET("api/jobs/{jobId}/applications")
    suspend fun getJobApplications(
        @Path("jobId") jobId: Int
    ): Response<List<ApplicantDto>>

    @POST("api/accept-application")
    suspend fun acceptApplication(
        @Body request: AcceptApplicationRequest
    ): Response<AcceptApplicationResponse>
}

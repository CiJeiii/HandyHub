package com.example.handyhub.ui.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.handyhub.ui.screens.AccountTypeScreen
import com.example.handyhub.ui.screens.AuthMode
import com.example.handyhub.ui.screens.AuthScreen
import com.example.handyhub.ui.screens.EmployeeFeedScreen
import com.example.handyhub.ui.screens.EmployerDashboardScreen
import com.example.handyhub.ui.screens.PostJobScreen
import com.example.handyhub.ui.screens.ReviewApplicantsScreen
import com.example.handyhub.ui.screens.Role
import com.example.handyhub.ui.screens.WelcomeScreen
import com.example.handyhub.ui.screens.WorkerProfileScreen

object ScreenRoutes {
    const val WELCOME = "welcome"
    const val AUTH = "auth"
    const val ACCOUNT_TYPE = "account_type"
    const val EMPLOYER_DASHBOARD = "employer_dashboard"
    const val EMPLOYEE_FEED = "employee_feed"
    const val POST_JOB = "post_job"
    const val REVIEW_APPLICANTS = "review_applicants"
    const val WORKER_PROFILE = "worker_profile"
    const val WORKER_PROFILE_DETAIL = "worker_profile/{workerId}"

    fun createWorkerProfileRoute(workerId: String) = "worker_profile/$workerId"
}

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()
) {
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = ScreenRoutes.WELCOME
    ) {
        // 1. Welcome Screen (Starting destination)
        composable(ScreenRoutes.WELCOME) {
            WelcomeScreen(
                onGetStartedClick = {
                    navController.navigate(ScreenRoutes.AUTH)
                }
            )
        }

        // 2. Auth Screen (Login / Sign-up)
        composable(ScreenRoutes.AUTH) {
            AuthScreen(
                initialMode = AuthMode.LOG_IN,
                onBackClick = {
                    navController.popBackStack()
                },
                onSubmit = { mode, _, fullName, _, _ ->
                    val actionText = if (mode == AuthMode.LOG_IN) "Welcome back, $fullName!" else "Account created for $fullName!"
                    Toast.makeText(context, actionText, Toast.LENGTH_SHORT).show()
                    navController.navigate(ScreenRoutes.ACCOUNT_TYPE)
                },
                onGoogleSignIn = {
                    Toast.makeText(context, "Signed in with Google", Toast.LENGTH_SHORT).show()
                    navController.navigate(ScreenRoutes.ACCOUNT_TYPE)
                }
            )
        }

        // 3. Account Type Selection Screen (Role Choice)
        composable(ScreenRoutes.ACCOUNT_TYPE) {
            AccountTypeScreen(
                onSelectRole = { role ->
                    if (role == Role.EMPLOYEE) {
                        Toast.makeText(context, "Welcome to Worker Feed", Toast.LENGTH_SHORT).show()
                        navController.navigate(ScreenRoutes.EMPLOYEE_FEED) {
                            popUpTo(ScreenRoutes.WELCOME) { inclusive = true }
                        }
                    } else {
                        Toast.makeText(context, "Welcome to Employer Hub", Toast.LENGTH_SHORT).show()
                        navController.navigate(ScreenRoutes.EMPLOYER_DASHBOARD) {
                            popUpTo(ScreenRoutes.WELCOME) { inclusive = true }
                        }
                    }
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        // 4. Employer Hub Dashboard Screen
        composable(ScreenRoutes.EMPLOYER_DASHBOARD) {
            EmployerDashboardScreen(
                onPostJobClick = {
                    navController.navigate(ScreenRoutes.POST_JOB)
                },
                onReviewApplicantsClick = { _ ->
                    navController.navigate(ScreenRoutes.REVIEW_APPLICANTS)
                },
                onLogoutClick = {
                    navController.navigate(ScreenRoutes.WELCOME) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        // 5. Post Job Screen
        composable(ScreenRoutes.POST_JOB) {
            PostJobScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onJobPostedSuccess = {
                    navController.popBackStack()
                }
            )
        }

        // 6. Review Applicants Screen
        composable(ScreenRoutes.REVIEW_APPLICANTS) {
            ReviewApplicantsScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onViewProfileClick = { applicant ->
                    navController.navigate(ScreenRoutes.createWorkerProfileRoute(applicant.employeeId))
                }
            )
        }

        // 7. Worker Profile Screen (Self-view or Inspector view)
        composable(
            route = ScreenRoutes.WORKER_PROFILE
        ) {
            WorkerProfileScreen(
                workerName = "Juan Dela Cruz",
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = ScreenRoutes.WORKER_PROFILE_DETAIL,
            arguments = listOf(navArgument("workerId") { type = NavType.StringType })
        ) { backStackEntry ->
            val workerId = backStackEntry.arguments?.getString("workerId") ?: "EMP-01"
            val workerDisplayName = if (workerId == "EMP-WORKER-002") "Pedro Penduko" else "Juan Dela Cruz"
            WorkerProfileScreen(
                workerName = workerDisplayName,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        // 8. Employee Feed Screen
        composable(ScreenRoutes.EMPLOYEE_FEED) {
            EmployeeFeedScreen(
                onProfileClick = {
                    navController.navigate(ScreenRoutes.WORKER_PROFILE)
                },
                onLogoutClick = {
                    navController.navigate(ScreenRoutes.WELCOME) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}

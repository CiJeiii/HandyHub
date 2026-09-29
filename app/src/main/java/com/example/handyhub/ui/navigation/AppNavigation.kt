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
import com.example.handyhub.ui.screens.ChatScreen
import com.example.handyhub.ui.screens.EmployeeFeedScreen
import com.example.handyhub.ui.screens.EmployerDashboardScreen
import com.example.handyhub.ui.screens.NotificationsScreen
import com.example.handyhub.ui.screens.PostJobScreen
import com.example.handyhub.ui.screens.ResetPasswordScreen
import com.example.handyhub.ui.screens.ReviewApplicantsScreen
import com.example.handyhub.ui.screens.Role
import com.example.handyhub.ui.screens.WelcomeScreen
import com.example.handyhub.ui.screens.WorkerInspectionScreen
import com.example.handyhub.ui.screens.WorkerProfileScreen

object ScreenRoutes {
    const val WELCOME = "welcome"
    const val AUTH = "auth"
    const val ACCOUNT_TYPE = "account_type"
    const val EMPLOYER_DASHBOARD = "employer_dashboard"
    const val OPEN_REQUESTS = "open_requests"
    const val EMPLOYEE_FEED = "employee_feed"
    const val POST_JOB = "post_job"
    const val REVIEW_APPLICANTS = "review_applicants"
    const val WORKER_PROFILE = "worker_profile"
    const val WORKER_INSPECTION_DETAIL = "worker_inspection/{workerId}?isAccepted={isAccepted}"
    const val CHAT_DETAIL = "chat/{workerId}"
    const val NOTIFICATIONS = "notifications"
    const val RESET_PASSWORD = "reset_password"

    fun createWorkerInspectionRoute(workerId: String, isAccepted: Boolean = false) = "worker_inspection/$workerId?isAccepted=$isAccepted"
    fun createChatRoute(workerId: String) = "chat/$workerId"
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
                },
                onResetPasswordNavigate = {
                    navController.navigate(ScreenRoutes.RESET_PASSWORD)
                }
            )
        }

        // 3. Reset Password Screen
        composable(ScreenRoutes.RESET_PASSWORD) {
            ResetPasswordScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onPasswordUpdated = {
                    navController.popBackStack()
                }
            )
        }

        // 4. Account Type Selection Screen (Role Choice)
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

        // 5. Employer Hub Dashboard Screen
        composable(ScreenRoutes.EMPLOYER_DASHBOARD) {
            EmployerDashboardScreen(
                onPostJobClick = {
                    navController.navigate(ScreenRoutes.POST_JOB)
                },
                onReviewApplicantsClick = { _ ->
                    navController.navigate(ScreenRoutes.REVIEW_APPLICANTS)
                },
                onStalkProfileClick = { workerId ->
                    navController.navigate(ScreenRoutes.createWorkerInspectionRoute(workerId, true))
                },
                onSwitchRoleClick = {
                    navController.navigate(ScreenRoutes.EMPLOYEE_FEED) {
                        popUpTo(ScreenRoutes.EMPLOYER_DASHBOARD) { inclusive = true }
                    }
                },
                onLogoutClick = {
                    navController.navigate(ScreenRoutes.AUTH) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable(ScreenRoutes.OPEN_REQUESTS) {
            EmployerDashboardScreen(
                onPostJobClick = {
                    navController.navigate(ScreenRoutes.POST_JOB)
                },
                onReviewApplicantsClick = { _ ->
                    navController.navigate(ScreenRoutes.REVIEW_APPLICANTS)
                },
                onStalkProfileClick = { workerId ->
                    navController.navigate(ScreenRoutes.createWorkerInspectionRoute(workerId, true))
                },
                onSwitchRoleClick = {
                    navController.navigate(ScreenRoutes.EMPLOYEE_FEED) {
                        popUpTo(ScreenRoutes.OPEN_REQUESTS) { inclusive = true }
                    }
                },
                onLogoutClick = {
                    navController.navigate(ScreenRoutes.AUTH) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        // 6. Post Job Screen
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

        // 7. Review Applicants Screen
        composable(ScreenRoutes.REVIEW_APPLICANTS) {
            ReviewApplicantsScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onViewProfileClick = { applicant, isAccepted ->
                    navController.navigate(ScreenRoutes.createWorkerInspectionRoute(applicant.employeeId, isAccepted))
                },
                onChatClick = { workerId ->
                    navController.navigate(ScreenRoutes.createChatRoute(workerId))
                }
            )
        }

        // 8. Worker Profile Screen (Worker Owner POV)
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

        // 9. Worker Inspection Screen (Employer POV)
        composable(
            route = ScreenRoutes.WORKER_INSPECTION_DETAIL,
            arguments = listOf(
                navArgument("workerId") { type = NavType.StringType },
                navArgument("isAccepted") {
                    type = NavType.BoolType
                    defaultValue = false
                }
            )
        ) { backStackEntry ->
            val workerId = backStackEntry.arguments?.getString("workerId") ?: "EMP-01"
            val isAccepted = backStackEntry.arguments?.getBoolean("isAccepted") ?: false
            val isRoberto = workerId == "EMP-WORKER-001" || workerId == "EMP-01"
            val workerName = if (isRoberto) "Roberto \"Bert\" Flores" else "Mario \"Mayong\" Santos"
            val trade = if (isRoberto) "Master Electrician" else "Novice Electrician"

            WorkerInspectionScreen(
                workerId = workerId,
                workerName = workerName,
                primaryTrade = trade,
                initialAccepted = isAccepted,
                onBackClick = {
                    navController.popBackStack()
                },
                onChatClick = { targetId ->
                    navController.navigate(ScreenRoutes.createChatRoute(targetId))
                }
            )
        }

        // 10. Notifications Screen
        composable(ScreenRoutes.NOTIFICATIONS) {
            NotificationsScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onChatClick = { workerId ->
                    navController.navigate(ScreenRoutes.createChatRoute(workerId))
                }
            )
        }

        // 11. In-App Chat Screen
        composable(
            route = ScreenRoutes.CHAT_DETAIL,
            arguments = listOf(navArgument("workerId") { type = NavType.StringType })
        ) { backStackEntry ->
            val workerId = backStackEntry.arguments?.getString("workerId") ?: "EMP-01"
            val workerName = if (workerId == "EMP-WORKER-001") "Roberto \"Bert\" Flores" else "Juan Dela Cruz"
            ChatScreen(
                workerName = workerName,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        // 12. Employee Feed Screen
        composable(ScreenRoutes.EMPLOYEE_FEED) {
            EmployeeFeedScreen(
                onProfileClick = {
                    navController.navigate(ScreenRoutes.WORKER_PROFILE)
                },
                onNotificationsClick = {
                    navController.navigate(ScreenRoutes.NOTIFICATIONS)
                },
                onChatClick = { workerId ->
                    navController.navigate(ScreenRoutes.createChatRoute(workerId))
                },
                onSwitchRoleClick = {
                    navController.navigate(ScreenRoutes.EMPLOYER_DASHBOARD) {
                        popUpTo(ScreenRoutes.EMPLOYEE_FEED) { inclusive = true }
                    }
                },
                onLogoutClick = {
                    navController.navigate(ScreenRoutes.AUTH) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}

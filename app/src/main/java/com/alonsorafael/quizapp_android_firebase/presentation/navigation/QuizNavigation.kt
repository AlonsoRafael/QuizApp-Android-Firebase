package com.alonsorafael.quizapp_android_firebase.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.alonsorafael.quizapp_android_firebase.presentation.auth.LoginScreen
import com.alonsorafael.quizapp_android_firebase.presentation.auth.SignUpScreen
import com.alonsorafael.quizapp_android_firebase.presentation.home.HomeScreen
import com.alonsorafael.quizapp_android_firebase.presentation.quiz.QuizScreen
import com.alonsorafael.quizapp_android_firebase.presentation.results.QuizResultsScreen
import com.alonsorafael.quizapp_android_firebase.presentation.history.HistoryScreen

@Composable
fun QuizNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                onNavigateToSignUp = {
                    navController.navigate(Screen.SignUp.route)
                },
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.SignUp.route) {
            SignUpScreen(
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.SignUp.route) { inclusive = true }
                    }
                },
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.SignUp.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToQuiz = { quizId ->
                    navController.navigate(Screen.Quiz.createRoute(quizId))
                },
                onNavigateToHistory = {
                    navController.navigate(Screen.History.route)
                },
                onNavigateToProfile = {
                    // TODO: Implement profile screen
                },
                onSignOut = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
        
        composable(
            route = Screen.Quiz.route,
            arguments = Screen.Quiz.arguments
        ) { backStackEntry ->
            val quizId = backStackEntry.arguments?.getString("quizId") ?: return@composable
            QuizScreen(
                quizId = quizId,
                onNavigateToResults = { quizIdResult ->
                    navController.navigate(Screen.Results.createRoute(quizIdResult))
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(
            route = Screen.Results.route,
            arguments = Screen.Results.arguments
        ) { backStackEntry ->
            val quizId = backStackEntry.arguments?.getString("quizId") ?: return@composable
            QuizResultsScreen(
                quizId = quizId,
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                },
                onNavigateToHistory = {
                    navController.navigate(Screen.History.route)
                },
                onRetakeQuiz = {
                    navController.navigate(Screen.Quiz.createRoute(quizId)) {
                        popUpTo(Screen.Results.createRoute(quizId)) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.History.route) {
            HistoryScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToQuiz = { quizId ->
                    navController.navigate(Screen.Quiz.createRoute(quizId))
                }
            )
        }
    }
}

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object SignUp : Screen("signup")
    object Home : Screen("home")
    object Quiz : Screen("quiz/{quizId}") {
        val arguments = listOf(
            androidx.navigation.NavArgument.Builder()
                .setName("quizId")
                .setType(androidx.navigation.NavType.StringType)
                .build()
        )
        
        fun createRoute(quizId: String) = "quiz/$quizId"
    }
    object Results : Screen("results/{quizId}") {
        val arguments = listOf(
            androidx.navigation.NavArgument.Builder()
                .setName("quizId")
                .setType(androidx.navigation.NavType.StringType)
                .build()
        )
        
        fun createRoute(quizId: String) = "results/$quizId"
    }
    object History : Screen("history")
}

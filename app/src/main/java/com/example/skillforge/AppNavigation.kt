package com.example.skillforge

import com.example.skillforge.data.navigation.Screen
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.skillforge.screen.CourseDetailScreen
import com.example.skillforge.screen.HomeScreen
import com.example.skillforge.screen.LessonScreen


@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {

        composable(Screen.Home.route) {
            HomeScreen(navController)
        }

        composable(
            route = Screen.Detail.route,
            arguments = listOf(
                navArgument("courseId") {
                    type = NavType.StringType
                }
            )
        ) {

            val courseId =
                it.arguments?.getString("courseId") ?: ""

            CourseDetailScreen(navController, courseId)

        }

        composable(
            route = Screen.Lesson.route,
            arguments = listOf(
                navArgument("courseId") {
                    type = NavType.StringType
                },
                navArgument("lessonId") {
                    type = NavType.StringType
                }
            )
        ) {

            val courseId =
                it.arguments?.getString("courseId") ?: ""

            val lessonId =
                it.arguments?.getString("lessonId") ?: ""

            LessonScreen(
                navController,
                courseId,
                lessonId
            )

        }

    }

}
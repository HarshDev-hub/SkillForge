package com.example.skillforge.data.navigation



sealed class Screen(val route: String) {

    object Home : Screen("home")

    object Detail : Screen("detail/{courseId}") {

        fun createRoute(courseId: String) = "detail/$courseId"

    }

    object Lesson : Screen("lesson/{courseId}/{lessonId}") {

        fun createRoute(
            courseId: String,
            lessonId: String
        ) = "lesson/$courseId/$lessonId"

    }

}
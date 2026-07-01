package com.example.skillforge.data.repo

import com.example.skillforge.data.api.RetrofitClient
import com.example.skillforge.data.model.ApiResponse
import com.example.skillforge.data.model.Course
import com.example.skillforge.data.model.Category

class CourseRepository {

    suspend fun getData(): ApiResponse {
        return RetrofitClient.apiService.getCourses()
    }

    suspend fun getAllCourses(): List<Course> {
        return getData().categories.flatMap { it.courses }
    }

    suspend fun getCourseById(courseId: String): Course? {
        return getAllCourses().find {
            it.id == courseId
        }
    }

    suspend fun getCourseWithCategory(courseId: String): Pair<Course, Category>? {
        val categories = getData().categories
        for (category in categories) {
            val course = category.courses.find { it.id == courseId }
            if (course != null) {
                return Pair(course, category)
            }
        }
        return null
    }

}
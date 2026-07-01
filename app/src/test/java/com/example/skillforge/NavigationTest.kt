package com.example.skillforge

import com.example.skillforge.data.navigation.Screen
import org.junit.Assert.assertEquals
import org.junit.Test

class NavigationTest {

    @Test
    fun `Screen Detail createRoute returns correct path`() {
        val courseId = "kotlin-101"
        val expected = "detail/kotlin-101"
        val actual = Screen.Detail.createRoute(courseId)
        assertEquals(expected, actual)
    }

    @Test
    fun `Screen Lesson createRoute returns correct path`() {
        val courseId = "kotlin-101"
        val lessonId = "lesson-1"
        val expected = "lesson/kotlin-101/lesson-1"
        val actual = Screen.Lesson.createRoute(courseId, lessonId)
        assertEquals(expected, actual)
    }
}

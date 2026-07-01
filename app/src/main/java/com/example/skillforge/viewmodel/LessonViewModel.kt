package com.example.skillforge.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillforge.data.model.Course
import com.example.skillforge.data.model.Lesson
import com.example.skillforge.data.model.Category
import com.example.skillforge.data.repo.CourseRepository
import com.example.skillforge.screen.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LessonViewModel : ViewModel() {

    private val repository = CourseRepository()

    private val _lessonState = MutableStateFlow<UiState<Triple<Course, Lesson, Category>>>(UiState.Loading)
    val lessonState: StateFlow<UiState<Triple<Course, Lesson, Category>>> = _lessonState

    fun fetchLessonDetails(courseId: String, lessonId: String) {
        viewModelScope.launch {
            _lessonState.value = UiState.Loading
            try {
                val result = repository.getCourseWithCategory(courseId)
                val course = result?.first
                val category = result?.second
                val lesson = course?.lessons?.find { it.id == lessonId }
                
                if (course != null && lesson != null && category != null) {
                    _lessonState.value = UiState.Success(Triple(course, lesson, category))
                } else {
                    _lessonState.value = UiState.Error("Lesson, Course or Category not found")
                }
            } catch (e: Exception) {
                _lessonState.value = UiState.Error(e.localizedMessage ?: "Unknown Error")
            }
        }
    }
}
package com.example.skillforge.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillforge.data.model.Course
import com.example.skillforge.data.model.Category
import com.example.skillforge.data.repo.CourseRepository
import com.example.skillforge.screen.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CourseDetailViewModel : ViewModel() {

    private val repository = CourseRepository()

    private val _courseState = MutableStateFlow<UiState<Pair<Course, Category>>>(UiState.Loading)
    val courseState: StateFlow<UiState<Pair<Course, Category>>> = _courseState

    fun fetchCourseDetails(courseId: String) {
        viewModelScope.launch {
            _courseState.value = UiState.Loading
            try {
                val result = repository.getCourseWithCategory(courseId)
                if (result != null) {
                    _courseState.value = UiState.Success(result)
                } else {
                    _courseState.value = UiState.Error("Course not found")
                }
            } catch (e: Exception) {
                _courseState.value = UiState.Error(e.localizedMessage ?: "Unknown Error")
            }
        }
    }
}
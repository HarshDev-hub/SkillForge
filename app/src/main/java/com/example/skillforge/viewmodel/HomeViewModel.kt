package com.example.skillforge.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillforge.data.model.ApiResponse
import com.example.skillforge.data.repo.CourseRepository
import com.example.skillforge.screen.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val repository = CourseRepository()

    private val _coursesState =
        MutableStateFlow<UiState<ApiResponse>>(UiState.Loading)

    val coursesState: StateFlow<UiState<ApiResponse>>
        get() = _coursesState

    init {
        fetchCourses()
    }

    private fun fetchCourses() {

        viewModelScope.launch {

            try {

                val response = repository.getData()

                _coursesState.value = UiState.Success(response)

            } catch (e: Exception) {

                _coursesState.value =
                    UiState.Error(e.localizedMessage ?: "Unknown Error")

            }

        }

    }

}
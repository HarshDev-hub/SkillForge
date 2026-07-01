package com.example.skillforge.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.skillforge.screen.component.HeaderSection
import com.example.skillforge.screen.component.SearchBar
import com.example.skillforge.ui.theme.Background
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.navigation.NavHostController
import com.example.skillforge.data.navigation.Screen
import com.example.skillforge.screen.component.CategoryCard
import com.example.skillforge.screen.component.CourseCard
import com.example.skillforge.screen.component.SectionTitle
import com.example.skillforge.screen.state.UiState
import com.example.skillforge.viewmodel.HomeViewModel


@Composable
fun HomeScreen( navController: NavHostController) {

    val viewModel: HomeViewModel = viewModel()

    val state by viewModel.coursesState.collectAsState()

    when (state) {

        is UiState.Loading -> {

            // Abhi simple hai, baad me loader improve karenge

        }

        is UiState.Error -> {

            // Error UI baad me banayenge

        }

        is UiState.Success -> {

            val response = (state as UiState.Success).data

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Background)
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {

                item {

                    Spacer(modifier = Modifier.height(18.dp))

                    HeaderSection()

                }

                item {

                    SearchBar()

                }

                item {

                    SectionTitle("Categories")

                }

                item {

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {

                        items(response.categories) { category ->

                            CategoryCard(category)

                        }

                    }

                }

                item {

                    SectionTitle(
                        title = "Popular courses"
                    )

                }

                items(
                    response.categories.flatMap { it.courses }
                ) { course ->

                    CourseCard(
                        course = course,
                        onClick = {
                            navController.navigate(
                                Screen.Detail.createRoute(course.id)
                            )
                        }
                    )

                }

            }

        }

    }

}
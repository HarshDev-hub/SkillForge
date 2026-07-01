package com.example.skillforge.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import com.example.skillforge.data.model.Course
import com.example.skillforge.data.model.Category
import com.example.skillforge.screen.component.HeroSection
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.TextButton
import androidx.compose.ui.draw.clip
import coil.compose.AsyncImage
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.skillforge.data.navigation.Screen
import com.example.skillforge.screen.state.UiState
import com.example.skillforge.viewmodel.CourseDetailViewModel

@Composable
fun CourseDetailScreen(
    navController: NavController,
    courseId: String
) {
    val viewModel: CourseDetailViewModel = viewModel()
    val state by viewModel.courseState.collectAsState()

    LaunchedEffect(courseId) {
        viewModel.fetchCourseDetails(courseId)
    }

    when (state) {
        is UiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color(0xFF18B7A7))
            }
        }

        is UiState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = (state as UiState.Error).message)
            }
        }

        is UiState.Success -> {
            val (course, category) = (state as UiState.Success).data
            CourseDetailContent(navController, course, category)
        }
    }
}

@Composable
fun CourseDetailContent(
    navController: NavController,
    course: Course,
    category: Category
) {
    val themeColor = try {
        Color(android.graphics.Color.parseColor(category.iconColor))
    } catch (e: Exception) {
        Color(0xFF18B7A7)
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {

            item {
                HeroSection(course, navController, themeColor)
            }
            item {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                ) {

                    Spacer(modifier = Modifier.height(16.dp))

                    // Tags
                    Row {

                        course.tags.forEach { tag ->

                            Surface(
                                color = themeColor.copy(alpha = 0.1f),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.padding(end = 8.dp)
                            ) {

                                Text(
                                    text = tag,
                                    color = themeColor,
                                    modifier = Modifier.padding(
                                        horizontal = 10.dp,
                                        vertical = 6.dp
                                    ),
                                    fontSize = 12.sp
                                )

                            }

                        }

                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    Text(
                        text = course.title,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = course.subtitle,
                        color = Color.Gray,
                        fontSize = 18.sp
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            Icons.Default.Star,
                            contentDescription = null,
                            tint = Color(0xFFFFB800),
                            modifier = Modifier.size(18.dp)
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Text(course.rating.toString())

                        Spacer(modifier = Modifier.width(18.dp))

                        Icon(
                            Icons.Default.People,
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier.size(18.dp)
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Text(course.studentsEnrolled.toString())

                        Spacer(modifier = Modifier.width(18.dp))

                        Icon(
                            Icons.Default.AccessTime,
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier.size(18.dp)
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Text("${course.durationHours}h")

                        Spacer(modifier = Modifier.width(18.dp))

                        Text(
                            text = course.level,
                            color = themeColor,
                            fontWeight = FontWeight.SemiBold
                        )

                    }

                }

            }

            item {

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 20.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        AsyncImage(
                            model = course.instructor.avatarUrl,
                            contentDescription = null,
                            modifier = Modifier
                                .size(60.dp)
                                .clip(CircleShape)
                        )

                        Spacer(modifier = Modifier.width(14.dp))

                        Column(
                            modifier = Modifier.weight(1f)
                        ) {

                            Text(
                                text = course.instructor.name,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = course.instructor.title,
                                color = Color.Gray
                            )

                        }

                        TextButton(
                            onClick = {}
                        ) {

                            Text(
                                text = "Follow",
                                color = themeColor,
                                fontWeight = FontWeight.Bold
                            )

                        }

                    }

                }

            }

            item {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                ) {

                    Spacer(modifier = Modifier.height(18.dp))

                    Text(
                        text = course.description,
                        fontSize = 16.sp,
                        color = Color.Gray,
                        lineHeight = 24.sp
                    )

                }

            }

            item {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                ) {

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "Course content",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "${course.lessons.size} lessons · ${
                            course.lessons.sumOf { it.durationMinutes }
                        } min",
                        color = Color.Gray
                    )

                }

            }

            items(course.lessons) { lesson ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 6.dp)
                        .clickable {
                            navController.navigate(Screen.Lesson.createRoute(course.id, lesson.id))
                        },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Column(
                            modifier = Modifier.weight(1f)
                        ) {

                            Text(
                                text = lesson.title,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 18.sp
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = "${lesson.durationMinutes} min",
                                color = Color.Gray
                            )

                        }

                        if (lesson.isFree) {

                            Surface(
                                color = themeColor.copy(alpha = 0.1f),
                                shape = RoundedCornerShape(8.dp)
                            ) {

                                Text(
                                    text = "FREE",
                                    modifier = Modifier.padding(
                                        horizontal = 10.dp,
                                        vertical = 5.dp
                                    ),
                                    color = themeColor
                                )

                            }

                        } else {

                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = null,
                                tint = Color.Gray
                            )

                        }

                    }

                }

            }

            item {
                Spacer(modifier = Modifier.height(90.dp))
            }

        }

        BottomBar(
            modifier = Modifier.align(Alignment.BottomCenter),
            themeColor = themeColor,
            onEnrollClick = {
                // Navigate to first lesson
                if (course.lessons.isNotEmpty()) {
                    navController.navigate(Screen.Lesson.createRoute(course.id, course.lessons[0].id))
                }
            }
        )

    }

}


@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    themeColor: Color = Color(0xFF18B7A7),
    onEnrollClick: () -> Unit
) {

    Surface(
        modifier = modifier.fillMaxWidth(),
        shadowElevation = 10.dp,
        color = Color.White
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = "PRICE",
                    color = Color.Gray,
                    fontSize = 12.sp
                )

                Text(
                    text = "Free",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

            }

            Button(
                onClick = onEnrollClick,
                modifier = Modifier.height(54.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = themeColor
                )
            ) {

                Text(
                    text = "Enroll now"
                )

            }

        }

    }

}
package com.example.skillforge.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.skillforge.data.model.Course
import com.example.skillforge.data.model.Lesson
import com.example.skillforge.data.navigation.Screen
import com.example.skillforge.screen.state.UiState
import com.example.skillforge.viewmodel.LessonViewModel

@Composable
fun LessonScreen(
    navController: NavController,
    courseId: String,
    lessonId: String
) {
    val viewModel: LessonViewModel = viewModel()
    val state by viewModel.lessonState.collectAsState()

    LaunchedEffect(courseId, lessonId) {
        viewModel.fetchLessonDetails(courseId, lessonId)
    }

    when (state) {
        is UiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color(0xFF18B7A7))
            }
        }
        is UiState.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = (state as UiState.Error).message)
            }
        }
        is UiState.Success -> {
            val (course, lesson) = (state as UiState.Success).data
            LessonContent(navController, course, lesson)
        }
    }
}

@Composable
fun LessonContent(
    navController: NavController,
    course: Course,
    currentLesson: Lesson
) {
    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
        // Video Player Placeholder
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .background(Color(0xFF0D2B24)) // Dark greenish background from image
        ) {
            // Top Controls
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.background(Color.White.copy(alpha = 0.1f), CircleShape)
                ) {
                    Icon(Icons.Default.ChevronLeft, contentDescription = "Back", tint = Color.White)
                }
                IconButton(
                    onClick = { /* Fullscreen */ },
                    modifier = Modifier.background(Color.White.copy(alpha = 0.1f), CircleShape)
                ) {
                    Icon(Icons.Default.Fullscreen, contentDescription = "Fullscreen", tint = Color.White)
                }
            }

            // Play Button
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .background(Color.White.copy(alpha = 0.2f), CircleShape)
                    .align(Alignment.Center),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.PlayArrow,
                    contentDescription = "Play",
                    tint = Color.White,
                    modifier = Modifier.size(40.dp)
                )
            }

            // Bottom Progress Info
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("02:14", color = Color.White, fontSize = 12.sp)
                    Slider(
                        value = 0.35f,
                        onValueChange = {},
                        modifier = Modifier.weight(1f).padding(horizontal = 8.dp),
                        colors = SliderDefaults.colors(
                            thumbColor = Color.White,
                            activeTrackColor = Color(0xFF18B7A7)
                        )
                    )
                    Text("06:00", color = Color.White, fontSize = 12.sp)
                }
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            item {
                Text(
                    text = "LESSON ${course.lessons.indexOf(currentLesson) + 1} · ${course.title.uppercase()}",
                    color = Color(0xFF18B7A7),
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = currentLesson.title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = currentLesson.content,
                    color = Color.Gray,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(24.dp))

                // Tabs
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    LessonTab("Lessons", isSelected = true)
                    Spacer(modifier = Modifier.width(20.dp))
                    LessonTab("Notes", isSelected = false)
                    Spacer(modifier = Modifier.width(20.dp))
                    LessonTab("Resources", isSelected = false)
                }
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(color = Color(0xFFF0F0F0))
                Spacer(modifier = Modifier.height(16.dp))
            }

            items(course.lessons) { lesson ->
                val isPlaying = lesson.id == currentLesson.id
                LessonItem(
                    lesson = lesson,
                    isPlaying = isPlaying,
                    onClick = {
                        if (!isPlaying) {
                            navController.navigate(Screen.Lesson.createRoute(course.id, lesson.id)) {
                                popUpTo(Screen.Lesson.route) {
                                    inclusive = true
                                }
                            }
                        }
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun LessonTab(title: String, isSelected: Boolean) {
    Column {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            color = if (isSelected) Color.Black else Color.Gray,
            fontSize = 16.sp
        )
        if (isSelected) {
            Spacer(modifier = Modifier.height(4.dp))
            Box(
                modifier = Modifier
                    .width(40.dp)
                    .height(3.dp)
                    .background(Color(0xFF18B7A7), RoundedCornerShape(2.dp))
            )
        }
    }
}

@Composable
fun LessonItem(lesson: Lesson, isPlaying: Boolean, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isPlaying) Color(0xFFE8FBF7) else Color.White
        ),
        border = if (!isPlaying) androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFF0F0F0)) else null
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        if (isPlaying) Color(0xFF18B7A7) else Color(0xFFF0F0F0),
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isPlaying) Icons.Default.Pause else if (lesson.isFree) Icons.Default.PlayArrow else Icons.Default.Lock,
                    contentDescription = null,
                    tint = if (isPlaying) Color.White else Color.Gray,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = lesson.title,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = if (isPlaying) Color(0xFF0D2B24) else Color.Black
                )
                Text(
                    text = if (isPlaying) "Now playing · ${lesson.durationMinutes} min" else "${lesson.durationMinutes} min",
                    fontSize = 14.sp,
                    color = if (isPlaying) Color(0xFF18B7A7) else Color.Gray
                )
            }
            if (lesson.isFree && !isPlaying) {
                Surface(
                    color = Color(0xFFE8FBF7),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "FREE",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        color = Color(0xFF18B7A7),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
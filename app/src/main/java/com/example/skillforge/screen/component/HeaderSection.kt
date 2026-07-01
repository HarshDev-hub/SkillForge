package com.example.skillforge.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HeaderSection() {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {

        Column {

            Text(
                text = "Welcome back",
                color = Color.Gray,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Find your next skill",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

        }

        Row {

            Surface(
                modifier = Modifier.size(48.dp),
                shape = CircleShape,
                tonalElevation = 1.dp
            ) {

                IconButton(onClick = {}) {

                    Icon(
                        imageVector = Icons.Outlined.Notifications,
                        contentDescription = null
                    )

                }

            }

            Spacer(modifier = Modifier.width(12.dp))

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF19B7A7)),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = "AS",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )

            }

        }

    }

}

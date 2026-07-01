package com.example.skillforge

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.skillforge.screen.HomeScreen
import com.example.skillforge.screen.component.HeaderSection
import com.example.skillforge.screen.state.UiState
import com.example.skillforge.ui.theme.SkillForgeTheme
import com.example.skillforge.viewmodel.HomeViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val viewModel: HomeViewModel = viewModel()

            val state by viewModel.coursesState.collectAsState()

            when (state) {

                is UiState.Loading -> {
                    Text("Loading...")
                }

                is UiState.Success -> {

                    val data = (state as UiState.Success).data

                    LaunchedEffect(Unit) {

                        Log.d("API_DATA", data.toString())

                    }

                    AppNavigation()

                }

                is UiState.Error -> {

                    val error = (state as UiState.Error).message

                    Text(error)

                }
            }

        }

    }

}
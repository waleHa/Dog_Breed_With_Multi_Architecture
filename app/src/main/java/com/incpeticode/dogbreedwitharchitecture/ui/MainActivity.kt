package com.incpeticode.dogbreedwitharchitecture.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.incpeticode.dogbreedwitharchitecture.ui.components.DogBreedScreen
import com.incpeticode.dogbreedwitharchitecture.ui.components.RandomImageTopBar
import com.incpeticode.dogbreedwitharchitecture.ui.theme.DogBreedWithArchitectureTheme
import com.incpeticode.dogbreedwitharchitecture.ui.viewmodel.DogViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
// Main activity implementing the DogView
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: DogViewModel by viewModels() // Get ViewModel instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Enable edge-to-edge display

        setContent {
            val randomImage by viewModel.randomImage.collectAsState() // Observe random image state

            DogBreedWithArchitectureTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { RandomImageTopBar(randomImage) }, // Display top bar with random image
                    content = { innerPadding ->
                        DogBreedScreen(modifier = Modifier.padding(innerPadding)) // Display dog breed screen
                    }
                )
            }
        }
    }
}

// Preview function for the DogBreedScreen composable
@Preview(showBackground = true)
@Composable
fun DogBreedScreenPreview() {
    DogBreedWithArchitectureTheme {
        DogBreedScreen() // Display the dog breed screen in preview mode
    }
}
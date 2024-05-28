package com.incpeticode.dogbreedwitharchitecture.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.incpeticode.dogbreedwitharchitecture.domain.model.DogImageModel
import com.incpeticode.dogbreedwitharchitecture.ui.viewmodel.DogViewModel
import com.incpeticode.dogbreedwitharchitecture.ui.viewmodel.DogViewState

// Composable function to display the top bar with the random dog image
@Composable
fun RandomImageTopBar(randomImage: DogImageModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth() // Make the card take the full width
            .aspectRatio(1.6f) // Set the aspect ratio
            .padding(8.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize() // Make the box take the full size of the card
                .background(Color.LightGray), // Set background color
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = rememberAsyncImagePainter(randomImage.imageUrl), // Load the image from the URL
                contentDescription = null,
                contentScale = ContentScale.Crop, // Crop the image to fill the box
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

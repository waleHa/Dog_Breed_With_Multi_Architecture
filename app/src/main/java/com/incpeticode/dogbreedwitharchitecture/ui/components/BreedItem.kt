package com.incpeticode.dogbreedwitharchitecture.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Composable function to display a single breed item in the list
@Composable
fun BreedItem(breed: String, onClick: () -> Unit) {
    Text(
        text = breed, // Display the breed name
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .fillMaxWidth() // Make the item take the full width
            .padding(8.dp) // Add padding around the item
            .clickable(onClick = onClick) // Make the item clickable and trigger the provided onClick action
    )
}
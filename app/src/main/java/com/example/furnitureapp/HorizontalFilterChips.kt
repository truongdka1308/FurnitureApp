package com.example.furnitureapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HorizontalFilterChips(categories: List<Category>, onCategorySelected: (Category) -> Unit) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
    ) {
        items(categories) { category ->
            FilterChip(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .clickable { onCategorySelected(category) }, // Sử dụng clickable để bắt sự kiện nhấp vào
                img = category.img,
                text = category.name
            )
        }
    }
}

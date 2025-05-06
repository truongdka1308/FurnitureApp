package com.example.furnitureapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.furnitureapp.*
import com.example.furnitureapp.R.*
import com.example.furnitureapp.model.CategoryViewModel
import com.example.furnitureapp.model.ProductViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController: NavController,
    productViewModel: ProductViewModel = viewModel(),
    categoryViewModel: CategoryViewModel = viewModel()
) {
    val products by productViewModel.products.collectAsState(initial = emptyList())

    val categories by categoryViewModel.categories.collectAsState(initial = emptyList())

    val selectedCategory = remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()

    // Gọi API lấy category khi vào màn hình
    LaunchedEffect(true) {
        categoryViewModel.fetchCategories()
        productViewModel.fetchAllProduct();
    }

    Column {
        CustomHeader(
            title = "Make Home",
            subTitle = "Beautiful",
            startIconResId = drawable.search,
            endIconResId = drawable.cart_icon,
            onEndIconClicked = {
                navController.navigate("cart")
            },
            onStartIconClicked = {
                navController.navigate("search")
            }
        )

        // Hiển thị danh sách categories
        HorizontalFilterChips(
            categories = categories,
            onCategorySelected = { category ->
                selectedCategory.value = category._id
                coroutineScope.launch {
                    productViewModel.fetchProductsByCategory(category._id)
                }
            }

        )

        // Nếu chưa chọn category, thì hiển thị tất cả sản phẩm
        val filteredProducts = if (selectedCategory.value != null) {
            products.filter { it.category == selectedCategory.value }
        } else {
            products
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize()
        ) {
            items(filteredProducts) { product ->
                ProductItem(navController, product = product)
            }
        }
    }
}

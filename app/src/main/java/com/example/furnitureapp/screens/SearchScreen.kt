package com.example.furnitureapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.furnitureapp.R
import com.example.furnitureapp.model.Product.Product
//import com.example.furnitureapp.api.Product
import com.example.furnitureapp.model.ProductViewModel

@Composable
fun SearchScreen(navController: NavController, productViewModel: ProductViewModel = viewModel()) {
    val products by productViewModel.products.collectAsState()
    val searchTextState = remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        productViewModel.fetchAllProduct()
    }

    // Filter products based on search text
    val filteredProducts = products.filter { product ->
        product.name.contains(searchTextState.value, ignoreCase = true)
    }

    Column {
        SearchHeader(
            searchText = searchTextState.value,
            onSearchTextChanged = { newText ->
                searchTextState.value = newText
            },
            startIconResId = R.drawable.search,
            endIconResId = R.drawable.logout,
            onEndIconClicked = { navController.popBackStack() }
        )

        LazyColumn {
            items(filteredProducts) { product ->
                SearchItem(navController = navController, product = product)
            }
        }
    }
}

@Composable
fun SearchHeader(
    searchText: String,
    onSearchTextChanged: (String) -> Unit,
    startIconResId: Int? = null,
    endIconResId: Int? = null,
    onStartIconClicked: () -> Unit = {},
    onEndIconClicked: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Start Icon
        startIconResId?.let { iconResId ->
            IconButton(onClick = { onStartIconClicked() }) {
                Icon(
                    painter = painterResource(id = iconResId),
                    contentDescription = "Start Icon",
                    tint = Color.Black,
                    modifier = Modifier.size(25.dp)
                )
            }
        }

        Spacer(modifier = Modifier.width(8.dp)) // Adjust the width of the spacer as needed

        // TextInput
        OutlinedTextField(
            value = searchText,
            onValueChange = { newValue ->
                onSearchTextChanged(newValue)
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = { /*TODO*/ }),
            placeholder = { Text("Search", fontSize = 14.sp) },
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(8.dp)) // Adjust the width of the spacer as needed

        // End Icon
        endIconResId?.let { iconResId ->
            IconButton(onClick = { onEndIconClicked() }) {
                Icon(
                    painter = painterResource(id = iconResId),
                    contentDescription = "End Icon",
                    tint = if (endIconResId == R.drawable.blank) Color.White else Color.Black,
                    modifier = Modifier.size(25.dp)
                )
            }
        }
    }
}

@Composable
fun SearchItem(
    navController: NavController,
    product: Product
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { navController.navigate("detail/${product._id}") },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
        ) {
            // Load and display image using Coil
            Box {
                val painter = rememberImagePainter(data = product.img?.get(0))
                Image(
                    painter = painter,
                    contentDescription = product.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(shape = RoundedCornerShape(12.dp))
                )
            }
            Spacer(modifier = Modifier.width(14.dp)) // Add space between image and text
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = product.name, fontSize = 14.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "$ ${product.price}", color = Color.Black, fontWeight = FontWeight.Bold)
            }
        }
    }
}

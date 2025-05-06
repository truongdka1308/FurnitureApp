package com.example.furnitureapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomHeader(
    title: String? = null,
    subTitle: String? = null,
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

        Spacer(modifier = Modifier.weight(1f))

        // Title
        Column(modifier = Modifier.weight(1.2f).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
            ) {
            title?.let{
                Text(
                text = title,
                modifier = Modifier.align(Alignment.CenterHorizontally).fillMaxWidth(),
                fontSize = 18.sp,
                color = Color.Gray

            )
            }
            // Subtitle
            subTitle?.let {
                Text(
                    text = subTitle,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    fontFamily = gelasioFontFamily
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // End Icon
        endIconResId?.let { iconResId ->
            IconButton(onClick = { onEndIconClicked() }) {
                Icon(
                    painter = painterResource(id = iconResId),
                    contentDescription = "End Icon",
                    tint = if (endIconResId==R.drawable.blank) Color.White else Color.Black,
                    modifier = Modifier.size(25.dp)
                )
            }
        }
    }
}


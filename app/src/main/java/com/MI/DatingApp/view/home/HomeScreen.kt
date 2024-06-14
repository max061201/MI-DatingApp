package com.MI.DatingApp.view.home


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.MI.DatingApp.R

@Composable
fun HomeScreen() {
    var showFilter: Boolean by remember { mutableStateOf(true) }

    Header(showFilter) {
        showFilter = true
    }
    if (showFilter) {
        FilterView {
            showFilter = false
        }
    }

}

@Composable
fun Header(showFilter: Boolean, onFilterClick: () -> Unit) {
    Box (contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Icon(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = null,
            tint = Color.Red,
            modifier = Modifier
                .size(60.dp)
                .blur(4.dp)
        )
        Text(
            text = "Chat&Meet",
            color = Color.White,
            style = TextStyle(
                shadow = Shadow(
                    color = Color.Black,
                    offset = Offset(3f, 3f),
                    blurRadius = 8f,
                )
            ),
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Center)
        )

        IconButton(
            onClick = onFilterClick,
            modifier = Modifier.align(Alignment.TopEnd).padding(16.dp).size(60.dp)

        ) {
            Icon(
                painter = painterResource(id = R.drawable.filter),
                contentDescription = "Filter",
                tint = Color.Black
            )
        }
    }
}



@Composable
fun FilterView(onClose: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.White, RoundedCornerShape(8.dp))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Filter",
            style = TextStyle(
                color = Color.Black,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Ajoutez vos composants de filtre ici, par exemple des sliders et des boutons.
        Text(text = "Distance")
        // Exemple de composants
        Spacer(modifier = Modifier.height(16.dp))
        Slider(value = 0f, onValueChange = {})
        Text(text = "Gender")
        Row {
            Button(onClick = {}) { Text(text = "Male") }
            Button(onClick = {}) { Text(text = "Female") }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Age")
        Slider(value = 0f, onValueChange = {})
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onClose) {
            Text(text = "Close")
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewHomeScreen() {
    Column(Modifier.fillMaxSize()) {
        HomeScreen()
    }

}

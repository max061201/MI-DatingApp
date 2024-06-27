package com.MI.DatingApp.view.home

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.sp
import com.MI.DatingApp.model.User
/**
UserDetail show more about the user
 */
@Composable
fun UserDetail(item: User, onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .height(500.dp)
            .padding(top = 50.dp, bottom = 50.dp, start = 20.dp, end = 20.dp )
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    onBack()
                })
            }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .clip(RoundedCornerShape(35.dp))
                .padding(8.dp)
        ) {
            var currentImageIndex by remember { mutableStateOf(0) }

            Image(
                painter = rememberAsyncImagePainter(model = item.imageUrls?.get(currentImageIndex)),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
                    .clip(RoundedCornerShape(35.dp)),
                contentScale = ContentScale.Crop
            )

            if (item.imageUrls?.size!! > 1) {
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(horizontal = 8.dp)
                ) {
                    item.imageUrls?.forEachIndexed { index, _ ->
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(if (index == currentImageIndex) Color.Magenta else Color.Gray)
                                .padding(horizontal = 5.dp)
                                .pointerInput(Unit) {
                                    detectTapGestures(onTap = {
                                        currentImageIndex = index
                                    })
                                }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = "${item.name}, ${item.yearOfBirth}",
                style = MaterialTheme.typography.titleLarge,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Description",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(bottom = 8.dp),
            fontSize =  20.sp,
        )
        Text(
            text = item.description,
            style = MaterialTheme.typography.labelSmall,
            color = Color.Black,
            fontSize =  18.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Interests",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(bottom = 8.dp),
            fontSize =  20.sp
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
        ) {
            item.interest.forEach { interest ->
                Text(
                    text = interest,
                    fontSize =  18.sp,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .background(Color.LightGray, shape = RoundedCornerShape(16.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
    }
}
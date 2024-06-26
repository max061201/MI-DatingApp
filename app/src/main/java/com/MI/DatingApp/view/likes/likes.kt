package com.MI.DatingApp.view.likes

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.MI.DatingApp.R
import com.MI.DatingApp.model.User
import com.MI.DatingApp.view.home.Item
import com.MI.DatingApp.view.home.UserDetail
import com.MI.DatingApp.viewModel.likes.LikesVM


@Composable
fun Likes(likesVM: LikesVM = viewModel()) {
    val receivedLikesUsers by likesVM.receivedLikesUsersLiveData.observeAsState(initial = emptyList())
    Log.d("receivedLikesUsers", receivedLikesUsers.toString())
    var showUserDetail by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf<User?>(null) }

    var likes = receivedLikesUsers

    if (showUserDetail) {
        UserDetail(item = selectedItem!! , onBack = {
            showUserDetail = false
        })
    } else {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFFFCF7F7), Color(0xFFAA3FEC))
                    )
                )
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                CurvedBox {
                    UnderlinedText(
                        text = "Likes",
                        color = Color.Black,
                        underlineColor = Color(0xFF58C3B6)

                    )

                }

                Text(
                    text = "${likes.size} Likes",
                    color = Color.Black,
                    modifier = Modifier.padding(16.dp),
                    fontWeight = FontWeight.Bold,
                )

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.padding(8.dp)
                ) {
                    items(likes.size) { index  ->
                        val user = likes[index]
                        val imageUrl = user.imageUrls?.firstOrNull()
                        Box(
                            modifier = Modifier
                                .padding(8.dp)
                                .aspectRatio(1f)
                                .background(Color.White, RoundedCornerShape(8.dp))
                                .clickable(onClick = {
                                    showUserDetail = true
                                    selectedItem = user

                                })
                        ) {

                            Image(
                                painter = rememberAsyncImagePainter(model = imageUrl),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )

                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(5.dp)
                                    .shadow(6.dp)

                            ) {
                                Column(
                                    modifier = Modifier
                                        .align(Alignment.BottomStart)
                                        .fillMaxWidth()
                                        .background(Color.Black.copy(alpha = 0.5f))
                                        .padding(8.dp)
                                ) {
                                    androidx.compose.material3.Text(
                                        text = user.name,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = Color.White
                                    )
                                }
                            }


//                            Text(
//                                text = user.name,
//                                color = Color.White,
//                                modifier = Modifier.padding(top=150.dp, start = 40.dp),
//                                )

                        }
                    }

                }

            }
        }
}
    }
}


@Composable
fun CurvedBox(
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent),

        ) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),

            ) {
            val width = size.width
            val height = size.height

            val path = Path().apply {
                moveTo(0f, 0f)
                lineTo(0f, height - 50)
                cubicTo(
                    width / 2, height,
                    width / 2, height,
                    width, height - 50
                )
                lineTo(width, 0f)
                close()
            }

            drawPath(path, color = Color.White, style = Fill)
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 90.dp),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }
}


@Composable
fun UnderlinedText(text: String, color: Color, underlineColor: Color) {
    Column(
        modifier = Modifier
            .padding(bottom = 2.dp) // Padding to avoid clipping the underline
    ) {
        Text(
            text = text,
            color = color,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Canvas(
            modifier = Modifier

                .height(2.dp)
        ) {
            drawLine(
                color = underlineColor,
                start = Offset(0f, 0f),
                end = Offset(90f, 0f),
                strokeWidth = size.height
            )
        }
    }
}

@Preview
@Composable
fun prview() {
    Likes()
}
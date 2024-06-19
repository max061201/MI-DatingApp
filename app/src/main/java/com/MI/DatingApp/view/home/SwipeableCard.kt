package com.MI.DatingApp.view.home

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.zIndex
import coil.compose.rememberAsyncImagePainter
import com.MI.DatingApp.R
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

data class Item(
    val imageUrl: String,
    val profession: String,
    val name: String
)

@Composable
fun SwipeCardDemo(){
    val accounts =  mutableListOf(
        Item("https://images.unsplash.com/photo-1668069574922-bca50880fd70?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=687&q=80", "Musician", "Alice (25)"),
        Item("https://images.unsplash.com/photo-1618641986557-1ecd230959aa?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=687&q=80", "Developer", "Chris (33)"),
        Item("https://images.unsplash.com/photo-1667935764607-73fca1a86555?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=688&q=80", "Teacher", "Roze (22)")
    )
    SwipeCardDemoList(accounts = accounts)
}

@Composable
fun CardContent(item: Item) {

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = rememberAsyncImagePainter(model = item.imageUrl),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                // .align(Alignment.BottomStart)
                .background(Color.Black.copy(alpha = 0.5f))
                .padding(8.dp)
        ) {
            Text(text = item.name, color = Color.White)
            Text(text = item.profession, color = Color.White)
        }
    }

}

@Composable
fun SwipeableCard(
    item: Item,
    modifier: Modifier = Modifier,
    onSwipedLeft: () -> Unit,
    onSwipedRight: () -> Unit,
    animationSpec: AnimationSpec<Float> = tween(durationMillis = 300)
) {
    val screenWidth = with(LocalDensity.current) {
        LocalConfiguration.current.screenWidthDp.dp.toPx()
    }

    var offsetX by rememberSaveable { mutableFloatStateOf(0f) }
    val animatableOffsetX = remember { Animatable(0f) }
    val animatableRotation = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()
    var showLeftIcon by remember { mutableStateOf(false) }
    var showRightIcon by remember { mutableStateOf(false) }

    val handleSwipeEnd: () -> Unit = {
        coroutineScope.launch {
            if (offsetX > screenWidth / 3) {
                animatableOffsetX.animateTo(screenWidth, animationSpec)
                animatableRotation.animateTo(10f, animationSpec)
                onSwipedRight()
                showRightIcon = false
            } else if (offsetX < -screenWidth / 3) {
                animatableOffsetX.animateTo(-screenWidth, animationSpec)
                animatableRotation.animateTo(-10f, animationSpec)
                onSwipedLeft()
                showLeftIcon = false
            } else {
                animatableOffsetX.animateTo(0f, animationSpec)
                animatableRotation.animateTo(0f, animationSpec)
                showLeftIcon = false
                showRightIcon = false
            }
        }
    }


    LaunchedEffect(offsetX) {
        animatableOffsetX.snapTo(offsetX)
        handleSwipeEnd()
    }

    Box(
        modifier = modifier
            .offset { IntOffset(animatableOffsetX.value.roundToInt(), 0) }
            .graphicsLayer(rotationZ = animatableRotation.value)
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onDragEnd = { handleSwipeEnd() }
                ) { change, dragAmount ->
                    change.consume()
                    offsetX += dragAmount
                    coroutineScope.launch {
                        animatableRotation.snapTo(offsetX / screenWidth * 30)
                    }
                    showLeftIcon = offsetX < -screenWidth / 3
                    showRightIcon = offsetX > screenWidth / 3
                }
            }
    ) {
        // Card content
        CardContent(item)

        if (showLeftIcon) {
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(100.dp)
                    //.background(Color.Red, shape = RoundedCornerShape(50))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_cross1),
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
        if (showRightIcon) {
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(100.dp)
                   // .background(Color.Green, shape = RoundedCornerShape(50))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_heart2),
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }

}

@Composable
fun ControlButtons(
    onLeftSwipe: () -> Unit,
    onRightSwipe: () -> Unit,
    onUndo: () -> Unit,
    modifier: Modifier = Modifier
) {
   Row(
       horizontalArrangement = Arrangement.SpaceAround,
       verticalAlignment = Alignment.CenterVertically,
       modifier = modifier.fillMaxWidth()
        ) {
            IconButton(onClick = onLeftSwipe) {
               Image(
                    painter = painterResource(id = R.drawable.ic_cross1),
                    contentDescription = null,
                    modifier = Modifier.size(48.dp)
                )
            }
            IconButton(onClick = onUndo) {
                Image(
                    painter = painterResource(id = R.drawable.ic_undo),
                    contentDescription = null,
                    modifier = Modifier.size(48.dp)
                )
            }
            IconButton(onClick = onRightSwipe) {
                Image(
                    painter = painterResource(id = R.drawable.ic_heart2),
                    contentDescription = null,
                    modifier = Modifier.size(48.dp)
                )
            }

    }

}




@Composable
fun SwipeCardDemoList(accounts: List<Item>) {
    var currentIndex by rememberSaveable  { mutableIntStateOf(0) }

    Box(modifier = Modifier.fillMaxSize()) {
        if (currentIndex < accounts.size) {
            accounts.asReversed().forEachIndexed { index, item ->
                val actualIndex = accounts.size - 1 - index
                if (actualIndex >= currentIndex) {
                    SwipeableCard(
                        item = item,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp)
                            .padding(16.dp)
                            .zIndex(actualIndex.toFloat()),
                        onSwipedLeft = {
                            if (actualIndex == currentIndex) currentIndex++
                        },
                        onSwipedRight = {
                            if (actualIndex == currentIndex) currentIndex++
                        }
                    )
                }
            }

        } else {
            Text("No more profiles to show", color = Color.Black , modifier = Modifier.align(Alignment.Center))
        }

        ControlButtons(
            onLeftSwipe = {
                if (currentIndex < accounts.size) {
                    currentIndex++
                }
            },
            onRightSwipe = {
                if (currentIndex > 0) {
                    currentIndex--
                }
            },
            onUndo = {
                if (currentIndex > 0) {
                    currentIndex--
                }
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )

    }
}

@Preview
@Composable
fun PreviewSwipeCardDemo() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        SwipeCardDemo()
    }

}

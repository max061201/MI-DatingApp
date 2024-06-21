package com.MI.DatingApp.view.home

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
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
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.MI.DatingApp.R
import com.MI.DatingApp.ui.theme.ComposeBottomNavigationExampleTheme
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

data class Item(
    val imageUrl: String,
    val profession: String,
    val name: String
)

@Composable
fun SwipeCardDemo() {
    val accounts = mutableListOf(
        Item("https://images.unsplash.com/photo-1668069574922-bca50880fd70?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=687&q=80", "Musician", "Alice (25)"),
        Item("https://images.unsplash.com/photo-1618641986557-1ecd230959aa?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=687&q=80", "Developer", "Chris (33)"),
        Item("https://images.unsplash.com/photo-1667935764607-73fca1a86555?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=688&q=80", "Teacher", "Roze (22)")
    )
    SwipeCardDemoList(accounts = accounts)
}

@Composable
fun CardContent(item: Item) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
            .shadow(16.dp)

    ) {
        Image(
            painter = rememberAsyncImagePainter(model = item.imageUrl),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
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
                    .background(Color.White, shape = RoundedCornerShape(50))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_heart1),
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
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom,
        modifier = modifier
            .fillMaxWidth()

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
    var currentIndex by rememberSaveable { mutableIntStateOf(0) }


    val screenWidth = with(LocalDensity.current) {
        LocalConfiguration.current.screenWidthDp.dp.toPx()
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier
                .height(500.dp)
                .padding(top = 50.dp)
        ){
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
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
                    Text("No more profiles to show", color = Color.Black, modifier = Modifier.align(Alignment.Center))
                }
            }
        }

        val swipeLeft: () -> Unit = {
            if (-currentIndex < -accounts.size ) {
                currentIndex++
            }
            println("CurrentIndex: $currentIndex < $accounts.size" )
        }

        val swipeRight: () -> Unit = {
            if (-currentIndex  < -accounts.size  ) {
                currentIndex++
            }
        }

        val undo: () -> Unit = {
            if (currentIndex > 0) {
                currentIndex--
            }
        }
            Text(text = "CurrentIndex $currentIndex < ${accounts.size} AccountSize", color = Color.Black)
        Text(text="", color = Color.Black)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 80.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally,

        ){
            ControlButtons(
                onLeftSwipe = swipeLeft,
                onRightSwipe = swipeRight,
                onUndo = undo,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    val navController = rememberNavController()
    ComposeBottomNavigationExampleTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            SwipeCardDemo()
        }
    }
}

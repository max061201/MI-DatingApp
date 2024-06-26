package com.MI.DatingApp.view.home

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.MI.DatingApp.R
import com.MI.DatingApp.viewModel.user.UserViewModel
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

data class Item(
    var name: String,
    var yearOfBirth: String,
    var profilsImage: String,
    var gender: String ,
    var genderLookingFor: String ,
    var imageUrls: MutableList<String>? = mutableListOf(),
    var description: String,
    var interest: MutableList<String> = mutableListOf(),
)


@Composable
fun SwipeCardDemo(viewModel: UserViewModel = viewModel()) {
    SwipeCardDemoList()
}

@Composable
fun CardContent(item: Item) {
    Box {
        Image(
            painter = rememberAsyncImagePainter(model = item.imageUrls?.get(0)),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .background(Color.Black.copy(alpha = 0.5f))
                .padding(8.dp)
        ) {
            Text(text =" ${item.name}, ${item.yearOfBirth}", style = MaterialTheme.typography.titleLarge, color = Color.White)
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
            if (offsetX > screenWidth / 4) {
                animatableOffsetX.animateTo(screenWidth, animationSpec)
                animatableRotation.animateTo(7f, animationSpec)
                onSwipedRight()
                showRightIcon = false
            } else if (offsetX < -screenWidth / 4) {
                animatableOffsetX.animateTo(-screenWidth, animationSpec)
                animatableRotation.animateTo(-7f, animationSpec)
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
fun SwipeCardDemoList(userViewModel: UserViewModel = viewModel()) {
    var currentIndex by rememberSaveable { mutableIntStateOf(0) }
    val userList by userViewModel.usersListLiveData.observeAsState(initial = emptyList())
    var showUserDetail by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf<Item?>(null) }

    // Reset currentIndex when the userList changes
    LaunchedEffect(userList) {
       // currentIndex = 0
        val userNames = userList.map { it.name }
        Log.d("userList SwipeCardDemoList", userNames.joinToString(", "))

    }

    val accounts = userList.map { user ->
        val imageUrl = if (!user.imageUrls.isNullOrEmpty()) {
            user.imageUrls!![0].toString()
        } else {""}

        Item(
            name = user.name,
       yearOfBirth = user.yearOfBirth,
        profilsImage = imageUrl,
        gender = user.gender,
        genderLookingFor = user.genderLookingFor,
        imageUrls =  user.imageUrls,
        description = user.description,
        interest = user.interest,
        )
    }

//    if (showUserDetail && selectedItem != null) {
//        UserDetail(item = selectedItem!!, onBack = {
//            showUserDetail = false
//        })
//    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier
                    .height(600.dp)
                    .padding(top = 50.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    if (currentIndex < accounts.size) {
                        accounts.asReversed().forEachIndexed { index, item ->
                            val actualIndex = accounts.size - 1 - index
                            if (actualIndex >= currentIndex) {

                                SwipeableCard(
                                    item = accounts[actualIndex],
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                        .zIndex(actualIndex.toFloat())
                                        .pointerInput(Unit) {
                                            detectTapGestures(onTap = {
                                                selectedItem = accounts[actualIndex]
                                                showUserDetail = true
                                            })
                                        },
                                    onSwipedLeft = {
                                        if (actualIndex == currentIndex) currentIndex++
                                        val dislikedUser = userList[actualIndex]
                                        userViewModel.dislike(dislikedUser)
                                    },
                                    onSwipedRight = {
                                        if (actualIndex == currentIndex) currentIndex++
                                        val likedUser = userList[actualIndex]
                                        userViewModel.like(likedUser)
                                    }
                                )
                            }
                        }
                    } else {
                        Text("No more profiles to show", color = Color.Black, style = MaterialTheme.typography.titleLarge , modifier = Modifier.align(Alignment.Center))
                    }
                }
            }

            val swipeLeft: () -> Unit = {
                if (currentIndex < accounts.size) {
                    currentIndex++
                }
                println("CurrentIndex: $currentIndex < $accounts.size" )
            }

            val swipeRight: () -> Unit = {
                if (currentIndex  < accounts.size ) {
                    currentIndex++
                }
            }

            val undo: () -> Unit = {
                if (currentIndex > 0) {
                    currentIndex--
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally,
            ){
/*                ControlButtons(
                    onLeftSwipe = swipeLeft,
                    onRightSwipe = swipeRight,
                    onUndo = undo,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )*/
            }
        }
    }
//}

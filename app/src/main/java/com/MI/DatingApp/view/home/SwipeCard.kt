package com.MI.DatingApp.view.home

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun SwipeCard(
    cardContent: @Composable () -> Unit,
    onSwipeLeft: () -> Unit,
    onSwipeRight: () -> Unit
) {
    var cardOffset by remember { mutableStateOf(Offset(0f, 0f)) }
    var cardSize by remember { mutableStateOf(IntSize.Zero) }
    val cardWidth = with(LocalDensity.current) { cardSize.width.toFloat() }

    val swipeThreshold = cardWidth / 3

    val animatableOffset = remember { Animatable(Offset(0f, 0f), Offset.VectorConverter) }
    val animationSpec = tween<Offset>(durationMillis = 300, easing = LinearOutSlowInEasing)

    val coroutineScope = rememberCoroutineScope()

    suspend fun handleSwipeComplete() {
        when {
            cardOffset.x > swipeThreshold -> {
                onSwipeRight()
                animatableOffset.snapTo(Offset(0f, 0f))
            }
            cardOffset.x < -swipeThreshold -> {
                onSwipeLeft()
                animatableOffset.snapTo(Offset(0f, 0f))
            }
            else -> {
                animatableOffset.animateTo(Offset(0f, 0f), animationSpec)
            }
        }
        cardOffset = Offset(0f, 0f)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .onGloballyPositioned { layoutCoordinates ->
                cardSize = layoutCoordinates.size
            }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragEnd = {
                        coroutineScope.launch {
                            handleSwipeComplete()
                        }
                    }
                ) { change, dragAmount ->
                    change.consume()
                    cardOffset += dragAmount
                    coroutineScope.launch {
                        animatableOffset.snapTo(cardOffset)
                    }
                }
            }
    ) {
        Box(
            modifier = Modifier
                .offset { IntOffset(animatableOffset.value.x.roundToInt(), animatableOffset.value.y.roundToInt()) }
                .size(300.dp, 400.dp)
                .background(Color.White, RoundedCornerShape(16.dp))
        ) {
            cardContent()
        }
    }
}


@Composable
fun SwipeCardDemo() {
    var cardIndex by remember { mutableStateOf(0) }
    val cards = listOf("Card 1", "Card 2", "Card 3")

    if (cardIndex < cards.size) {
        SwipeCard(
            cardContent = {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = cards[cardIndex], style = MaterialTheme.typography.displayMedium)
                }
            },
            onSwipeLeft = {
                cardIndex++
            },
            onSwipeRight = {
                cardIndex++
            }
        )
    } else {
        Text("No more cards", style = MaterialTheme.typography.displayMedium, modifier = Modifier.
        fillMaxSize().
        wrapContentSize(Alignment.Center))
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewSwipeCardDemo() {
    SwipeCardDemo()
}

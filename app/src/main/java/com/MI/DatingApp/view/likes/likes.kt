package com.MI.DatingApp.view.likes

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp



@Composable
fun Likes(){
    var likes= mutableListOf("1","2","1","2","1","2","7")
    Text(text = "Text", color = Color.Black)

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
        ){
            Column(
                modifier = Modifier.fillMaxSize()
            ){
                CurvedBox {
                    UnderlinedText(text = "Likes",
                        color = Color.Black,
                        underlineColor= Color(0xFF58C3B6)

                    )

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
                    width / 2, height ,
                    width / 2, height ,
                    width, height-50
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
        Canvas(modifier = Modifier

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
fun prview(){
    Likes()
}
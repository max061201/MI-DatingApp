package com.MI.DatingApp.view.recyclableGlobal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.MI.DatingApp.R

@Composable
fun TitlePages(titleText: String, modifier: Modifier = Modifier){
    Box {
        Text(
            text = titleText,
            style = MaterialTheme.typography.titleLarge,
            color = Color.Black,
            modifier = modifier
        )
        Box(
            modifier = Modifier
                .padding(top = 20.dp)
                .size(width = 30.dp, height = 7.dp)
                .background(Color(0xFF4EDDB5), shape = RoundedCornerShape(2.dp))
                .align(Alignment.Center)
        )
    }
}


@Composable
fun IconWithText(modifierText: Modifier = Modifier, modifierIcon: Modifier = Modifier){
    Icon(
        painter = painterResource(id = R.drawable.logo),
        contentDescription = null,
        tint = Color.Red,
        modifier = modifierIcon
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
        modifier = modifierText
    )
}
package com.MI.DatingApp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.MI.DatingApp.R

val outletFontRegularStandard = FontFamily(
    Font(R.font.outfit_regular)
)

val outletFontBoldTitle= FontFamily(
    Font(R.font.outfit_regular),
    Font(R.font.outfit_bold, weight = FontWeight.Bold),
)


// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = outletFontRegularStandard,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
        color = Color.White

    ),

    titleLarge = TextStyle(
        fontFamily =  outletFontBoldTitle,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.sp,
        color = Color.White
    ),

    labelSmall = TextStyle(
        fontFamily = outletFontRegularStandard,
        fontSize = 14.sp,
        letterSpacing = 0.1.sp,
        lineHeight = 20.sp,
        color = Color.White
    )

    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)
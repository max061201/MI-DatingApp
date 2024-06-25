package com.MI.DatingApp.view.registieren.recyclable

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.MI.DatingApp.R

data class TextRegistering(val title: String = "", val subTitle: String)


val RegisteringTexts = TextRegistering(
    title = "SignUp to Chat&Meet",
    subTitle = "start chating with people close to you \n signing up to our chat application"
)

val RegisteringImageText = TextRegistering(
    subTitle = "You can already select your first \n              profile photo"

)

data class OutletAttribute(
    val text: String,
    val textFieldColors: OutLineTextColor
)

data class OutLineTextColor(
    val focusedBorderColor: Color,
    val cursorColor: Color,
    val textColor: Color,
    val errorBorderColor: Color,
    val textStyle: Color
)



var outletAttributeRegisPage1 = mutableListOf(
    OutletAttribute(
        text = "name",
        OutLineTextColor(
            focusedBorderColor = Color.White, // Change border color when focused
            cursorColor = Color.White, // Change cursor color
            textColor = Color.Black, // Change text color
            errorBorderColor = Color.Red,
            textStyle = Color.White

        )
    ),
    OutletAttribute(
        text = "Email",

        OutLineTextColor(
            focusedBorderColor = Color.White, // Change border color when focused
            cursorColor = Color.White, // Change cursor color
            textColor = Color.Black, // Change text color
            errorBorderColor = Color.Red,
            textStyle = Color.White
        )
    ),

    OutletAttribute(
        text = "password",

        OutLineTextColor(
            focusedBorderColor = Color.White, // Change border color when focused
            cursorColor = Color.White, // Change cursor color
            textColor = Color.Black, // Change text color
            errorBorderColor = Color.Red,
            textStyle = Color.White
        )
    ),

    OutletAttribute(
        text = "confirm Password",
        OutLineTextColor(
            focusedBorderColor = Color.White, // Change border color when focused
            cursorColor = Color.White, // Change cursor color
            textColor = Color.Black, // Change text color
            errorBorderColor = Color.Red,
            textStyle = Color.White
        )
    ),


    )

var outletAttributeRegisPage2 = mutableListOf(
    OutletAttribute(
        text = "your Breath",
        OutLineTextColor(
            focusedBorderColor = Color.White, // Change border color when focused
            cursorColor = Color.White, // Change cursor color
            textColor = Color.Black, // Change text color
            errorBorderColor = Color.Red,
            textStyle = Color.White

        )
    ),
    OutletAttribute(
        text = "Gender",
        OutLineTextColor(
            focusedBorderColor = Color.White, // Change border color when focused
            cursorColor = Color.White, // Change cursor color
            textColor = Color.Black, // Change text color
            errorBorderColor = Color.Red,
            textStyle = Color.White
        )
    ),
)

var outletAttributeRegisPage3 = mutableListOf(
    OutletAttribute(
        text = "",
        OutLineTextColor(
            focusedBorderColor = Color.White, // Change border color when focused
            cursorColor = Color.White, // Change cursor color
            textColor = Color.Black, // Change text color
            errorBorderColor = Color.Red,
            textStyle = Color.White

        )
    ),
    OutletAttribute(
        text = "Gender",
        OutLineTextColor(
            focusedBorderColor = Color.White, // Change border color when focused
            cursorColor = Color.White, // Change cursor color
            textColor = Color.Black, // Change text color
            errorBorderColor = Color.Red,
            textStyle = Color.White
        )
    ),
)

var userInterests:Map<String,Int> = mapOf(
    "Travel" to R.drawable.baseline_airplanemode_active_24,
    "Sport" to R.drawable.baseline_sports_handball_24,
    "Cinma" to R.drawable.baseline_local_movies_24,
    "Shoping" to R.drawable.baseline_shopping_cart_24,

)
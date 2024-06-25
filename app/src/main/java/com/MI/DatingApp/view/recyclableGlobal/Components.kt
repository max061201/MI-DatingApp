package com.MI.DatingApp.view.recyclableGlobal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.MI.DatingApp.R

@Composable
fun TitleGlobal(titleText: String, modifier: Modifier = Modifier){
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
fun AppIcon( modifierIcon: Modifier = Modifier) {
    Icon(
        painter = painterResource(id = R.drawable.logo),
        contentDescription = null,
        tint = Color.Red,
        modifier = modifierIcon
    )
}

@Composable
fun TextGlobal(isStandard: Boolean = true, idText : Int = 0, text: String = "", modifier: Modifier = Modifier){
    Text(
        text = stringResource(idText),
        style = if (isStandard) MaterialTheme.typography.labelSmall  else   MaterialTheme.typography.titleLarge,
        color = Color.White,
        modifier = modifier
    )
}

@Composable
fun LoginRegisterHeader(idTitle: Int, idSubtitle: Int){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 100.dp).background(Color.Red)
    ) {
        AppIcon( modifierIcon =  Modifier
            .size(100.dp)
            .clip(MaterialTheme.shapes.large))
        TextGlobal(isStandard = false ,idTitle,
            modifier = Modifier.padding(top = 16.dp))
        TextGlobal(isStandard = true ,idSubtitle,
            modifier = Modifier.padding(top = 8.dp).width(250.dp))
    }
}


@Composable
fun IconWithText(modifierText: Modifier = Modifier, modifierIcon: Modifier = Modifier
    .size(60.dp)
    .blur(4.dp)){
    Box(){
        AppIcon( modifierIcon)
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
}

@Composable
fun OutlinedTextFieldGlobal(
    textValue: String,
    onValueChanged: (String) -> Unit,
    label: String,
    isPassword: Boolean = false,
    imeAction: ImeAction = ImeAction.None,
    focusedBorderColor: Color = Color.White,
    unfocusedBorderColor: Color = Color.White,
    modifier: Modifier = Modifier
){
    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = textValue,
        onValueChange = onValueChanged,
        label = { Text(text = label, style = MaterialTheme.typography.labelSmall) },
        modifier = modifier,
        textStyle = TextStyle(color = Color.White),
        trailingIcon = {
            if (isPassword) {
                val image =
                    if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = null)
                }
            }
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = focusedBorderColor,
            unfocusedBorderColor = unfocusedBorderColor
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = imeAction
        ),
        visualTransformation = if (isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
    )
}
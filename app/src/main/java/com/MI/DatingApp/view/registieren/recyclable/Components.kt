package com.MI.DatingApp.view.registieren.recyclable

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.MI.DatingApp.R
import com.MI.DatingApp.viewModel.registering.Error
import com.MI.DatingApp.viewModel.registering.RegisteringVM
import com.MI.DatingApp.viewModel.registering.formatAndToString
import java.util.Date


/**
Base Components that are used in Regestrieren
 */
@Composable
fun BaseText(textUnit: TextUnit, text: String, color: Color) {
    Text(
        fontSize = textUnit,
        text = text,
        color = color,
        style = MaterialTheme.typography.titleLarge
    )
}

@Composable
fun RegistFirstItems(fullCycle: Int = 1) {
    AppIcon()
    BaseText(textUnit = 20.sp, text = RegisteringTexts.title, color = Color.White)
    BaseText(textUnit = 16.sp, text = RegisteringTexts.subTitle, color = Color.White)
    Circles(fullCycle)

}

@Composable
fun errorMessage(messages: MutableList<Error>) {
    if (messages.isNotEmpty()) {
        Text(
            text = "please check your " + messages[0].errorType,
            color = Color.Red,
        )
    }

}


@Composable
fun BasicOutlineText(
    onValueChange: (String) -> Unit,
    outletAttribute: OutletAttribute,
    textValue: String,
    isPassword: Boolean = false

) {
    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = textValue,
        onValueChange = onValueChange,
        label = { Text(outletAttribute.text, color = Color.White) },
        trailingIcon = {
            if (isPassword) {
                val image =
                    if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = null)
                }
            }
        },
        visualTransformation = if (isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = outletAttribute.textFieldColors.focusedBorderColor, // Change border color when focused
            cursorColor = outletAttribute.textFieldColors.cursorColor, // Change cursor color
            textColor = outletAttribute.textFieldColors.textColor, // Change text color
            unfocusedBorderColor = Color.White,
        )
    )
}

@Composable
fun Circle(fullCycle: Boolean) {
    if (fullCycle) {
        Canvas(modifier = Modifier.size(16.dp)) {
            val center = Offset(size.width / 2, size.height / 2)
            val radius = size.width / 4 // Adjust the radius as needed

            drawCircle(
                color = Color.White,  // Change color as needed
                center = center,
                radius = radius  // Change stroke width as needed
            )
        }
    } else {
        Canvas(modifier = Modifier.size(16.dp)) {
            val center = Offset(size.width / 2, size.height / 2)
            val radius = size.width / 4 // Adjust the radius as needed

            drawCircle(
                color = Color.White,  // Change color as needed
                center = center,
                radius = radius,
                style = Stroke(width = 2f)  // Change stroke width as needed
            )
        }
    }
}

@Composable
fun Circles(stepByStep: Int = 1) {
    val twoCycle: Boolean = stepByStep == 2 || stepByStep == 3
    val thirdCycle = stepByStep == 3
    Row(
    ) {
        Circle(true)
        Spacer(modifier = Modifier.width(30.dp))
        Circle(twoCycle)
        Spacer(modifier = Modifier.width(30.dp))
        Circle(thirdCycle)
    }
}

@Composable
fun ButtonCompose(onClick: () -> Unit, text: String = "Next") {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(vertical = 50.dp)
            .width(300.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.Black
        )
    ) {
        Text(text = text, color = Color.Black)
    }
}

@Composable
fun DatePickerTextField(
    value: String,
    reg: Boolean = true,
    setDate: (date: String) -> Unit,

    ) {
    var showDialog by remember { mutableStateOf(false) }

    val colorText: Color
    val backgroundColor: Color
    val lineColor: Color
    if (reg) {
        colorText = Color.White
        backgroundColor = Color.Transparent
        lineColor = Color.White
    } else {
        colorText = Color.Black
        backgroundColor = Color.White
        lineColor = Color.Black
    }

    TextField(

        value = value,
        onValueChange = { /* Ignoring manual input for now */ },
        label = { Text("Date", color = colorText) },
        readOnly = true, // Prevents manual input
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        ),

        keyboardActions = KeyboardActions(
            onDone = {
                showDialog = true
            }
        ),
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.CalendarToday,
                contentDescription = null,
                modifier = Modifier.clickable { showDialog = true }
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = backgroundColor,
            focusedIndicatorColor = lineColor,
            unfocusedIndicatorColor = lineColor,

            )
    )
    Spacer(modifier = Modifier.height(8.dp))
    if (showDialog) {

        DatePickerDialogCo(showDialog = showDialog) {
            setDate(it)
        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialogCo(showDialog: Boolean,setDate: (date: String) -> Unit) {
    val datePickerState = rememberDatePickerState(
        initialDisplayedMonthMillis = System.currentTimeMillis(),
        yearRange = 1900..2024
    )
    val showDatePicker = remember { mutableStateOf(showDialog) }

    if (showDatePicker.value) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker.value = true },
            confirmButton = {
                TextButton(
                    onClick = { showDatePicker.value = false },
                    enabled = datePickerState.selectedDateMillis != null
                ) {
                    Text(text = "Confirm")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker.value = false }) {
                    Text(text = "Dismiss")
                }
            },
            colors = DatePickerDefaults.colors(
                containerColor = Color.Black,
                yearContentColor = Color.White,
                currentYearContentColor = Color.Blue,
                titleContentColor = Color.White,
                headlineContentColor = Color.White,
                weekdayContentColor = Color.Gray,
                subheadContentColor = Color.LightGray,
                dayContentColor = Color.White,
                selectedDayContentColor = Color.Blue,
                todayContentColor = Color.Green,


                )


        ) {

            DatePicker(
                state = datePickerState
            )
            if (datePickerState.selectedDateMillis != null) {
                setDate(Date(datePickerState.selectedDateMillis!!).formatAndToString())
            }

        }
    }
}

@Composable
fun Gander(
    outletAttribute: OutletAttribute,
    registeringViewModel: RegisteringVM
) {
    var showDialog by remember { mutableStateOf(false) }


    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = registeringViewModel.user.value!!.gender,
        onValueChange = { /* Ignoring manual input for now */ },
        label = { Text("Your Gander", color = Color.White) },
        readOnly = true, // Prevents manual input
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                showDialog = true
            }
        ),
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null,
                modifier = Modifier.clickable { showDialog = true }
            )
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = outletAttribute.textFieldColors.focusedBorderColor, // Change border color when focused
            cursorColor = outletAttribute.textFieldColors.cursorColor, // Change cursor color
            textColor = outletAttribute.textFieldColors.textColor, // Change text color
            unfocusedBorderColor = Color.White,

            )
    )
    if (showDialog) {
        GanderDialog({
            registeringViewModel.setGander(it)
        }, {
            showDialog = false;
        })
    }

}

@Composable
fun LookingForSection(
    value: String,
    outletAttribute: OutletAttribute,
    setGeander: (data: String) -> Unit

) {
    var showDialog by remember { mutableStateOf(false) }
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        onValueChange = { /* Ignoring manual input for now */ },
        label = { Text("Your are looking for", color = Color.White) },
        readOnly = true, // Prevents manual input
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                showDialog = true
            }
        ),
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null,
                modifier = Modifier.clickable { showDialog = true }
            )
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = outletAttribute.textFieldColors.focusedBorderColor, // Change border color when focused
            cursorColor = outletAttribute.textFieldColors.cursorColor, // Change cursor color
            textColor = outletAttribute.textFieldColors.textColor, // Change text color
            unfocusedBorderColor = Color.White,

            )
    )
    if (showDialog) {
        GanderDialog(
            setGander = { setGeander(it) },
            onDismissRequest = { showDialog = false }, mutableListOf("Women", "Male")
        )
    }
}

@Composable
fun DescribesYouSection(registeringViewModel: RegisteringVM, outletAttribute: OutletAttribute) {
    OutlinedTextField(
        value = registeringViewModel.user.value!!.description,
        onValueChange = {
            registeringViewModel.describe(it)
        },
        modifier = Modifier
            .height(150.dp)
            .fillMaxWidth()
            .padding(16.dp),
        label = { Text(text = "Describes you") },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = outletAttribute.textFieldColors.focusedBorderColor, // Change border color when focused
            cursorColor = outletAttribute.textFieldColors.cursorColor, // Change cursor color
            textColor = outletAttribute.textFieldColors.textColor, // Change text color
            unfocusedBorderColor = Color.White,

            )
    )

}

@Composable
fun GanderDialog(
    setGander: (String) -> Unit,
    onDismissRequest: () -> Unit,
    containt: MutableList<String> = mutableListOf("Male", "Female")
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
                contentColor = Color.Black
            )
        ) {
            Column(
                modifier = Modifier
                    .width(300.dp)
                    .height(300.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                containt.forEach {
                    Text(
                        text = it,
                        color = Color.Black,
                        modifier = Modifier
                            .clickable {
                                setGander(it)
                                onDismissRequest()
                            }
                            .padding(8.dp)

                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

        }


    }
}

@Composable
fun UserImage(registeringViewModel: RegisteringVM) {
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
        uri?.let {
            val imagePath = it.toString() // Convert Uri to String here
            registeringViewModel.setImagePath(imagePath) // Pass the imagePath String
        }
    }

    AsyncImage(
        model = imageUri,
        contentDescription = null,
        modifier = Modifier
            .padding(4.dp)
            .width(100.dp)
            .height(100.dp)
            .clip(RoundedCornerShape(50.dp)),
        contentScale = ContentScale.Crop,
    )

    Button(onClick = {
        launcher.launch("image/*")
    }) {
        Text(text = "select image")
    }
}


@Composable
fun AppIcon() {
    Icon(
        painter = painterResource(id = R.drawable.logo),
        contentDescription = null,
        tint = Color.Red,
        modifier = Modifier
            .size(100.dp)
            .clip(MaterialTheme.shapes.large)
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Interests(
    interestsMap: Map<String, Int> = userInterests,
    setInterestes: (data: String) -> Unit,
    interest: MutableList<String>,
    reg: Boolean = true
) {
    val interests: String =
        interest.joinToString(separator = "  ")
    val colorText: Color
    val backgroundColor: Color
    val lineColor: Color
    if (reg) {
        colorText = Color.White
        backgroundColor = Color.Transparent
        lineColor = Color.White
    } else {
        colorText = Color.Black
        backgroundColor = Color.White
        lineColor = Color.Black
    }


    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(text = "your interests", color = colorText)
        TextField(
            value = interests,
            onValueChange = { setInterestes(it) },
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)

                .padding(16.dp),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = backgroundColor,
                focusedIndicatorColor = lineColor,
                unfocusedIndicatorColor = lineColor,
            ),
            textStyle = TextStyle(colorText),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
        )
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
        ) {
            interestsMap.forEach { (key, value) ->
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = {
                    setInterestes(key)

                }) {
                    Row {
                        Text(text = key)
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            painter = painterResource(id = value), // Replace with your icon resource
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                }
            }

        }

    }
}

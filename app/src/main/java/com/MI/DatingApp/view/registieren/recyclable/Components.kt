package com.MI.DatingApp.view.registieren.recyclable

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.IconButton

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextButton
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.MI.DatingApp.viewModel.registering.Error
import com.MI.DatingApp.viewModel.registering.RegisteringVM
import com.MI.DatingApp.viewModel.registering.User
import com.MI.DatingApp.viewModel.registering.formatAndToString
import java.util.Date

@Composable
fun Registering(stepByStep: Int) {
    var twoCycle: Boolean = stepByStep == 2 || stepByStep == 3
    val thirdCycle = stepByStep == 3
    Text(
        fontSize = 20.sp,
        text = RegisteringTexts.title,
        color = Color.White
    )
    Text(
        text = RegisteringTexts.subTitle,
        modifier = Modifier.padding(16.dp),
        color = Color.White
    )

    Row(
    ) {
        Circle(true)
        Spacer(modifier = Modifier.width(30.dp))
        Circle(twoCycle)
        Spacer(modifier = Modifier.width(30.dp))
        Circle(thirdCycle)
    }

    if (stepByStep == 2) {

        //  ImageSelect()
    }
}

@Composable
fun Text(textUnit: TextUnit, text: String, color: Color) {
    Text(
        fontSize = textUnit,
        text = text,
        color = color
    )
}

@Composable
fun RegistFirstItems(fullCycle: Int = 1) {
    Text(textUnit = 20.sp, text = RegisteringTexts.title, color = Color.White)
    Text(textUnit = 16.sp, text = RegisteringTexts.subTitle, color = Color.White)
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
fun ButtonCompose(onClick: () -> Unit) {
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
        Text(text = "Next")
    }
}

@Composable
fun DatePickerTextField(
    selectedDate: Date?,
    outletAttribute: OutletAttribute,
    value: User,
    registeringViewModel: RegisteringVM
) {
    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    OutlinedTextField(
        value = value.date,
        onValueChange = { /* Ignoring manual input for now */ },
        label = { Text("Date", color = Color.White) },
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
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = outletAttribute.textFieldColors.focusedBorderColor, // Change border color when focused
            cursorColor = outletAttribute.textFieldColors.cursorColor, // Change cursor color
            textColor = outletAttribute.textFieldColors.textColor, // Change text color
            unfocusedBorderColor = Color.White,

            )
    )
    if (showDialog) {
        DatePickerDialogCo(value, registeringViewModel)
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialogCo(value: User, registeringViewModel: RegisteringVM) {
    val datePickerState = rememberDatePickerState(
        initialDisplayedMonthMillis = System.currentTimeMillis(),
        yearRange = 1900..2024
    )
    val showDatePicker = remember { mutableStateOf(true) }

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
            }) {

            DatePicker(
                state = datePickerState
            )
            if (datePickerState.selectedDateMillis != null) {
                registeringViewModel.setDate(Date(datePickerState.selectedDateMillis!!).formatAndToString())
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
        value = registeringViewModel.user.value!!.gander,
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
        GanderDialog(registeringViewModel) {
            showDialog = false;
        }
    }

}

@Composable
fun GanderDialog(registeringViewModel: RegisteringVM, onDismissRequest: () -> Unit) {
    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Male",
                    modifier = Modifier
                        .clickable {
                            registeringViewModel.setGander("Male")
                            onDismissRequest()
                        }
                        .padding(8.dp)

                )
                Spacer(modifier = Modifier.height(8.dp)) // Add some space between the texts
                Text(
                    text = "Female",
                    modifier = Modifier
                        .clickable {
                            registeringViewModel.setGander("Female")
                            onDismissRequest()
                        }
                        .padding(8.dp)

                )
            }

        }


    }
}
@Composable
fun UserImage(){
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val launcher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
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

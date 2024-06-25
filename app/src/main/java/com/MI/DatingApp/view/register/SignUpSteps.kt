package com.MI.DatingApp.view.register

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.MI.DatingApp.view.recyclableGlobal.OutlinedTextFieldGlobal
import com.MI.DatingApp.viewModel.Register.SignUpViewModel
import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import java.util.*

@Composable
fun SignUpScreenStep1(viewModel: SignUpViewModel = SignUpViewModel(), onNextClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextFieldGlobal(
            textValue = viewModel.user.name,
            onValueChanged = viewModel::onNameChange,
            label = "Your name"
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextFieldGlobal(
            textValue = viewModel.user.email,
            onValueChanged = viewModel::onEmailChange,
            label = "Your email"
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextFieldGlobal(
            textValue = viewModel.user.password,
            onValueChanged = viewModel::onPasswordChange,
            label = "Password",
            isPassword = true
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextFieldGlobal(
            textValue = viewModel.user.confirmedPassword,
            onValueChanged = viewModel::onConfirmPasswordChange,
            label = "Confirm Password",
            isPassword = true
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onNextClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(horizontal = 20.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
        ) {
            Text(
                text = "Next",
                fontSize = 20.sp,
                lineHeight = 16.sp,
                color = Color.Black,
                fontWeight = FontWeight.Medium
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Existing account? Log In",
            color = Color.White,
            modifier = Modifier.clickable { /* Navigate to login screen */ }
        )
    }
}

@Composable
fun SignUpScreenStep2( viewModel: SignUpViewModel = SignUpViewModel(), onNextClick: () -> Unit) {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(LocalContext.current, { _: DatePicker, selectedYear: Int, _: Int, _: Int ->
        viewModel.onYearOfBirthChange(selectedYear.toString())
    }, year, month, day)

    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextFieldGlobal(
            textValue = viewModel.user.yearOfBirth,
            onValueChanged = { viewModel.onYearOfBirthChange(it) },
            label = "Your year of birth",
            modifier = Modifier.clickable { datePickerDialog.show() }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Box {
            OutlinedTextFieldGlobal(
                textValue = viewModel.user.gender,
                onValueChanged = {},
                label = "Your Gender",
                modifier = Modifier.clickable { expanded = !expanded }
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(onClick = {
                    viewModel.onGenderChange("Male")
                    expanded = false
                }) {
                    Text("Male")
                }
                DropdownMenuItem(onClick = {
                    viewModel.onGenderChange("Female")
                    expanded = false
                }) {
                    Text("Female")
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onNextClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(horizontal = 20.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
        ) {
            Text(
                text = "Next",
                fontSize = 20.sp,
                lineHeight = 16.sp,
                color = Color.Black,
                fontWeight = FontWeight.Medium
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Existing account? Log In",
            color = Color.White,
            modifier = Modifier.clickable { /* Navigate to login screen */ }
        )
    }
}

@Composable
fun SignUpScreenStep3(viewModel: SignUpViewModel  = SignUpViewModel(), onCreateAccountClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextFieldGlobal(
            textValue = viewModel.user.genderLookingFor,
            onValueChanged = viewModel::onLookingForChange,
            label = "You are looking for"
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextFieldGlobal(
            textValue = viewModel.user.description,
            onValueChanged = viewModel::onDescribesYouChange,
            label = "Describes you"
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextFieldGlobal(
            textValue = viewModel.user.interest.joinToString(", "),
            onValueChanged = viewModel::onInterestsChange,
            label = "Your Interests"
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onCreateAccountClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(horizontal = 20.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
        ) {
            Text(
                text = "Create Account",
                fontSize = 20.sp,
                lineHeight = 16.sp,
                color = Color.Black,
                fontWeight = FontWeight.Medium
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Existing account? Log In",
            color = Color.White,
            modifier = Modifier.clickable { /* Navigate to login screen */ }
        )
    }
}

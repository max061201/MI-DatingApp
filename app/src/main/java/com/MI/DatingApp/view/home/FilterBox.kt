package com.MI.DatingApp.view.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import com.MI.DatingApp.R
import com.MI.DatingApp.view.recyclableGlobal.TitleGlobal
import com.MI.DatingApp.viewModel.home.FilterViewModel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.MI.DatingApp.model.CurrentUser


@Composable
fun FilterBox(onDismiss: () -> Unit, filterViewModel: FilterViewModel = viewModel()) {
    // var ageRange by remember { mutableStateOf(21f..37f) }
    val currentUser = CurrentUser.getUser()

    // MutableState für den ausgewählten Gender
    var selectedGender by remember {
        mutableStateOf(currentUser?.genderLookingFor ?: "") // Standardmäßig "Male", falls currentUser null ist
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(color = Color.White, shape = RoundedCornerShape(16.dp))
            .shadow(elevation = 10.dp)
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onDismiss,  modifier = Modifier.padding(top = 30.dp)) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }

                TitleGlobal("Filter", Modifier.padding(start = 16.dp, end = 16.dp))

                IconButton(onClick = {
                    filterViewModel.updateFilterData()
                    onDismiss()
                },  modifier = Modifier.padding(top = 30.dp)) {
                    Icon(imageVector = Icons.Default.Check, contentDescription = "Apply", tint = Color(0xFFAA3FEC))
                }
            }

            Column(
                modifier = Modifier
                    .background(Color.White)
                    .padding(16.dp)
            )  {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text= "Gender",
                    color = Color.Black,
                    fontSize =  14.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = FontFamily.SansSerif,
                    modifier = Modifier.padding(10.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    GenderButton("Male", selectedGender == "Male") {
                        selectedGender = "Male"
                        filterViewModel.setGender(selectedGender) // Update filter data with the selected gender
                    }
                    GenderButton("Female", selectedGender == "Female") {
                        selectedGender = "Female"
                        filterViewModel.setGender(selectedGender) // Update filter data with the selected gender
                    }
                }
                // Add your gender selection UI here
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Age",
                    color = Color.Black,
                    fontSize =  14.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = FontFamily.SansSerif,
                    modifier = Modifier.padding(10.dp)
                )
                RangeSliderM3(filterViewModel)
            }
        }
    }

}

@Composable
fun GenderButton(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) colorResource(id = R.color.lila) else Color(0xFFE0E0E0),
            contentColor = if (isSelected) Color.White else Color.Gray
        ),
        modifier = Modifier.padding(horizontal = 4.dp)
    ) {
        Text(text = text)
    }
}


@Composable
fun RangeSliderM3(filterViewModel: FilterViewModel = viewModel()) {
    val filterData by filterViewModel.filterData.collectAsState()

    var sliderPosition by remember { mutableStateOf(filterData.ageRange.first.toFloat()..filterData.ageRange.second.toFloat()) }

    LaunchedEffect(filterData) {
        sliderPosition = filterData.ageRange.first.toFloat()..filterData.ageRange.second.toFloat()
    }

    RangeSlider(
        value = sliderPosition,
        onValueChange = {
            sliderPosition = it
            //filterViewModel.updateFilterData(
            //    gender = filterData.gender,
            //   ageRange = it.start.toInt() to it.endInclusive.toInt()
            //)
        },
        valueRange = 18f..70f,
        colors = SliderDefaults.colors(
            thumbColor = colorResource(id = R.color.lila),
            activeTrackColor = colorResource(id = R.color.lila),
            inactiveTrackColor = Color(0xFFBDBDBD)
        )
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "${sliderPosition.start.toInt()}", color = Color.Gray)
        Text(text = "${sliderPosition.endInclusive.toInt()}", color = Color.Gray)
    }
}


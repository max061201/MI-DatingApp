package com.MI.DatingApp.view.registieren

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.MI.DatingApp.model.User
import com.MI.DatingApp.view.registieren.recyclable.BasicOutlineText
import com.MI.DatingApp.view.registieren.recyclable.ButtonCompose
import com.MI.DatingApp.view.registieren.recyclable.DatePickerTextField
import com.MI.DatingApp.view.registieren.recyclable.DescribesYouSection
import com.MI.DatingApp.view.registieren.recyclable.Gander
import com.MI.DatingApp.view.registieren.recyclable.Interests
import com.MI.DatingApp.view.registieren.recyclable.LookingForSection
import com.MI.DatingApp.view.registieren.recyclable.OutletAttribute
import com.MI.DatingApp.view.registieren.recyclable.RegistFirstItems
import com.MI.DatingApp.view.registieren.recyclable.UserImage
import com.MI.DatingApp.view.registieren.recyclable.errorMessage
import com.MI.DatingApp.view.registieren.recyclable.outletAttributeRegisPage1
import com.MI.DatingApp.view.registieren.recyclable.outletAttributeRegisPage2
import com.MI.DatingApp.view.registieren.recyclable.outletAttributeRegisPage3
import com.MI.DatingApp.viewModel.registering.Error
import com.MI.DatingApp.viewModel.registering.RegisteringVM



/**
All components that build the Registrieren Page
 */

@Composable
fun Registrieren(navController: NavHostController, viewModel: RegisteringVM = viewModel()) {
    val uservalue by viewModel.user.observeAsState()
    val errorfield1 by viewModel.errorField.observeAsState()
    val registerNavController = rememberNavController()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF1C1B1B), Color(0xFFAA3FEC))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            NavHost(navController = registerNavController, startDestination = "firstPage") {
                composable("firstPage") {
                    FirstPage(
                        navController = registerNavController,
                        uservalue,
                        viewModel,
                        errorfield1
                    )

                }
                composable("secondRPage") {
                    secondRPage(registerNavController, uservalue, (viewModel as RegisteringVM))
                }

                composable("third") {
                    ThirdPage(
                        navController = navController,
                        registeringViewModel = viewModel
                    )
                }


            }
            errorMessage(errorfield1!!)
            Spacer(modifier = Modifier.height(16.dp))
            ClickableText(
                text = buildAnnotatedString {
                    append("Existing account ")
                    withStyle(
                        style = SpanStyle(
                            textDecoration = TextDecoration.Underline,
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold
                        )
                    ) {
                        append("Log In")
                    }
                },
                style = MaterialTheme.typography.labelSmall,
                onClick = {
                    navController.navigate("login")
                }
            )
        }

    }
}

/**
First page for the user to enter name, email, password if done navigate to second page
 */
@Composable
fun FirstPage(
    navController: NavController,
    uservalue: User?,
    registeringViewModel: RegisteringVM,
    error: MutableList<Error>?
) {

    Column(
        modifier = Modifier.width(300.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        RegistFirstItems()
        outletAttributeRegisPage1.forEach { outLet ->
            BasicOutlineText(
                textValue = when (outLet.text) {
                    "name" -> uservalue!!.name
                    "Email" -> uservalue!!.email
                    "password" -> uservalue!!.password
                    "confirm Password" -> uservalue!!.confirmedPassword
                    else -> ""
                },
                onValueChange = { newValue ->
                    when (outLet.text) {
                        "name" -> registeringViewModel.setName(name = newValue)
                        "Email" -> registeringViewModel.setEmail(email = newValue)
                        "password" -> registeringViewModel.setPassword(password = newValue)
                        "confirm Password" -> registeringViewModel.setConfirmedPassword(
                            confirmedPassword = newValue
                        )
                    }

                }, outletAttribute = outLet,
                isPassword = outLet.text == "password" || outLet.text == "confirm Password"
            )
        }
        ButtonCompose(onClick = {
            val moveToSecondPage = registeringViewModel.checkErrorForFirstPage()
            if (moveToSecondPage) {
                navController.navigate("secondRPage")
            }
        })


    }
}
/**
second page for the user to enter Picture date of birth and gender
 */
@Composable
fun secondRPage(
    navController: NavHostController,
    uservalue: User?,
    registeringViewModel: RegisteringVM
) {
    Column(
        modifier = Modifier.width(300.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RegistFirstItems(2)

        UserImage(registeringViewModel)
        DatePickerTextField(
            value = uservalue!!.yearOfBirth
        ) {
            registeringViewModel.setDate(it)
        }
        Gander(
            mutableListOf<OutletAttribute>(outletAttributeRegisPage2[0])[0],
            registeringViewModel
        )
        ButtonCompose({
            if (registeringViewModel.checkErrorForSecondPage()) {
                navController.navigate("third")
            }
        })
    }
}

/**
Third page for the user to enter description gender looking for and interests
 */
@Composable
fun ThirdPage(
    navController: NavHostController,
    registeringViewModel: RegisteringVM
) {
    Column(
        modifier = Modifier.width(300.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RegistFirstItems(3)
        LookingForSection(
            registeringViewModel.user.value!!.genderLookingFor,
            outletAttributeRegisPage3[0],

            ) {
            registeringViewModel.setGanderLookingFor(it)
        }
        DescribesYouSection(registeringViewModel, outletAttributeRegisPage3[1])
        Interests(
            interest = registeringViewModel.user.value!!.interest,
            setInterestes = { registeringViewModel.setInterests(it) }
            )
        ButtonCompose({


            registeringViewModel.saveUserInFirebaseAuth()
            navController.navigate("login")
        }, text = "Create Account")
    }
}
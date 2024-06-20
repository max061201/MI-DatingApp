package com.MI.DatingApp.view.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.MI.DatingApp.R
import com.MI.DatingApp.model.CurrentUser
import com.MI.DatingApp.model.User
import com.MI.DatingApp.viewModel.profile.ProfileVM

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileVM = viewModel()
) {
    val testUser = CurrentUser.getTestUser()

    val userEdit by viewModel.userchanges.observeAsState()
    val scrollState = rememberScrollState()
    viewModel.setUserValue(
        User(
            id = testUser.id,
            name = testUser.name,
            email = testUser.email,
            yearOfBirth = testUser.yearOfBirth,
            gender = testUser.gender,
            imageUrls = testUser.imageUrls,
            description = testUser.description,
            interest = testUser.interest,
            likes = testUser.likes,
            dislikes = testUser.dislikes
        )
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFFFCF7F7), Color(0xFFAA3FEC))
                    )
                )
        ) {
            item {
                CurvedBox {
                    Box(
                        modifier = Modifier
                            .clickable(onClick = { navController.navigate("home") })
                            .background(Color.White)
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.baseline_arrow_back_24),
                            contentDescription = "Back",
                            tint = androidx.compose.ui.graphics.Color.Black,
                            modifier = Modifier.size(24.dp)
                        )
                        ProfileHeader(userEdit, viewModel, firebase = testUser)
                    }
                }


            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { AccountSettings(userEdit, viewModel) }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { AboutMe(userEdit, viewModel) }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { AddImages(viewModel, userEdit!!) }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { LogoutButton(navController, viewModel) }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { DeleteAccountButton(navController, viewModel) }
            item { Spacer(modifier = Modifier.height(100.dp)) }
        }

    }

}

@Composable
fun ProfileHeader(userEdit: User?, viewModel: ProfileVM, firebase: User) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Images(viewModel, userEdit!!, 0)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            firebase.name + " ," + firebase.yearOfBirth,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun AccountSettings(userEdit: User?, viewModel: ProfileVM) {
    Card(
        shape = RoundedCornerShape(16.dp),
        backgroundColor = Color.White,
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Account Settings", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }
            Spacer(modifier = Modifier.height(8.dp))
            TextField(value = userEdit!!.name, onValueChange = {
                viewModel.setName(it)
            }, label = { Text("Name") })
            Spacer(modifier = Modifier.height(8.dp))
            TextField(value = userEdit.yearOfBirth, onValueChange = {
                viewModel.setDate(it)
            }, label = { Text("Date of Birth") })
            Spacer(modifier = Modifier.height(8.dp))
            TextField(value = userEdit.email, onValueChange = {
                viewModel.setEmail(it)
            }, label = { Text("Email") })
        }
    }
}

@Composable
fun AboutMe(userEdit: User?, viewModel: ProfileVM) {
    Card(
        shape = RoundedCornerShape(16.dp),
        backgroundColor = Color.White,
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("About me", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }
            Spacer(modifier = Modifier.height(8.dp))
            TextField(value = userEdit!!.description, onValueChange = {
                viewModel.setDesc(it)
            }, label = { Text("Description") })
            Spacer(modifier = Modifier.height(8.dp))
            TextField(value = userEdit.gender, onValueChange = {
                viewModel.setGender(it)
            }, label = { Text("Gender") })
        }
    }
}

@Composable
fun AddImages(viewModel: ProfileVM, user: User) {
    var j: Int = 0
    Card(
        shape = RoundedCornerShape(16.dp),
        backgroundColor = Color.White,
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Add Images", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Row(modifier = Modifier.padding(16.dp)) {

                Images(viewModel, user!!, 1)
                Images(viewModel, user!!, 2)
                Images(viewModel, user!!, 3)
            }
        }
    }
}

@Composable
fun LogoutButton(navController: NavController, viewModel: ProfileVM) {
    Box(
        Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = {
                viewModel.updateDataFirebase()
                navController.navigate("home")
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFAA3FEC)),
            modifier = Modifier
                .width(300.dp)
                .height(50.dp)
        ) {
            Text("Save", color = Color.White)
        }
    }
}

@Composable
fun DeleteAccountButton(navController: NavController, viewModel: ProfileVM) {
    Box(
        Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = {
                viewModel.deleteAccount()
                navController.navigate("login")
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
            modifier = Modifier
                .width(300.dp)
                .height(50.dp)
        ) {
            Text("Delete Account", color = Color.Red)
        }
    }
}

@Composable
fun Images(viewModel: ProfileVM, userEdit: User, index: Int = 0) {
    val image: String = if (userEdit.imageUrls!!.size > index) {
        userEdit.imageUrls!![index]
    } else {
        ""
    }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
        uri?.let {
            val imagePath = it.toString()
            viewModel.setImage(imagePath, image)
        }
    }
    Box(
        modifier = Modifier
            .clickable(onClick = {
                launcher.launch("image/*")
            })
            .padding(4.dp)
            .width(100.dp)
            .height(100.dp)
            .border(BorderStroke(2.dp, Color.Gray), shape = RoundedCornerShape(50.dp))
            .clip(RoundedCornerShape(50.dp))
    ) {
        AsyncImage(
            model = image,
            contentDescription = null,
            modifier = Modifier
                .padding(4.dp)
                .width(100.dp)
                .height(100.dp)
                .clip(RoundedCornerShape(50.dp)),
            contentScale = ContentScale.Crop,
        )
    }
}

@Composable
fun CurvedBox(content: @Composable BoxScope.() -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        ) {
            val width = size.width
            val height = size.height

            val path = androidx.compose.ui.graphics.Path().apply {
                moveTo(0f, 0f)
                lineTo(0f, height - 50) // Adjust to control curve height
                cubicTo(
                    width / 2, height + 50, // Control point 1
                    width / 2, height + 50, // Control point 2
                    width, height - 50
                )
                lineTo(width, 0f)
                close()
            }

            drawPath(path, color = Color.White, style = Fill)
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 50.dp) // Adjust to match curve height
        ) {
            content()
        }
    }
}

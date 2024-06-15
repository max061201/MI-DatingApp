package com.MI.DatingApp.view.profile


import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.MI.DatingApp.R
import com.MI.DatingApp.model.UserFirebase
import com.MI.DatingApp.viewModel.profile.ProfileVM
import com.MI.DatingApp.viewModel.profile.UserEdit

@Composable
fun ProfileScreen(
    navController: NavController,
    userFirebase: UserFirebase = UserFirebase(
        name = "test",
        yearOfBirth = "50",
        description = "hallo i am test ",
        interests = mutableSetOf("s", "f", "a"),
        email = "aaa",
        gender = "male",
        id = 123L,
        lookingFor = "woman",
        photos = mutableListOf(R.drawable.heart, R.drawable.delete)
    ),
    viewModle: ProfileVM = viewModel()
) {


    val userEdit by viewModle.user.observeAsState()

    viewModle.setUserValue(
        UserEdit(
            name = userFirebase.name,
            email = userFirebase.email,
            date = userFirebase.yearOfBirth,
            gander = userFirebase.gender,
            imageUrl = mutableSetOf(),
            describes = userFirebase.description
        )
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F2F2))
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF2F2F2))

        ) {
            item {
                Box(
                    modifier =
                    Modifier
                        .clickable(
                            onClick = { navController.navigate("home") })
                        .padding(5.dp)

                )
                {

                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.baseline_arrow_back_24),
                        contentDescription = "Back",
                        tint = androidx.compose.ui.graphics.Color.Black,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            item { ProfileHeader(userEdit, viewModle, firebase = userFirebase) }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { AccountSettings(userEdit, viewModle) }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { AboutMe(userEdit, viewModle) }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { DiscoverySettings(viewModle) }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { LogoutButton(navController, viewModle) }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { DeleteAccountButton(navController, viewModle) }
            item { Spacer(modifier = Modifier.height(100.dp)) }



        }
    }


}

@Composable
fun ProfileHeader(userEdit: UserEdit?, viewModle: ProfileVM,firebase: UserFirebase) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {

        Images(viewModle)
        Spacer(modifier = Modifier.height(8.dp))
        Text(firebase.name+" ,"+firebase.yearOfBirth, fontSize = 24.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun AccountSettings(userEdit: UserEdit?, viewModle: ProfileVM) {
    Card(
        shape = RoundedCornerShape(16.dp),
        backgroundColor = Color.White,
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Account Settings", fontWeight = FontWeight.Bold, fontSize = 18.sp)

            }
            Spacer(modifier = Modifier.height(8.dp))
            TextField(value = userEdit!!.name, onValueChange = {
                viewModle.setName(it)

            }, label = { Text("Name") })
            Spacer(modifier = Modifier.height(8.dp))
            TextField(value = userEdit.date, onValueChange = {
                viewModle.setDate(it)

            }, label = { Text("Date of Birth") })
            Spacer(modifier = Modifier.height(8.dp))
            TextField(value = userEdit.email, onValueChange = {
                viewModle.setEmail(it)

            }, label = { Text("Email") })
        }
    }
}

@Composable
fun AboutMe(userEdit: UserEdit?, viewModle: ProfileVM) {
    Card(
        shape = RoundedCornerShape(16.dp),
        backgroundColor = Color.White,
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("About me", fontWeight = FontWeight.Bold, fontSize = 18.sp)

            }
            Spacer(modifier = Modifier.height(8.dp))
            TextField(value = userEdit!!.describes, onValueChange = {
                viewModle.setDesc(it)

            }, label = { Text("Description") })
            Spacer(modifier = Modifier.height(8.dp))
            TextField(value = userEdit.gander, onValueChange = {
                viewModle.setGander(it)

            }, label = { Text("Gender") })
        }
    }
}

@Composable
fun DiscoverySettings(viewModle: ProfileVM) {
    Card(
        shape = RoundedCornerShape(16.dp),
        backgroundColor = Color.White,
        elevation = 4.dp
    ) {
        Column(   modifier = Modifier.padding(16.dp)) {
            Text("add Images", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Row(
                modifier = Modifier.padding(16.dp)
            ) {

                Images(viewModle)
                Images(viewModle)
                Images(viewModle)
            }
        }

    }
}

@Composable
fun LogoutButton(navController: NavController, viweModle: ProfileVM) {
    Box(
        Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = {
                viweModle.updateDataFirebase()
                navController.navigate("home")
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray),
            modifier = Modifier
                .width(300.dp)
                .height(50.dp),

            ) {
            Text("save", color = Color.White)
        }
    }

}

@Composable
fun DeleteAccountButton(navController: NavController,viewModle: ProfileVM) {
    Box(
        Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = { //TODO delete Data from firebase
                viewModle.deleteAccount()
                navController.navigate("login")
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
            modifier = Modifier
                .width(300.dp)
                .height(50.dp)

        ) {

            Text("Delete Account", color = Color.White)
        }
    }


}
@Composable
fun Images(viewModle: ProfileVM,image:String=""){
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
        uri?.let {
            val imagePath = it.toString() // Convert Uri to String here
            viewModle.setImage(imagePath)
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
            .border(BorderStroke(2.dp, Color.Gray), shape = RoundedCornerShape(50.dp)
            )
            .clip(RoundedCornerShape(50.dp))
    ){
        AsyncImage(
            model = if (imageUri == null) image else imageUri ,
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

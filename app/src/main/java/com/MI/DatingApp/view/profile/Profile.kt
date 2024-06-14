package com.MI.DatingApp.view.profile



import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.MI.DatingApp.R

@Composable
fun ProfileScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F2F2))
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        ProfileHeader()
        Spacer(modifier = Modifier.height(16.dp))
        AccountSettings()
        Spacer(modifier = Modifier.height(16.dp))
        AboutMe()
        Spacer(modifier = Modifier.height(16.dp))
        DiscoverySettings()
        Spacer(modifier = Modifier.height(16.dp))
        LogoutButton()
        Spacer(modifier = Modifier.height(8.dp))
        DeleteAccountButton()
    }
}

@Composable
fun ProfileHeader() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.heart),
            contentDescription = "Profile Picture",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text("Stefie, 21", fontSize = 24.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun AccountSettings() {
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
                Text("Edit", color = Color.Blue, modifier = Modifier.clickable { })
            }
            Spacer(modifier = Modifier.height(8.dp))
            TextField(value = "Jenny", onValueChange = {}, label = { Text("Name") }, readOnly = true)
            Spacer(modifier = Modifier.height(8.dp))
            TextField(value = "02-05-1997", onValueChange = {}, label = { Text("Date of Birth") }, readOnly = true)
            Spacer(modifier = Modifier.height(8.dp))
            TextField(value = "abcqwertyug@gmail.com", onValueChange = {}, label = { Text("Email") }, readOnly = true)
        }
    }
}

@Composable
fun AboutMe() {
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
                Text("About Me", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text("Edit", color = Color.Blue, modifier = Modifier.clickable { })
            }
            Spacer(modifier = Modifier.height(8.dp))
            TextField(value = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", onValueChange = {}, label = { Text("Description") }, readOnly = true)
            Spacer(modifier = Modifier.height(8.dp))
            TextField(value = "Female", onValueChange = {}, label = { Text("Gender") }, readOnly = true)
        }
    }
}

@Composable
fun DiscoverySettings() {
    Card(
        shape = RoundedCornerShape(16.dp),
        backgroundColor = Color.White,
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Discovery Settings", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Location", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text("My Current Location", color = Color.Blue, modifier = Modifier.clickable { })
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Show Me")
                Text("Men")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text("Age Range", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("22")
                Text("34")
            }
            Slider(value = 26f, onValueChange = {}, valueRange = 18f..60f)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Maximum Distance", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text("100km")
            Slider(value = 50f, onValueChange = {}, valueRange = 1f..100f)
        }
    }
}

@Composable
fun LogoutButton() {
    Button(
        onClick = { /* Handle logout */ },
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray),
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Text("Logout", color = Color.White)
    }
}

@Composable
fun DeleteAccountButton() {
    Button(
        onClick = { /* Handle account deletion */ },
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Text("Delete Account", color = Color.White)
    }
}
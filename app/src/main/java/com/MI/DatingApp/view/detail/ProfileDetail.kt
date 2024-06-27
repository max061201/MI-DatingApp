import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.MI.DatingApp.R
import com.MI.DatingApp.model.UserFirebase
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager


/**
Create Detail page
 Used when clicking on the user profile in Likes or Matches
 */
@Composable
fun ProfileDetail(
    navController: NavHostController,
    user: UserFirebase =
        UserFirebase(
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

    ) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0F0F0))
            .padding(16.dp)
    ) {
        TopBar(navController)

        Spacer(modifier = Modifier.height(16.dp))

        ProfileCard(user)

        Spacer(modifier = Modifier.height(16.dp))

        DescriptionSection(user)

        Spacer(modifier = Modifier.height(16.dp))

        InterestsSection(user)

        Spacer(modifier = Modifier.height(16.dp))

        ActionButtons(navController, user)
    }
}

@Composable
fun TopBar(navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(modifier = Modifier.clickable(onClick = { navController.navigate("home") })) {

            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.baseline_arrow_back_24),
                contentDescription = "Back",
                tint = androidx.compose.ui.graphics.Color.Black,
                modifier = Modifier.size(24.dp)
            )
        }


        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.weight(1f)
        ) {


            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Chat&Meet",
                style = MaterialTheme.typography.h6,
                color = androidx.compose.ui.graphics.Color.Black,
                fontSize = 20.sp
            )
        }
  /*      Box(modifier = Modifier.clickable(onClick = {})) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.baseline_filter_alt_24),
                contentDescription = "Filter",
                tint = androidx.compose.ui.graphics.Color.Black,
                modifier = Modifier.size(24.dp)
           ) */
        }

    }


@OptIn(ExperimentalPagerApi::class)
@Composable
fun ProfileCard(user: UserFirebase) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth(),
        elevation = 4.dp
    ) {
        Column() {
            HorizontalPager(
                count = user.photos.size,
                modifier = Modifier.height(300.dp)
            ) { page ->
                // Replace with your actual image resource



                val imageResId = user.photos[page % 5]

                Image(
                    painter = painterResource(id = imageResId),
                    contentDescription = "Profile Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    "${user.name}, ${user.yearOfBirth}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
                Text("5 km", color = Color.Gray)
            }
        }
    }
}

@Composable
fun DescriptionSection(user: UserFirebase) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text("Description", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = user.description,
            color = Color.Gray
        )
    }
}

@Composable
fun InterestsSection(user: UserFirebase) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text("Interests", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            user.interests.forEach {
                InterestChip(text = it)
                Spacer(modifier = Modifier.width(8.dp))

            }

        }
    }
}

@Composable
fun InterestChip(text: String) {
    Surface(
        modifier = Modifier,
        shape = RoundedCornerShape(16.dp),
        color = Color.LightGray,
        elevation = 4.dp
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            color = Color.Black
        )
    }
}

@Composable
fun ActionButtons(navController: NavHostController, user: UserFirebase) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        FloatingActionButton(
            onClick = { navController.navigate("home") },
            backgroundColor = Color.Red
        ) {
            Icon(
                painter = painterResource(id = R.drawable.delete),
                contentDescription = "Dislike", modifier = Modifier.size(24.dp)
            )
        }

        FloatingActionButton(onClick = { /*TODO*/ }, backgroundColor = Color.Green) {
            Icon(
                painter = painterResource(id = R.drawable.heart),
                contentDescription = "Like",
                modifier = Modifier.size(24.dp)
            )
        }
    }
}



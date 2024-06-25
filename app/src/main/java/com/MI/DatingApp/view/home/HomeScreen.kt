package com.MI.DatingApp.view.home


import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.MI.DatingApp.R
import com.MI.DatingApp.view.recyclableGlobal.TitleGlobal
import com.MI.DatingApp.viewModel.home.FilterViewModel

@Composable
fun HomeScreen() {
    val filterViewModel: FilterViewModel = viewModel()

    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            HeaderContent(onFilterClick = { filterViewModel.toggleFilterVisibility() })
            SwipeCardDemo()

        }
        AnimatedVisibility(
            visible = filterViewModel.isFilterVisible.collectAsState().value,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            FilterBox(onDismiss = { filterViewModel.toggleFilterVisibility() }, filterViewModel)
        }
    }
}



@Composable
fun HeaderContent(onFilterClick: () -> Unit){
    Box (contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
    ) {

        TitleGlobal("Home", Modifier.padding(start = 16.dp, end = 16.dp))

        IconButton(
           onClick =  onFilterClick,
           modifier = Modifier
               .align(Alignment.BottomEnd)
               .padding(top = 16.dp)
               .size(30.dp)
       ) {
            Icon(
                painter = painterResource(id = R.drawable.filter),
                contentDescription = "Filter",
                tint = Color.Black,
                modifier = Modifier
                    .padding(5.dp)
                    .align(Alignment.CenterEnd)
           )
       }
    }
}




@Composable
@Preview(showBackground = true)
fun PreviewHomeScreen() {
    Column(Modifier.fillMaxSize()) {
        HomeScreen()
    }

}
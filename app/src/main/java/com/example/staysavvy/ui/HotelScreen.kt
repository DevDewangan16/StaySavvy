package com.example.staysavvy.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.staysavvy.R
import com.example.staysavvy.data.HotelCategoryItem

@Composable
fun HotelScreen(
    staySavvyViewModel: StaySavvyViewModel,
    items: List<HotelCategoryItem>,
    navController: NavHostController
){
    val place by staySavvyViewModel.place.collectAsState()
    val database=items/*.filter {
        it.city.lowercase()==place.lowercase()
    }*/
    LazyColumn(contentPadding = PaddingValues(vertical = 30.dp),
        verticalArrangement = Arrangement.spacedBy(2.5.dp)){
        items(database){
            HotelCard(
                hotelImage = it.hotelPicture, hotelName = it.name, cityName = it.city, price = it.price,navController=navController)
        }
    }
}

@Composable
fun InternetHotelScreen(
    staySavvyViewModel: StaySavvyViewModel, itemUiState:StaySavvyViewModel.ItemUiState,
    navController: NavHostController
){
    when(itemUiState){
        is StaySavvyViewModel.ItemUiState.Loading->{
            LoadingScreen()
        }
        is StaySavvyViewModel.ItemUiState.Success->{
            HotelScreen(
                staySavvyViewModel = staySavvyViewModel, items = itemUiState.items,navController=navController)
        }
        else->{
            ErrorScreen(staySavvyViewModel = staySavvyViewModel)
        }
    }
}


@Composable
fun HotelCard(
    hotelImage: String,
    hotelName: String,
    cityName: String,
    price: Int,
    navController: NavHostController
){
    Card(modifier = Modifier
        .clickable {
            navController.navigate(StaySavvyScreen.Room.name)
        }
        .fillMaxWidth()
        .height(250.dp)
        .padding(5.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Black)){
        Column {
            AsyncImage(
                model = hotelImage, contentDescription ="Hotel",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp))
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {

                Text(
                        text = "${hotelName},${cityName}",
                        fontSize = 16.sp,
                        color = Color.White,
                        textAlign = TextAlign.Left,
                        modifier = Modifier.padding(start = 5.dp)
                )
                Card(modifier = Modifier.fillMaxHeight(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Blue
                    )) {
                    Column(modifier = Modifier.fillMaxHeight(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center) {
                        Text(
                            text = price.toString(),
                            fontSize = 25.sp,
                            color = Color.White,
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun LoadingScreen(){
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Image(painter = painterResource(id = R.drawable.loading_screen), contentDescription = "Loading",
            modifier = Modifier.size(175.dp))
    }
}
@Composable
fun ErrorScreen(staySavvyViewModel: StaySavvyViewModel){
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center){
        Image(painter = painterResource(id = R.drawable.erroe_screen), contentDescription = "Loading")
        Text(text = "Ooops! Internet unavilable .Please check your connection or retry after turning your wifi or mobile data on.",
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            textAlign = TextAlign.Center)
        Button(onClick = { staySavvyViewModel.getStaySavvyItem()}) {
            Text(text = "Retry")
        }
    }
}

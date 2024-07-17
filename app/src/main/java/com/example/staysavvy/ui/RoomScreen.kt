package com.example.staysavvy.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.staysavvy.R
import com.example.staysavvy.R.drawable.muscle
import com.example.staysavvy.data.HotelCategoryItem

@Composable
fun RoomScreen(staySavvyViewModel: StaySavvyViewModel,
               items: List<HotelCategoryItem>,
               navController: NavHostController){
    LazyColumn(
        modifier = Modifier.padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Card(modifier = Modifier
                .padding(top = 30.dp)
                .fillMaxSize()) {
                AsyncImage(model = R.drawable.bangalore, contentDescription ="HotelImage",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp))
            }
        }
        item {
            Text(text = "Hotel,Agra",
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1,
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0,84,105)
            )
        }
        item {
            Text(text = "About the hotel",
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(2,204,254))
            Text(text = "Welcome to Hotel Serenity, a luxurious retreat nestled in the heart of the city. Our hotel offers an unparalleled blend of elegance, comfort, and modern amenities, ensuring an unforgettable stay for both business and leisure travelers.",
                modifier = Modifier.fillMaxWidth(),
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
            )
        }
        item {
            Divider(thickness = 1.dp, modifier = androidx.compose.ui.Modifier.padding(
                vertical = 5.dp
            ), color = Color.LightGray)
        }
        item {
            Text(text = "Amenities available",
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(2,204,254))
            Row (modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceEvenly){
                Column(verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(painter = painterResource(id = R.drawable.muscle), contentDescription ="" ,
                        modifier = Modifier.size(50.dp))
                    androidx.compose.material3.Text(text = "Gym", fontSize = 16.sp)
                }
                Column(verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(painter = painterResource(id = R.drawable.parking), contentDescription ="" ,
                        modifier = Modifier.size(50.dp))
                    androidx.compose.material3.Text(text = "Free Parking", fontSize = 16.sp)
                }
                Column(verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(painter = painterResource(id = R.drawable.restaurant), contentDescription ="",
                        modifier = Modifier.size(50.dp))
                    androidx.compose.material3.Text(text = "Restaurants", fontSize = 16.sp)
                }
            }
        }
        item {
            Divider(thickness = 1.dp, modifier = androidx.compose.ui.Modifier.padding(
                vertical = 5.dp
            ), color = Color.LightGray)
        }
        item {
            Text(text = "Property Rules & Information ",
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(2,204,254))
            Text(text = "* Check-in 12:00 PM,Check-out 11:00 AM",
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
            )
            Text(text = "* Pets are not allowed.",
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
            )
            Text(text = "* Outside food is not allowed",
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
            )
            Text(text = "* Passport,Aadhar & Govt. ID are accepted as ID proofs.",
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
            )
        }
        item {
            Divider(thickness = 1.dp, modifier = androidx.compose.ui.Modifier.padding(
                vertical = 5.dp
            ), color = Color.LightGray)
        }
        item {
            Text(text = "Select Room(s)",
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(2,204,254))
            RoomSelection(RoomImage = R.drawable.delux_room, RoomName = "Deluxe Room Ac", RoomPrice ="Rs. 4,999" )
            RoomSelection(
                RoomImage = R.drawable.double_room,
                RoomName = "Double Room",
                RoomPrice = "Rs. 7,999"
            )
            RoomSelection(
                RoomImage = R.drawable.executive_room,
                RoomName = "Executives",
                RoomPrice = "Rs. 9,999"
            )
        }
    }
}

@Composable
fun InternetRoomScreen(
    staySavvyViewModel: StaySavvyViewModel, itemUiState:StaySavvyViewModel.ItemUiState,
    navController: NavHostController
){
    when(itemUiState){
        is StaySavvyViewModel.ItemUiState.Loading->{
            LoadingScreen()
        }
        is StaySavvyViewModel.ItemUiState.Success->{
            RoomScreen(
                staySavvyViewModel = staySavvyViewModel, items = itemUiState.items,navController=navController)
        }
        else->{
            ErrorScreen(staySavvyViewModel = staySavvyViewModel)
        }
    }
}

@Composable
fun RoomSelection(
    RoomImage:Int,
    RoomName:String,
    RoomPrice:String
){
    Column (modifier = Modifier.padding(5.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)){
        Card(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = RoomImage), contentDescription = "Room Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentScale = ContentScale.FillWidth
            )
        }
        Row (horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()){
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(text = RoomName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    )
                Text(text = RoomPrice,
                    fontSize = 16.sp)
            }
            Card(modifier = Modifier
                .clickable {

                }
                .fillMaxHeight()
                .padding(vertical = 3.dp, horizontal = 5.dp),
                border = BorderStroke(2.dp,Color(2,204,254)),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )

            ) {
                Column (modifier = Modifier
                    .fillMaxHeight()
                    .padding(vertical = 5.dp, horizontal = 15.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally){
                    Text(text = "Select",
                        fontSize = 20.sp,
                        color = Color(2,204,254))
                }
            }
        }
    }
}

package com.example.staysavvy.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.staysavvy.data.HotelCategoryItem
import com.example.staysavvy.data.InternetItemWithQuantity
import com.example.staysavvy.data.RoomCategory

@Composable
fun CheckoutScreen(staySavvyViewModel: StaySavvyViewModel){
    val cartItems by staySavvyViewModel.cartItems.collectAsState()
    val checkIn by staySavvyViewModel.checkIn.collectAsState()
    val checkOut by staySavvyViewModel.checkOut.collectAsState()
    val cartItemsWithQuantity =cartItems.groupBy { it }
        .map {
                (item,cartItems)->
            InternetItemWithQuantity(
            item,
            cartItems.size
        )
        }

    LazyColumn {
        item {
            Column {
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 5.dp)) {
                    androidx.compose.material.Text(
                        text = "Booking Details",
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 1,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0, 84, 105)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Column(
                            modifier = Modifier.fillMaxHeight(),
                            verticalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            Text(
                                text = "CHECK-IN",
                                fontSize = 16.sp
                            )
                            Text(
                                text = checkIn,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Card(colors = CardDefaults.cardColors(
                            containerColor = Color.Gray
                        ),
                            modifier = Modifier.fillMaxHeight()) {
                            Text(text = "@days ",
                                fontSize = 16.sp,
                                modifier = Modifier.padding(4.dp))
                        }
                        Column(
                            modifier = Modifier.fillMaxHeight(),
                            verticalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            Text(
                                text = "CHECK-OUT",
                                fontSize = 16.sp
                            )
                            Text(
                                text = checkOut,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
        items(cartItemsWithQuantity){
            BookingDetails(roomItem = it.item, staySavvyViewModel = staySavvyViewModel)
        }
    }
}

@Composable
fun BookingDetails(
    roomItem:RoomCategory,
    staySavvyViewModel: StaySavvyViewModel
){
    Column (modifier = Modifier.padding(5.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)){
        Card(
            modifier = Modifier
                .fillMaxSize()
        ) {
            AsyncImage(model = roomItem.RoomImg, contentDescription = "Room Image ",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentScale = ContentScale.FillWidth)
        }
        Row (horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()){
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                androidx.compose.material.Text(
                    text = stringResource(id = roomItem.RoomName),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
                androidx.compose.material.Text(
                    text = "Rs. ${roomItem.RoomPrice}",
                    fontSize = 16.sp
                )
            }
            Card(modifier = Modifier
                .clickable {
                    staySavvyViewModel.removeFromCart(oldItem = roomItem)
                    staySavvyViewModel.setLoading(true)
                }
                .fillMaxHeight()
                .padding(vertical = 3.dp, horizontal = 5.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.LightGray
                )

            ) {
                Column (modifier = Modifier
                    .fillMaxHeight()
                    .padding(vertical = 5.dp, horizontal = 15.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally){
                    androidx.compose.material.Text(
                        text = "Remove",
                        fontSize = 20.sp,
                        color = Color(2, 204, 254)
                    )
                }
            }
        }
    }
}

package com.example.staysavvy.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.sp
import com.example.staysavvy.data.HotelCategoryItem
import com.example.staysavvy.data.InternetItemWithQuantity
import com.example.staysavvy.data.RoomCategory

@Composable
fun CheckoutScreen(staySavvyViewModel: StaySavvyViewModel){
    val cartItems by staySavvyViewModel.cartItems.collectAsState()
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
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "",
                    fontSize = 24.sp,
                    )
            }
        }
        items(cartItemsWithQuantity){

        }
    }
}

@Composable
fun BookingDetails(
    roomItem:RoomCategory,
    staySavvyViewModel: StaySavvyViewModel
){

}

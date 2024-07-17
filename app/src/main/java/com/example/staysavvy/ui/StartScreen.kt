@file:Suppress("NAME_SHADOWING")

package com.example.staysavvy.ui

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.Image
import com.example.staysavvy.R
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.staysavvy.data.DataSource
import com.example.staysavvy.ui.StaySavvyScreen.*
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartScreen(staySavvyViewModel: StaySavvyViewModel,
                OnClicked:(String)->Unit,
                ){
    val place by staySavvyViewModel.place.collectAsState()
    val numberOfRooms by staySavvyViewModel.numberOfRoom.collectAsState()
    val checkIn by staySavvyViewModel.checkIn.collectAsState()
    val checkOut by staySavvyViewModel.checkOut.collectAsState()


    Column (modifier = Modifier
        .fillMaxSize()
        .padding(
            top = 50.dp,
            start = 20.dp, end = 20.dp
        ),
        ){
        Text(text = "Find Room",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            textAlign = TextAlign.Left,
            modifier = Modifier.padding(start = 10.dp)
            )
        OutlinedTextField(
            value =place,
            onValueChange ={
                staySavvyViewModel.setPlace(it)
            } ,
            leadingIcon = {
                Icon(imageVector = Icons.Default.LocationOn, contentDescription = "place")
            },
            label = {
                Text(text = "Where you want to go?")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = White,
                focusedLabelColor = Black,
                focusedLeadingIconColor = Black,
                unfocusedLeadingIconColor = Black,
                unfocusedLabelColor = Black,
                focusedBorderColor = Black,
                unfocusedBorderColor = Black
            ),
            textStyle = TextStyle(Black, fontWeight = FontWeight.Bold)

        )
        MyDatePicker("Checkin Date", checkIn, { staySavvyViewModel.setCheckIn(it) })
        MyDatePicker("Checkout Date", checkOut, { staySavvyViewModel.setCheckOut(it) })
        OutlinedTextField(
            value =numberOfRooms,
            onValueChange ={
                staySavvyViewModel.setNumberOfRooms(it)
            } ,
            leadingIcon = {
                Icon(imageVector = Icons.Default.Home, contentDescription = "Number of Rooms")
            },
            label = {
                Text(text = "Number of Rooms")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = White,
                focusedLabelColor = Black,
                focusedLeadingIconColor = Black,
                unfocusedLeadingIconColor = Black,
                unfocusedLabelColor = Black,
                focusedBorderColor = Black,
                unfocusedBorderColor = Black
            ),
            textStyle = TextStyle(color = Black, fontWeight = FontWeight.Bold)

        )
        Card(modifier = Modifier
            .clickable {
                OnClicked(StaySavvyScreen.Hotel.name)
            }
            .fillMaxWidth()
            .height(80.dp)
            .padding(10.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.Black
            )) {
            Column(modifier = Modifier
                .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Search",
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    color =  White,
                    )
            }
        }
        LazyRow {
            items(DataSource.loadCategories()){
                CityCard(cityName=it.cityName,cityPicture=it.cityPic)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDatePicker(
    label: String,
    check: String,
    dateValue: (String) -> Unit,
) {
    val mContext = LocalContext.current
    val mYear: Int
    val mMonth: Int
    val mDay: Int

    val mCalendar = Calendar.getInstance()

    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()

    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            dateValue("$mDayOfMonth/${mMonth + 1}/$mYear")
        }, mYear, mMonth, mDay
    )

    OutlinedTextField(
        value = check,
        onValueChange = { dateValue(it)
        },
        label = { Text(label) },
        enabled = false,
        modifier = Modifier
            .clickable { mDatePickerDialog.show() }
            .fillMaxWidth()
            .padding(10.dp),
        leadingIcon = {
            Icon(imageVector = Icons.Default.DateRange, contentDescription = "Date")
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = White,
            focusedLabelColor = Black,
            focusedLeadingIconColor = Black,
            unfocusedLeadingIconColor = Black,
            unfocusedLabelColor = Black,
            focusedBorderColor = Black,
            unfocusedBorderColor = Black,
            disabledBorderColor = Black,
            disabledLabelColor = Black,
            disabledLeadingIconColor = Black
        ),
        textStyle = TextStyle(color = Black, fontWeight = FontWeight.Bold)
    )
}

@Composable
fun CityCard(
     cityName:Int,
     cityPicture:Int
){
    Card (modifier = Modifier
        .size(225.dp)
        .padding(5.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Black
        )){
        Column(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = cityPicture,
                contentDescription = stringResource(id = cityName),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(175.dp),
                contentScale = ContentScale.Crop)
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = stringResource(id = cityName),
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
            }
        }
    }
}


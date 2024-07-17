package com.example.staysavvy.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.staysavvy.data.DataSource

@Composable
fun Where2GoScreen(){
    LazyColumn {
        items(DataSource.loadCategories()){
            SuggestionCard(cityPicture = it.cityPic, cityName = it.cityName)
        }
    }
}

@Composable
fun SuggestionCard(
    cityPicture:Int,
    cityName:Int
){
    Card(modifier = Modifier
        .fillMaxWidth()
        .height(270.dp)
        .padding(5.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Black
        )) {
        Column(modifier = Modifier.fillMaxSize()) {
            AsyncImage(model = cityPicture, contentDescription = stringResource(id = cityName),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),
                contentScale = ContentScale.Crop)
            Column (
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally)
            {
                Text(
                    text = stringResource(id = cityName),
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
            }
        }
    }
}
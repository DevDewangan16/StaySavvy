package com.example.staysavvy.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.staysavvy.R

@Composable
fun FaqScreen() {
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(10.dp)){
        item {
            Image(
            painter = painterResource(id = R.drawable.faq), contentDescription = "Faqs Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        ) }
        item {
            Text(
                text = "What happens if I enter the wrong OTP during account verification?",
                fontSize = 24.sp,
                color = Color(0, 84, 105),
                fontWeight = FontWeight.Bold
            )
        }
        item {
            Text(
                text = "If you enter the wrong OTP,you will be shown an error message and will have to re-enter the correct code.Please mention the correct OTP that you receive on your registered mobile number via SMS to proceed with the verification process successfully.",
                fontSize = 20.sp,
            )
        }
        item {
            Text(
                text = "Where can I contact if I face any issues while booking a hotel room using the app?",
                fontSize = 24.sp,
                color = Color(0, 84, 105),
                fontWeight = FontWeight.Bold
            )
        }
        item {
            Text(
                text = "If you face any issues during your booking process you can reach out to our customer support team by at or call us at 9999999999",
                fontSize = 20.sp,
            )
        }
        item {
            Text(
                text = "Is my personal information secure when using this app?",
                fontSize = 24.sp,
                color = Color(0, 84, 105),
                fontWeight = FontWeight.Bold
            )
        }
        item {
            Text(
                text = "Yes,we value the trust of our users and prioritise the security of their personal information .All your data ,including personal details and booking information is secure with us and protected using encryption protocols to ensure confidentiality",
                fontSize = 20.sp,
            )
        }
    }
}
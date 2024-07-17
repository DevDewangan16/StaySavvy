@file:Suppress("DEPRECATION")

package com.example.staysavvy.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.staysavvy.R
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit


@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(staySavvyViewModel: StaySavvyViewModel,
                 callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks){
    val name by staySavvyViewModel.name.collectAsState()
    val phoneNumber by staySavvyViewModel.phoneNumber.collectAsState()
    val email by staySavvyViewModel.email.collectAsState()
    val otp by staySavvyViewModel.otp.collectAsState()
    val verificationId by staySavvyViewModel.verificationId.collectAsState()
    val context = LocalContext.current
                OutlinedTextField(
                    value =name ,
                    onValueChange ={
                        staySavvyViewModel.setName(it)
                    } ,
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Person, contentDescription = "User ICon")
                    },
                    label = {
                        Text(text = "Full Name")
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
                    textStyle = TextStyle(color = Black, fontWeight = FontWeight.Bold),
                    singleLine = true
                )
                OutlinedTextField(
                    value =phoneNumber,
                    onValueChange ={
                        staySavvyViewModel.setPhoneNumber(it)
                    } ,
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Phone, contentDescription = "User ICon")
                    },
                    label = {
                        Text(text = "Mobile Number")
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
                    textStyle = TextStyle(color = Black, fontWeight = FontWeight.Bold),
                    singleLine = true
                )
                OutlinedTextField(
                    value =email,
                    onValueChange ={
                        staySavvyViewModel.setEmail(it)
                    } ,
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Email, contentDescription = "User ICon")
                    },
                    label = {
                        Text(text = "Email")
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
                    textStyle = TextStyle(color = Black, fontWeight = FontWeight.Bold),
                    singleLine = true
                )
                Card(modifier = Modifier
                    .clickable {
                        val options = PhoneAuthOptions
                            .newBuilder(auth)
                            .setPhoneNumber("+91${phoneNumber}") // Phone number to verify
                            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                            .setActivity(context as Activity) // Activity (for callback binding)
                            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
                            .build()
                        PhoneAuthProvider.verifyPhoneNumber(options)
                        staySavvyViewModel.setLoading(true)
                    }
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(10.dp),
                    colors =CardDefaults.cardColors(
                        containerColor = Black
                    )) {
                    Column(modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Send OTP",
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center,
                            color =  White,

                        )
                    }
                }
            }

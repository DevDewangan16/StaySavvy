package com.example.staysavvy.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.text.format.DateUtils
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.staysavvy.R
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

@SuppressLint("SuspiciousIndentation")
@Composable
fun OtpScreen(otp:String,staySavvyViewModel: StaySavvyViewModel,
              callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks){
    val context= LocalContext.current
    val verificationId by staySavvyViewModel.verificationId.collectAsState()
    val phoneNumber by staySavvyViewModel.phoneNumber.collectAsState()
    val ticks by staySavvyViewModel.ticks.collectAsState()
    Text(text = "Verify Mobile Number",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 1)
    Text(text = "OTP has been sent to you on your mobile number ,please enter it below",
                    fontSize = 15.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center)
    OtpTextBox(otp = otp, staySavvyViewModel = staySavvyViewModel)
    Card(modifier = Modifier.fillMaxWidth().clickable {
        if (otp.isEmpty()){
            Toast.makeText(context, "Please enter OTP", Toast.LENGTH_SHORT).show()
        }else{
            val credential = PhoneAuthProvider.getCredential(verificationId!!,otp)
            signInWithPhoneAuthCredential(
                credential=credential,
                context=context,
                staySavvyViewModel=staySavvyViewModel
            )
        }
    }
        .height(80.dp)
        .padding(10.dp),
        colors =CardDefaults.cardColors(
            containerColor = Color.Black)) {
        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text ="Verify OTP",
                color =  Color.White,
                fontWeight = FontWeight.Bold,
            )
        }
    }
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
        }
        .fillMaxWidth()
        .height(80.dp)
        .padding(10.dp),
        colors =CardDefaults.cardColors(
            containerColor = Color.Black
        )) {
        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = if (ticks == 0L) "Resend OTP" else "Resend OTP (${DateUtils.formatElapsedTime(ticks)})",
                color =  Color.White,
                fontWeight = if (ticks==0L) FontWeight.Bold else FontWeight.Normal,
            )
        }
    }
    Card(modifier = Modifier.fillMaxWidth().clickable {
        if (verificationId.isNotEmpty()){
            staySavvyViewModel.setVerificationId("")
            staySavvyViewModel.setOtp("")
        }
    }
        .height(80.dp)
        .padding(10.dp),
        colors =CardDefaults.cardColors(
            containerColor = Color.Black)) {
        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text ="Edit Phone Number",
                color =  Color.White,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}


@Composable
fun OtpTextBox(otp:String,staySavvyViewModel: StaySavvyViewModel) {
    BasicTextField(
        value =otp ,
        onValueChange = {
            staySavvyViewModel.setOtp(it)
        },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true){

        Row(horizontalArrangement = Arrangement.Center) {
            repeat(6){index->
                val number=when{
                    index >=otp.length->""
                    else->otp[index].toString()
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(4.dp)) {
                    Text(text = number,
                        fontSize = 32.sp)
                    Box (
                        modifier = Modifier
                            .width(40.dp)
                            .height(2.dp)
                            .background(Color.Gray)
                    ){

                    }
                }
            }
        }
    }
}


private fun signInWithPhoneAuthCredential(
    credential: PhoneAuthCredential,
    context: Context,
    staySavvyViewModel: StaySavvyViewModel) {
    auth.signInWithCredential(credential)
        .addOnCompleteListener(context as Activity) { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Toast.makeText(context, "Verification Successful", Toast.LENGTH_SHORT).show()
                val user = task.result?.user
                if (user!=null){
                    staySavvyViewModel.setUser(user)
                }
            } else {
                // Sign in failed, display a message and update the UI
                if (task.exception is FirebaseAuthInvalidCredentialsException) {
                    // The verification code entered was invalid
                    Toast.makeText(context, "The OTP entered is invalid .Please try again.", Toast.LENGTH_SHORT).show()
                }
                // Update UI
            }
        }
}

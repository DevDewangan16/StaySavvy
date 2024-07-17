package com.example.staysavvy.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
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
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

@Composable
fun SignUpUi(staySavvyViewModel: StaySavvyViewModel) {
    val name by staySavvyViewModel.name.collectAsState()
    val phoneNumber by staySavvyViewModel.phoneNumber.collectAsState()
    val email by staySavvyViewModel.email.collectAsState()
    val otp by staySavvyViewModel.otp.collectAsState()
    val verificationId by staySavvyViewModel.verificationId.collectAsState()
    val loading by staySavvyViewModel.loading.collectAsState()

    val context = LocalContext.current
    val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
        }

        override fun onVerificationFailed(e: FirebaseException) {
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken,
        ) {
            staySavvyViewModel.setVerificationId(verificationId)
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
            staySavvyViewModel.resetTimer()
            staySavvyViewModel.runTimer()
            staySavvyViewModel.setLoading(false)
        }
    }
    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(33,33,33))
                .padding(top = 50.dp),
        ) {
            Text(
                text = "Sign Up",
                fontSize = 24.sp,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp),
                color = Color.White
            )
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.stay_savvy_app_icon),
                            contentDescription = "",
                            modifier = Modifier.size(200.dp)
                        )
                    }
                    if (verificationId.isEmpty()) {
                        SignUpScreen(staySavvyViewModel = staySavvyViewModel, callbacks = callbacks)
                    } else {
                        OtpScreen(
                            otp = otp,
                            staySavvyViewModel = staySavvyViewModel,
                            callbacks = callbacks
                        )
                    }
                }
            }
        }
        if (loading) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color(255, 255, 255, 190)),

                ) {
                Box {
                    CircularProgressIndicator(
                        color =Color.Black
                    )
                }
                Text(text = "Loading")
            }
        }
    }
}


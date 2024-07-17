package com.example.staysavvy.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.staysavvy.R
import com.google.firebase.auth.FirebaseAuth

enum class StaySavvyScreen(val title :String){
    Start("Find Room"),
    Hotel("Select Hotel"),
    Room("Select Room"),
    Checkout("CheckOut"),
    Faqs("FAQs"),
    Suggestion("Where2Go")
}

var canNavigateBack=false
val auth=FirebaseAuth.getInstance()
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StaySavvyApp(staySavvyViewModel: StaySavvyViewModel= viewModel(),
                 navController: NavHostController= rememberNavController()) {
    val user by staySavvyViewModel.user.collectAsState()
    val isVisible by staySavvyViewModel.isVisible.collectAsState()

    val logoutClicked by staySavvyViewModel.logoutClicked.collectAsState()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen =StaySavvyScreen.valueOf(
        backStackEntry?.destination?.route?:StaySavvyScreen.Start.name
    )
    canNavigateBack=navController.previousBackStackEntry != null

    auth.currentUser?.let { staySavvyViewModel.setUser(it) }
   if (isVisible){
        SplashScreen()
    }
    else if (user==null){
        SignUpUi(staySavvyViewModel = staySavvyViewModel)
    }
    else{
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = currentScreen.title,
                                    fontSize = 26.sp,
                                    fontFamily = FontFamily.SansSerif,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.Black
                                )
                            }
                            Row(modifier = Modifier.clickable {
                                staySavvyViewModel.setLogoutStatus(true)
                            }) {

                                Icon(painter = painterResource(id = R.drawable.logout), contentDescription ="Logout",
                                    modifier = Modifier.size(24.dp))
                                Text(text = "Logout",
                                    fontSize = 18.sp,
                                    modifier = Modifier.padding(
                                        end = 14.dp,
                                        start = 4.dp
                                    ))
                            }
                        }
                    },
                    navigationIcon = {
                        if (canNavigateBack){
                            IconButton(onClick = {
                                navController.navigateUp()
                            }) {
                                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription ="Back Button" )
                            }
                        }
                    })
            },
            bottomBar ={
                BottomAppBar(navController=navController)
            }
        ) {
            NavHost(navController = navController, startDestination =StaySavvyScreen.Start.name, modifier = Modifier.padding(it) ) {
                composable(route = StaySavvyScreen.Start.name){
                    StartScreen(staySavvyViewModel = staySavvyViewModel, OnClicked = {navController.navigate(StaySavvyScreen.Hotel.name)})
                }
                composable(route = StaySavvyScreen.Hotel.name){
                    InternetHotelScreen(staySavvyViewModel = staySavvyViewModel, itemUiState = staySavvyViewModel.itemUiState,navController=navController)
                }
                composable(route =StaySavvyScreen.Room.name){
                    InternetRoomScreen(
                        staySavvyViewModel = staySavvyViewModel,
                        itemUiState = staySavvyViewModel.itemUiState,
                        navController = navController
                    )
                }
                composable(route = StaySavvyScreen.Faqs.name){
                    FaqScreen()
                }
                composable(route = StaySavvyScreen.Suggestion.name){
                    Where2GoScreen()
                }
            }
            if (logoutClicked){
                AlertCheck(onYesButtonPressed = {
                    auth.signOut()
                    staySavvyViewModel.clearData()
                },
                    onNoButtonPressed = {
                        staySavvyViewModel.setLogoutStatus(false)
                    }
                )
            }
        }
    }
}

@Composable
fun BottomAppBar(navController: NavHostController){
    Row (modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 70.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween){
        Column(verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.clickable {
                navController.navigate(StaySavvyScreen.Start.name)
            }) {
            androidx.compose.material3.Icon(imageVector = Icons.Outlined.Home, contentDescription = "Home")
            Text(text = "Home", fontSize = 10.sp)
        }
        Column(verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.clickable {
                navController.navigate(StaySavvyScreen.Suggestion.name)
            }) {
            androidx.compose.material3.Icon(imageVector = Icons.Outlined.Place, contentDescription = "Home")
            Text(text = "Where2Go", fontSize = 10.sp)
        }
        Column(verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.clickable {
                navController.navigate(StaySavvyScreen.Faqs.name)
            }) {
            androidx.compose.material3.Icon(imageVector = Icons.Outlined.Info, contentDescription = "Home")
            Text(text = "FAQs", fontSize = 10.sp)
        }
    }
}


@Composable
fun AlertCheck(
    onYesButtonPressed:()->Unit,
    onNoButtonPressed:()->Unit

){
    AlertDialog(
        title = {
            Text(text = "Logout?", fontWeight = FontWeight.Bold)
        },
        containerColor = Color.White,
        text = {
            Text(text = "Are you sure you want to Logout")
        },
        confirmButton = {
            TextButton(onClick = {
                onYesButtonPressed()
            }) {
                Text(text = "Yes")
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onNoButtonPressed()
            }) {
                Text(text = "No")
            }
        },
        onDismissRequest = {
            onNoButtonPressed()
        }
    )
}
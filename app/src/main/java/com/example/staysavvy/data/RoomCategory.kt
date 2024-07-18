package com.example.staysavvy.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class RoomCategory(
    @StringRes val RoomName:Int,
    @DrawableRes val RoomImg:Int,
    val RoomPrice:Int
)

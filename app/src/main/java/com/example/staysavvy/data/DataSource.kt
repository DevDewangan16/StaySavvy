package com.example.staysavvy.data

import com.example.staysavvy.R

object DataSource {
    fun loadCategories():List<CityCategories>{
        return listOf(
            CityCategories(R.string.new_delhi,R.drawable.new_delhi),
            CityCategories(R.string.mumbai,R.drawable.mumbai),
            CityCategories(R.string.bangalore,R.drawable.bangalore)
        )
    }
    fun loadRoomsCategory():List<RoomCategory>{
        return listOf(
            RoomCategory(R.string.Deluxe,R.drawable.delux_room,4999),
            RoomCategory(R.string.double_room,R.drawable.double_room,7999),
            RoomCategory(R.string.executives,R.drawable.executive_room,9999)
        )
    }
}
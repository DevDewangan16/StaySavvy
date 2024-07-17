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

}
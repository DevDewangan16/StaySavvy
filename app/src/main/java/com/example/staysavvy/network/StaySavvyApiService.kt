package com.example.staysavvy.network

import com.example.staysavvy.data.HotelCategoryItem
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private const val BASE_URl="https://hotel-kappa-one.vercel.app/"

private val retrofit=Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URl)
    .build()

interface StaySavvyApiService {
    @GET("hotels")
    suspend fun getItems():List<HotelCategoryItem>
}

object StaySavvyApi{
    val retrofitService:StaySavvyApiService by lazy{
        retrofit.create(
            StaySavvyApiService::class.java
        )
    }
}
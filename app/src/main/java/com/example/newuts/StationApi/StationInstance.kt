package com.example.newuts.StationApi

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object StationInstance {
    val api:StationService by lazy {

        Retrofit.Builder()
            .baseUrl("https://rstations.p.rapidapi.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }
}
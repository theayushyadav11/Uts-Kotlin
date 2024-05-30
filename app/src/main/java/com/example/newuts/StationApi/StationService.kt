package com.example.newuts.StationApi

import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface StationService {
    @Headers(
        "content-type:application/json",
        "X-RapidAPI-Key:8d7ad8a3c8msh760747a636b2ff1p11ae4ejsn4c4bed23d2da",
        "X-RapidAPI-Host:rstations.p.rapidapi.com")
    @POST("/")
    suspend fun getStations(@Body query:SearchQuery):ArrayList<ArrayList<String>>
}
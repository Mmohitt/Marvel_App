package com.example.marvel_app.Retrofit

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api_Marvel {
    @GET("/v1/public/characters")
    suspend fun getData(
        @Query("apikey")apikey:String,
        @Query("ts")ts:String,
        @Query("hash")hash:String,
        @Query("limit")limit:Int = 100
    ): Response<Characters>
}
package com.example.marvel_app.Util

import com.example.marvel_app.Retrofit.Character
import com.example.marvel_app.Retrofit.Characters
import retrofit2.Response
import java.math.BigInteger
import java.security.MessageDigest

class Constants {

    companion object{
        const val BASE_URL = "https://gateway.marvel.com"
        const val API_KEY = "7eedd968aede66fdf75d6a57087d8701"
        private const val PRIVATE_KEY = "42bafccd7f62152db955b89be25afe729543c9a1"

        val TIMESTAMP: String = System.currentTimeMillis().toString()
        private val input = "$TIMESTAMP$PRIVATE_KEY$API_KEY"
        private val md = MessageDigest.getInstance("MD5")

        val HASH = BigInteger(
            1, md.digest(input.toByteArray())
        ).toString(16).padStart(32, '0')

    }
}
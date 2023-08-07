package com.example.marvel_app.Retrofit

import androidx.room.Entity
import androidx.room.PrimaryKey

data class Characters(
    val attributionHTML: String,
    val attributionText: String,
    val code: Int,
    val copyright: String,
    val `data`: Data,
    val etag: String,
    val status: String
)

@Entity
data class Character(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String?,
    val thumbnail: String,
    val visitCount: Int = 0
)
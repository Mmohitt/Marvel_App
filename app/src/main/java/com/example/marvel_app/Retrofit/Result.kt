package com.example.marvel_app.Retrofit

data class Result(
    val id: Int,
    val name: String,
    val description: String,
    val images: List<Image>,
    val modified: String,
    val thumbnail: Thumbnail
)
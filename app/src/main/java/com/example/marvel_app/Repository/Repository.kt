package com.example.marvel_app.Repository

import com.example.marvel_app.Database.Database
import com.example.marvel_app.Retrofit.Api_Marvel
import com.example.marvel_app.Retrofit.Character
import com.example.marvel_app.Util.Constants.Companion.API_KEY
import com.example.marvel_app.Util.Constants.Companion.HASH
import com.example.marvel_app.Util.Constants.Companion.TIMESTAMP

class Repository(private val apiMarvel: Api_Marvel, private val database: Database) {


    suspend fun getMoviesCharacterFromServer() =

        apiMarvel.getData(API_KEY, TIMESTAMP, HASH)

    suspend fun getMoviesCharactersFromDB(): List<Character>? {
        return database.dao().getAllCharacters()
    }

    suspend fun updateCount(count: Int, characterId: Int): Int =
        database.dao().updateCount(count, characterId)
}
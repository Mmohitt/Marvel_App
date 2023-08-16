package com.example.marvel_app.Repository

import com.example.marvel_app.Database.Database
import com.example.marvel_app.Retrofit.Api_Marvel
import com.example.marvel_app.Retrofit.Character
import com.example.marvel_app.Util.Constants.Companion.API_KEY
import com.example.marvel_app.Util.Constants.Companion.HASH
import com.example.marvel_app.Util.Constants.Companion.TIMESTAMP
import com.example.marvel_app.Util.Constants.Companion.getRequiredData

class Repository(private val apiMarvel: Api_Marvel, private val database: Database) {


    suspend fun getCharacters(): List<Character>? {
        var myResult: MutableList<Character> = mutableListOf()
        // Check if data is available in the database
        val cachedCharacters = database.dao().getAllCharacters()
        if (cachedCharacters.isEmpty()) {
            // If no data in the database, make the API call
            val result = apiMarvel.getData(API_KEY, TIMESTAMP , HASH)
            getRequiredData(result.body()).let {
                myResult.addAll(it)
            }
            database.dao().insertAllCharacters(myResult)
            return myResult
        }
        return cachedCharacters
    }

     suspend fun updateCount(count:Int,characterId:Int):Int =
        database.dao().updateCount(count,characterId)

    suspend fun getCount(characterid:Int) =
        database.dao().getCount(characterid)
}
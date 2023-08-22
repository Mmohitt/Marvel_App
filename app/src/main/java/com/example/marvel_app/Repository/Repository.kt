package com.example.marvel_app.Repository

import com.example.marvel_app.Database.Database
import com.example.marvel_app.Retrofit.Api_Marvel
import com.example.marvel_app.Retrofit.Character
import com.example.marvel_app.Retrofit.Characters
import com.example.marvel_app.Util.Constants.Companion.API_KEY
import com.example.marvel_app.Util.Constants.Companion.HASH
import com.example.marvel_app.Util.Constants.Companion.TIMESTAMP

class Repository(private val apiMarvel: Api_Marvel, private val database: Database) {


    suspend fun getMoviesCharacterFromServer(): List<Character>? {
        var myResult: MutableList<Character> = mutableListOf()

        val result = apiMarvel.getData(API_KEY, TIMESTAMP, HASH)
        getRequiredData(result.body()).let {
            myResult.addAll(it)
        }
        database.dao().insertAllCharacters(myResult)
        return myResult
    }

    suspend fun getMoviesCharactersFromDB(): List<Character>? {
        return database.dao().getAllCharacters()
    }

    suspend fun updateCount(count: Int, characterId: Int): Int =
        database.dao().updateCount(count, characterId)

    private fun getRequiredData(characters: Characters?): List<Character> {
        return mutableListOf<Character>().apply {
            characters?.data?.results?.forEach {
                add(
                    Character(
                        id = it.id,
                        name = it.name,
                        description = it.description.ifEmpty {
                            "Description not provied by author..."
                        },
                        thumbnail = convertHttpToHttps(
                            it.thumbnail.path ?: ""
                        ) + "/" + "landscape_large" + "." + it.thumbnail.extension
                    )
                )

            }
        }
    }

    private fun convertHttpToHttps(url: String): String {
        return if (url.startsWith("http://")) {
            "https://" + url.substring(7)
        } else {
            url
        }
    }
}
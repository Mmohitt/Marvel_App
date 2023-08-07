package com.example.marvel_app.Database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.marvel_app.Retrofit.Character

@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(character: Character)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCharacters(character: List<Character>)

    @Query("SELECT * from Character")
    suspend fun getAllCharacters(): List<Character>

    @Query("UPDATE CHARACTER set visitCount = :count WHERE id=:characterId")
    suspend fun updateCount(count: Int, characterId: Int): Int

    @Query("SELECT visitCount FROM Character where id=:characterId")
    suspend fun getCount(characterId: Int): Int
}
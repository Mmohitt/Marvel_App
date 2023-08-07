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
    suspend fun getAllCharacters():List<Character>

}
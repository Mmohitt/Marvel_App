package com.example.marvel_app.Database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.marvel_app.Retrofit.Character

@androidx.room.Database(entities = [Character::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun dao(): Dao

    companion object {
        private var database: Database? = null
        fun getDatabase(context: Context): Database {
            if (database == null) {
                synchronized(Database::class.java) {
                        database = Room.databaseBuilder(
                            context.applicationContext,
                            Database::class.java,
                            "marvel_database" // Name of the database file
                        )
                            .build()
                }
            }
            return database!!
        }
    }
}
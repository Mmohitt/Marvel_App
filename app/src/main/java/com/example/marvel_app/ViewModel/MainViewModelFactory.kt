package com.example.marvel_app.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.marvel_app.Database.Database
import com.example.marvel_app.Repository.Repository

class MainViewModelFactory(private val repository: Repository,private val database: Database) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            // Return an instance of your ViewModel with the required constructor parameter(s)
            return MainViewModel(repository,database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

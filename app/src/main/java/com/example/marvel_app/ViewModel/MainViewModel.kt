package com.example.marvel_app.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvel_app.Repository.Repository
import com.example.marvel_app.Retrofit.Character
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {

    val characters: MutableLiveData<List<Character>> = MutableLiveData()

    fun getCharacters() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCharacters()?.let {
                characters.postValue(it)
            }
        }
    }

    fun updateCount(character: Character) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateCount(character.visitCount + 1, character.id)
        }
    }
}

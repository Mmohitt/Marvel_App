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

    val error: MutableLiveData<Unit> = MutableLiveData()

    fun loadCharacter() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getMoviesCharactersFromDB()?.let { charactersList ->
                if (charactersList.isNotEmpty()) {
                    characters.postValue(charactersList)
                } else {
                    repository.getMoviesCharacterFromServer()?.let { charactersList ->
                        characters.postValue(charactersList)
                    }
                }
            }
        }
    }

//    private fun isInternetAvailable(): Boolean {
//
//    }

    fun updateCount(character: Character) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateCount(character.visitCount + 1, character.id)
        }
    }
}

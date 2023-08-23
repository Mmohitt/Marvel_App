package com.example.marvel_app.ViewModel

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvel_app.Repository.Repository
import com.example.marvel_app.Retrofit.Character
import com.example.marvel_app.Retrofit.Characters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: Repository,
    private val database: com.example.marvel_app.Database.Database
) : ViewModel() {

    val characters: MutableLiveData<List<Character>> = MutableLiveData()
    val errorForInternet: MutableLiveData<Unit> = MutableLiveData()
    val errorForResponse: MutableLiveData<Unit> = MutableLiveData()

    fun loadCharacter(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getMoviesCharactersFromDB()?.let { characterList ->
                if (characterList.isNotEmpty()) {
                    characters.postValue(characterList)
                    Log.d("ViewModelAA","From DB")
                } else {
                    if (isInternetAvailable(context)) {
                        try{
                            val response = repository.getMoviesCharacterFromServer()
                            if(response.isSuccessful){
                                val myResult: MutableList<Character> = mutableListOf()
                                getRequiredData(response.body()).let {
                                myResult.addAll(it)
                              }
                                database.dao().insertAllCharacters(myResult)
                                characters.postValue(myResult)
                            }
                            else{
                                errorForResponse.postValue(Unit)
                            }
                        }
                        catch (e: Exception){

                        }

                    } else {
                        errorForInternet.postValue(Unit)
                    }
                }
            }
        }
    }

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

    private fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        return capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }

    fun updateCount(character: Character) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateCount(character.visitCount + 1, character.id)
        }
    }
}

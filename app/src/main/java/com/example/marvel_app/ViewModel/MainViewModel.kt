package com.example.marvel_app.ViewModel

import InternetConnectivityChecker
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.NetworkCapabilities.TRANSPORT_ETHERNET
import android.net.NetworkCapabilities.TRANSPORT_WIFI
import android.os.Build
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvel_app.MainActivity
import com.example.marvel_app.Repository.Repository
import com.example.marvel_app.Retrofit.Character
import com.example.marvel_app.Util.MyApp
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {

    val characters: MutableLiveData<List<Character>> = MutableLiveData()


    val error: MutableLiveData<Unit> = MutableLiveData()

    fun loadCharacter(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getMoviesCharactersFromDB()?.let { charactersList ->
                if (charactersList.isNotEmpty()) {
                    characters.postValue(charactersList)
                } else {
                    if(isInternetAvailable(context)){
                        repository.getMoviesCharacterFromServer()?.let { charactersList ->
                            characters.postValue(charactersList)
                        }
                    }
                    else{
                        error.postValue(Unit)
                    }
                }
            }
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

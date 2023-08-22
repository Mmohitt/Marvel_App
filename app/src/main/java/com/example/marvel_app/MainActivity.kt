package com.example.marvel_app

import com.example.marvel_app.ViewModel.MainViewModelFactory
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.marvel_app.Adapter.CharacterAdapter
import com.example.marvel_app.Database.Database
import com.example.marvel_app.Repository.Repository
import com.example.marvel_app.Retrofit.Api_Marvel
import com.example.marvel_app.Retrofit.Character
import com.example.marvel_app.Retrofit.RetrofitHelper
import com.example.marvel_app.ViewModel.MainViewModel
import com.example.marvel_app.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val apiMarvel by lazy {
            RetrofitHelper.getRetrofitInstance().create(Api_Marvel::class.java)
        }

        val database by lazy {
            Database.getDatabase(applicationContext)
        }

        val repository by lazy {
            Repository(apiMarvel, database)
        }
        mainViewModel =
            ViewModelProvider(this, MainViewModelFactory(repository)).get(MainViewModel::class.java)

        mainViewModel.loadCharacter(applicationContext)

        mainViewModel.characters.observe(this) { charactersList ->
            loadData(charactersList)
        }

        mainViewModel.error.observe(this){
            Toast.makeText(applicationContext, "No internet connection", Toast.LENGTH_SHORT).show()
        }

    }

    private fun loadData(list: List<Character>) {
        binding.CharactersList.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = CharacterAdapter(list) { character ->
                updateVisitCount(character)
                navigateToCharacterDetailActivity(character)
            }
        }
    }

    private fun updateVisitCount(character: Character) {
        mainViewModel.updateCount(character)
    }

    private fun navigateToCharacterDetailActivity(character: Character) {
        val intent = Intent(this, CharacterDetailActivity::class.java)
        intent.putExtra("name", character.name)
        intent.putExtra("description", character.description)
        intent.putExtra("image", character.thumbnail)
        startActivity(intent)
    }


}

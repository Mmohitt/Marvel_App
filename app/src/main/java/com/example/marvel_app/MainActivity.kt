package com.example.marvel_app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.marvel_app.Database.Database
import com.example.marvel_app.Repository.Repository
import com.example.marvel_app.Retrofit.Api_Marvel
import com.example.marvel_app.Retrofit.Character
import com.example.marvel_app.Retrofit.RetrofitHelper
import com.example.marvel_app.ViewModel.MainViewModel
import com.example.marvel_app.ViewModel.MainViewModelFactory
import com.example.marvel_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val apiMarvel = RetrofitHelper.getRetrofitInstance().create(Api_Marvel::class.java)
        val database = Database.getDatabase(applicationContext)
        val repository = Repository(apiMarvel, database)
        mainViewModel =
            ViewModelProvider(this, MainViewModelFactory(repository)).get(MainViewModel::class.java)

        mainViewModel.characters.observe(this, Observer {
            loadData(it)
        })
        mainViewModel.getCharacters()
    }

    private fun loadData(list: List<Character>) {
        binding.CharactersList.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = CharacterAdapter(list) { character ->
                // Handle item click here and navigate to CharacterDetailActivity
                navigateToCharacterDetailActivity(character)
            }
        }
    }

    private fun navigateToCharacterDetailActivity(character: Character) {
        val intent = Intent(this, CharacterDetailActivity::class.java)
        intent.putExtra("name", character.name)
        intent.putExtra("description", character.description)
        intent.putExtra("image", character.thumbnail)
        startActivity(intent)
    }


}

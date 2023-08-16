package com.example.marvel_app

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.marvel_app.databinding.FragmentCharacterDetailsBinding

class CharacterDetailActivity : AppCompatActivity() {
    var characterName: String = ""
    var characterDescription: String = ""
    var imageUrl: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : FragmentCharacterDetailsBinding = DataBindingUtil.setContentView(this, R.layout.fragment_character_details)

        characterName = intent.getStringExtra("name") ?: ""
        characterDescription = intent.getStringExtra("description") ?: ""
        imageUrl = intent.getStringExtra("image") ?: ""

        binding.textView2.text = characterName
        binding.description.text = characterDescription
        Glide.with(this)
            .load(imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .into(binding.image)
    }
}
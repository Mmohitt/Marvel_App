package com.example.marvel_app

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CharacterAdapter(private val list: List<String>):RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>(){
    inner class CharacterViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int =
      list.size


    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}
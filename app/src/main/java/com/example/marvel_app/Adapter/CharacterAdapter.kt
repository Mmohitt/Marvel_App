package com.example.marvel_app.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.marvel_app.R
import com.example.marvel_app.Retrofit.Character
import com.squareup.picasso.Picasso

class CharacterAdapter(private val list: List<Character>, private val onItemClick: (Character) -> Unit) :
    RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {
    inner class CharacterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.characterImage)
        val text: TextView = itemView.findViewById(R.id.textView)

        init {
            itemView.setOnClickListener {
                val character = list[adapterPosition]
                onItemClick(character)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_characters, parent, false
        )
        return CharacterViewHolder(view)
    }

    override fun getItemCount(): Int =
        list.size

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.text.text = list[position].name
        Picasso.get()
            .load(list[position].thumbnail)
            .into(holder.image)

    }
}
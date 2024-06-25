package com.guna.rickymortyapp.Presentation

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.guna.rickymortyapp.CharacterDetailActivity
import com.guna.rickymortyapp.Data.CharacterData
import com.guna.rickymortyapp.R

class RecyclerAdapter(private val contexts: Context,data: List<CharacterData>) : RecyclerView.Adapter<RecyclerAdapter.CharacterDataViewHolder>() {
    var characterData: List<CharacterData> = data

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterDataViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.character_view, parent, false)
        return CharacterDataViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterDataViewHolder, position: Int) {
        val characterData = characterData[position]
        holder.bind(characterData)
    }

    override fun getItemCount(): Int {
        return characterData.size
    }

    inner class CharacterDataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.findViewById(R.id.name)
        private val status: TextView = itemView.findViewById(R.id.status)
        private val imageview: ImageView = itemView.findViewById(R.id.imageview)

        fun bind(characterData : CharacterData) {
            name.text = characterData.name
            status.text = characterData.status


            Glide.with(itemView.context)
                .load(characterData.image)
                .into(imageview)

            itemView.setOnClickListener{
                val status = characterData.status
                val gender = characterData.gender
                val type = characterData.type
                val species = characterData.species
                val imageview = characterData.image
                val location = characterData.location

                val intent = Intent(contexts, CharacterDetailActivity::class.java)
                intent.putExtra("status", status)
                intent.putExtra("gender", gender)
                intent.putExtra("type", type)
                intent.putExtra("species", species)
                intent.putExtra("imageview", imageview)
                intent.putExtra("location", location)
                contexts.startActivity(intent)

            }
        }


    }


}

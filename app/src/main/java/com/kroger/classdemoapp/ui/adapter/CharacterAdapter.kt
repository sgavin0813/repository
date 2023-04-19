package com.kroger.classdemoapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kroger.classdemoapp.R
import com.kroger.classdemoapp.databinding.CharacterCardViewBinding
import com.kroger.classdemoapp.model.Article

class CharacterAdapter(
    private val onItemClick: (character: Article, adapterPosition: Int) -> Unit,
) : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    init {
        setHasStableIds(true)
    }

    private val movieItems = mutableListOf<Article>()

    @SuppressLint("NotifyDataSetChanged")
    fun refreshData(characters: List<Article>) {
        movieItems.clear()
        movieItems.addAll(characters)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CharacterCardViewBinding.inflate(layoutInflater, parent, false)
        return CharacterViewHolder(binding) { position ->
            onItemClick(movieItems[position], position)
        }
    }

    override fun getItemCount() = movieItems.size

    override fun getItemId(position: Int) = position.toLong()

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = movieItems[position]
        holder.bind(character)
    }

    inner class CharacterViewHolder(
        private val binding: CharacterCardViewBinding,
        private val onItemClick: (adapterPosition: Int) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                onItemClick(adapterPosition)
            }
        }

        fun bind(character: Article) {
            //this is where we make changes to bind data
            Glide
                .with(binding.root)
                .load(character.urlToImage)
                .error(R.drawable.baseline_10k_24)
                .into(binding.characterImage)

            binding.title.text = character.title
            binding.characterGender.text = character.author
        }
    }



}

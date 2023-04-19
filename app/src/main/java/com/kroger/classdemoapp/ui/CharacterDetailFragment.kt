package com.kroger.classdemoapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.kroger.classdemoapp.R
import com.kroger.classdemoapp.databinding.FragmentCharacterDetailBinding
import com.kroger.classdemoapp.viewmodel.CharacterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterDetailFragment : Fragment() {

    private var _binding: FragmentCharacterDetailBinding? = null
    private val binding get() = _binding!!

    private val characterViewModel: CharacterViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentCharacterDetailBinding.inflate(inflater, container, false)

        // Retrieve the character's title from the arguments bundle
        val characterTitle = arguments?.getString(ARG_CHARACTER_TITLE)

        if (characterTitle != null) {
            val character = characterViewModel.fetchByTitle(characterTitle)

            // Populate the UI with the character data
            Glide.with(requireContext()).load(character?.urlToImage).into(binding.characterImage)
            binding.title.text = getString(R.string.name, character?.title ?:"N/A" )
            binding.details.text = character?.description ?: "No Data"
        }

        return binding.root
    }



    companion object {
        private const val ARG_CHARACTER_TITLE = "character_title"

        fun newInstance(characterTitle: String): CharacterDetailFragment {
            val args = Bundle().apply {
                putString(ARG_CHARACTER_TITLE, characterTitle)
            }
            return CharacterDetailFragment().apply {
                arguments = args
            }
        }
    }
}

package com.kroger.classdemoapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.kroger.classdemoapp.R
import com.kroger.classdemoapp.databinding.FragmentCharacterListBinding
import com.kroger.classdemoapp.ui.adapter.CharacterAdapter
import com.kroger.classdemoapp.viewmodel.CharacterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharacterListFragment : Fragment() {

    private var _binding: FragmentCharacterListBinding? = null
    private val binding get() = _binding!!

    private val characterViewModel: CharacterViewModel by activityViewModels()
    private val characterAdapter = CharacterAdapter { character, _ ->
        requireActivity().supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(
                R.id.fragment_container_view,
                CharacterDetailFragment.newInstance(character.title),
            )
            addToBackStack(null)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCharacterListBinding.inflate(inflater, container, false)
        setupObservers()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.characterRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = characterAdapter
        }
        characterViewModel.fillData()
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            characterViewModel.characters.collect { event ->
                when (event) {
                    CharacterViewModel.MovieItemEvent.Failure -> {
                        binding.progressBar.isVisible = false
                        binding.characterRecyclerView.isVisible = false
                        binding.errorMessage.isVisible = true
                    }
                    CharacterViewModel.MovieItemEvent.Loading -> {
                        binding.progressBar.isVisible = true
                        binding.characterRecyclerView.isVisible = false
                        binding.errorMessage.isVisible = false
                    }
                    is CharacterViewModel.MovieItemEvent.Success -> {
                        characterAdapter.refreshData(event.article)
                        binding.progressBar.isVisible = false
                        binding.errorMessage.isVisible = false
                        binding.characterRecyclerView.isVisible = true
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

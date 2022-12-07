package com.zii.featurefavorite.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.zii.core.main.domain.model.MovieModel
import com.zii.core.main.vo.Resource
import com.zii.featurefavorite.databinding.FragmentListFavoriteBinding
import com.zii.featurefavorite.di.DaggerFavoriteComponent
import com.zii.featurefavorite.viewmodel.FavoriteViewModel
import com.zii.featurefavorite.viewmodel.FavoriteViewModelFactory
import com.zii.movieapp.R
import com.zii.movieapp.module.FavoriteModuleDependencies
import com.zii.movieapp.presentation.adapter.ListMovieAdapter
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class ListFavoriteFragment : Fragment() {
    private var _binding: FragmentListFavoriteBinding? = null
    private val binding get() = _binding

    @Inject
    lateinit var factory : FavoriteViewModelFactory

    private val viewModel : FavoriteViewModel by viewModels { factory }
    private val movieAdapter : ListMovieAdapter by lazy {
        ListMovieAdapter(
            onItemClicked = { navigateToDetail(it) }
        ) }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerFavoriteComponent.builder()
            .context(context)
            .appDependencies(EntryPointAccessors.fromApplication(
                requireContext(),
                FavoriteModuleDependencies::class.java
            ))
            .build()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListFavoriteBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()
        initObserver()
    }

    private fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            viewModel.getFavorite().collect {
                when (it) {
                    is Resource.Success -> {
                        if (it.data?.isNotEmpty() == true) {
                            movieAdapter.submitList(it.data?.toMutableList())
                            setupRecycler()
                        }
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun setupRecycler() {
        binding?.apply {
            rvMovies.adapter = movieAdapter
            rvMovies.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun navigateToDetail(data: MovieModel) {
        val toDetail = ListFavoriteFragmentDirections.actionFavoriteFragmentToDetailMovieFragment(data)
        findNavController().navigate(toDetail)
    }

    private fun initToolbar() {
        binding?.apply {
            tvTitle.text = getString(R.string.favorite)
            icBack.setOnClickListener { findNavController().popBackStack() }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
package com.zii.movieapp.presentation.search.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.zii.core.main.domain.model.MovieModel
import com.zii.core.main.vo.Resource
import com.zii.movieapp.databinding.FragmentSearchBinding
import com.zii.movieapp.presentation.adapter.ListMovieAdapter
import com.zii.movieapp.presentation.search.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.jetbrains.anko.sdk27.coroutines.onEditorAction

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding

    private val viewModel: SearchViewModel by viewModels()
    private val movieAdapter: ListMovieAdapter by lazy { ListMovieAdapter(
        onItemClicked = { navigateToDetail(it) }
    ) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
        initObserver()
    }

    private fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            viewModel.searchMovies.collect {
                when (it) {
                    is Resource.Success -> {
                        movieAdapter.submitList(it.data?.toMutableList())
                        setupRecycler()
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
        val toDetail = SearchFragmentDirections.actionSearchFragmentToDetailMovieFragment(data)
        findNavController().navigate(toDetail)
    }

    private fun initListener() {
        binding?.apply {
            btnBack.setOnClickListener { findNavController().popBackStack() }
            iconCancelSearch.setOnClickListener { inputSearch.text?.clear() }
            inputSearch.onEditorAction { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    inputSearch.clearFocus()
                }
            }
            inputSearch.doAfterTextChanged {
                iconCancelSearch.isVisible = it.toString().isNotEmpty()
            }
            inputSearch.addTextChangedListener {
                it?.let { query ->
                    if (query.trim().isNotEmpty()) viewModel.getSearchMovies(query.toString())
                }
            }
        }
    }
}
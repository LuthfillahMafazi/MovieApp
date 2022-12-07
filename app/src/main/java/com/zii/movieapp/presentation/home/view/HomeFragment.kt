package com.zii.movieapp.presentation.home.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.zii.core.main.domain.model.MovieModel
import com.zii.core.main.enums.MovieType
import com.zii.core.main.vo.Resource
import com.zii.movieapp.R
import com.zii.movieapp.databinding.FragmentHomeBinding
import com.zii.movieapp.databinding.ItemGridMoviesBinding
import com.zii.movieapp.presentation.adapter.BannerAdapter
import com.zii.movieapp.presentation.home.adapter.MoviesAdapter
import com.zii.movieapp.presentation.home.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding

    private val homeViewModel: HomeViewModel by viewModels()
    private val bannerAdapter by lazy { BannerAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()
        initObserver()
    }

    private fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            homeViewModel.upcomingMovies.collect {
                when (it) {
                    is Resource.Loading -> {
                        binding?.banner?.shimmer?.root?.visibility = View.VISIBLE
                    }
                    is Resource.Error -> {
                        binding?.banner?.shimmer?.root?.visibility = View.GONE
                    }
                    is Resource.Success -> {
                        binding?.banner?.shimmer?.root?.visibility = View.GONE
                        setupBannerAdapter(it.data)
                    }
                    else -> Unit
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            homeViewModel.nowPlayingMovies.collect {
                when (it) {
                    is Resource.Loading -> {
                        binding?.layoutPopularMovies?.shimmer?.root?.isVisible
                    }
                    is Resource.Error -> {
                        binding?.layoutPopularMovies?.shimmer?.root?.isGone
                    }
                    is Resource.Success -> {
                        binding?.layoutPopularMovies?.shimmer?.root?.visibility = View.GONE
                        setupRecyclerView(binding?.layoutPopularMovies, MovieType.NOW_PLAYING, it.data)
                    }
                    else -> Unit
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            homeViewModel.topRatedMovies.collect {
                when (it) {
                    is Resource.Loading -> {
                        binding?.layoutTopRatedMovies?.shimmer?.root?.isVisible
                    }
                    is Resource.Error -> {
                        binding?.layoutTopRatedMovies?.shimmer?.root?.isGone
                    }
                    is Resource.Success -> {
                        binding?.layoutTopRatedMovies?.shimmer?.root?.visibility = View.GONE
                        setupRecyclerView(binding?.layoutTopRatedMovies, MovieType.TOP_RATED, it.data)
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun setupBannerAdapter(data: List<MovieModel>?) {
        binding?.apply {
            banner.carouselBanner.apply {
                adapter = bannerAdapter
                start(5, TimeUnit.SECONDS)

            bannerAdapter.setData(data?.take(5))
            }
        }
    }

    private fun setupRecyclerView(view: ItemGridMoviesBinding?, movieType: MovieType, data : List<MovieModel>?) {
        val movieAdapter = MoviesAdapter()
        movieAdapter.submitList(data?.toMutableList())
        view?.apply {
            viewGroup.visibility = View.VISIBLE
            rvMovies.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            rvMovies.adapter = movieAdapter
            tvTypeMovies.text = movieType.movie

            movieAdapter.callbackClicked {
                val toDetail = HomeFragmentDirections.actionHomeFragmentToDetailMovieFragment(it)
                findNavController().navigate(toDetail)
            }
        }
    }

    private fun initToolbar() {
        binding?.toolbar?.apply {
            tvTitle.text = getString(R.string.cover)
            icBack.isGone
            icSearchToolbar.visibility = View.VISIBLE
            icFavoriteToolbar.visibility = View.VISIBLE

            icSearchToolbar.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
            }

            icFavoriteToolbar.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_favoriteFragment)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
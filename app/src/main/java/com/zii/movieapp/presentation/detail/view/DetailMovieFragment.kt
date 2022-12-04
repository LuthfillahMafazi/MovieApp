package com.zii.movieapp.presentation.detail.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.zii.core.main.vo.Resource
import com.zii.movieapp.R
import com.zii.movieapp.databinding.FragmentDetailMovieBinding
import com.zii.movieapp.presentation.detail.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs

@AndroidEntryPoint
class DetailMovieFragment : Fragment() {

    private var _binding: FragmentDetailMovieBinding? = null
    private val binding get() = _binding

    private val viewModel: DetailViewModel by viewModels()
    private val args by navArgs<DetailMovieFragmentArgs>()

    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailMovieBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        initListener()
        if (!isFavorite) initObserverLocalMovie()
    }

    private fun initObserverLocalMovie() {
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            args.movie?.id?.let { movieId ->
                viewModel.getLocaleMovie(movieId).collect {
                    when (it) {
                        is Resource.Success -> {
                            it.data?.let { isFavorite = true } ?: run { isFavorite = false }
                            checkFavorite()
                        }
                        else -> {}
                    }
                }
            }
        }
    }

    private fun initListener() {
        binding?.apply {
            toolbar.icBack.setOnClickListener { findNavController().popBackStack() }
            appBarLayout.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
                val scrollPosition = abs(verticalOffset) - appBarLayout.totalScrollRange
                toolbar.tvTitle.isVisible = abs(scrollPosition) <= 100
            }
            btnFavorite.setOnClickListener {
                args.movie?.let { movie ->
                    viewModel.updateFavoriteMovie(isFavorite, movie)
                    isFavorite = !isFavorite
                    checkFavorite()
                    if (isFavorite) Toast.makeText(requireContext(), "Success to add Favorite", Toast.LENGTH_SHORT).show()
                    else Toast.makeText(requireContext(), "Remove from favorite", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun checkFavorite() {
        if (isFavorite) {
            binding?.btnFavorite?.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_favorite
                )
            )
        } else {
            binding?.btnFavorite?.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_unfavorite
                )
            )
        }
    }

    private fun setupView() {
        binding?.apply {
            Glide.with(requireContext()).load(args.movie?.posterUri).into(header.imgMovie)
            header.tvTitle.text = args.movie?.title
            val dateOfYear = args.movie?.releaseDate?.take(4)
            val genre = args.movie?.genre?.map { it?.name }?.joinToString { ", " }.toString()
            val sub = "$dateOfYear - $genre - ${args.movie?.voteAverage}"
            header.tvDesc.text = sub
            toolbar.tvTitle.text = args.movie?.title
            textViewContent.text = args.movie?.overview
        }
    }
}
package com.zii.movieapp.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zii.core.main.domain.model.MovieModel
import com.zii.movieapp.databinding.ItemGridHorizontalMovieBinding

class MoviesAdapter() : ListAdapter<MovieModel, MoviesAdapter.ViewHolder>(diffCallback) {
    private var onItemClicked: ((MovieModel?) -> Unit)? = null

    fun callbackClicked(callback: ((MovieModel?) -> Unit)) {
        onItemClicked = callback
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view =
            ItemGridHorizontalMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun submitList(list: MutableList<MovieModel>?) {
        super.submitList(list?.map { it.copy() })
    }

    inner class ViewHolder(private val binding: ItemGridHorizontalMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(data: MovieModel) {
                binding.apply {
                    tvTitleMovie.text = data.title
                    tvRating.text = data.voteAverage?.toDouble().toString()
                    Glide.with(itemView.context).load(data.posterUri).into(imgMovie)

                    itemView.setOnClickListener {
                        onItemClicked?.invoke(data)
                    }
                }
            }

    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<MovieModel>() {
            override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
                return oldItem == newItem
            }

        }
    }
}
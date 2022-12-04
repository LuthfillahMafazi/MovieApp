package com.zii.movieapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zii.core.main.domain.model.MovieModel
import com.zii.movieapp.databinding.ListMovieBinding

class ListMovieAdapter(private val onItemClicked: (MovieModel) -> Unit): ListAdapter<MovieModel, ListMovieAdapter.ViewHolder>(diffCallback) {

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

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListMovieAdapter.ViewHolder {
        val view =
            ListMovieBinding.inflate(
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

    inner class ViewHolder(private val binding: ListMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: MovieModel) {
            binding.apply {
                tvTitle.text = data.title
                tvGenre.text = data.genre?.map { it?.name }?.joinToString { ", " }
                tvOverview.text = data.overview
                Glide.with(itemView.context).load(data.posterUri).into(imgMovie)

                itemView.setOnClickListener {
                    onItemClicked(data)
                }
            }
        }

    }
}
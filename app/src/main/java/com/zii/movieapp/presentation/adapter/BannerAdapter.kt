package com.zii.movieapp.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zii.core.main.domain.model.MovieModel
import com.zii.movieapp.databinding.ItemBannerBinding
import io.github.vejei.carouselview.CarouselAdapter

class BannerAdapter: CarouselAdapter<BannerAdapter.ViewHolder>() {
    private val movies = mutableListOf<MovieModel>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(movies: List<MovieModel>?) {
        this.movies.clear()
        this.movies.addAll(movies ?: listOf())
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemBannerBinding) :
    RecyclerView.ViewHolder(binding.root) {
        fun bind(data: MovieModel) {
            binding.apply {
                Glide.with(itemView.context).load(data.backdropUri).into(imgBanner)
                tvTitle.text = data.title
            }
        }
    }

    override fun onCreatePageViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindPageViewHolder(holder: ViewHolder, position: Int) {
        movies.getOrNull(position)?.let { holder.bind(it) }
    }

    override fun getPageCount(): Int = movies.size

}
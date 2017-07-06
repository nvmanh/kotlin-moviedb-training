package com.framgia.moviedbtraining.movies

import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.framgia.moviedbtraining.App
import com.framgia.moviedbtraining.R
import com.framgia.moviedbtraining.constants.Constants
import com.framgia.moviedbtraining.model.Movie

class NowPlayingAdapter(private var movies: List<Movie>,
    private val rowLayout: Int) : RecyclerView.Adapter<NowPlayingAdapter.MovieViewHolder>() {

  class MovieViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    internal var imageView: ImageView = v.findViewById(R.id.imageViewPoster) as ImageView

  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(rowLayout, parent, false)
    return MovieViewHolder(view)
  }

  override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
    val currentMovie = movies[position]
    if (!TextUtils.isEmpty(currentMovie.posterPath))
    Glide.with(App.self()).load(Constants.WEB_URL + currentMovie.posterPath!!).into(
        holder.imageView)
  }

  override fun getItemCount(): Int {
    return movies.size
  }
}
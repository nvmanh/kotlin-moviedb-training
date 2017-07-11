package com.framgia.moviedbtraining.favorites

import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.framgia.moviedbtraining.App
import com.framgia.moviedbtraining.constants.Constants
import com.framgia.moviedbtraining.model.Movie
import kotlinx.android.synthetic.main.item_favourites.view.*

class FavouritesAdapter(private var movies: List<Movie>,
    private val rowLayout: Int) : RecyclerView.Adapter<FavouritesAdapter.MovieViewHolder>() {

  class MovieViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    fun binData(movie: Movie) {
      with(movie) {
        itemView.tvName.text = movie.title
        itemView.tvDate.text = movie.releaseDate.toString()
        itemView.tvRating.text = movie.voteAverage.toString()
        if (!TextUtils.isEmpty(movie.posterPath)) {
          Glide.with(App.self()).load(Constants.WEB_URL + movie.posterPath!!).into(
              itemView.ivPoster)
        }
      }
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(rowLayout, parent, false)
    return MovieViewHolder(view)
  }

  override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
    val currentMovie = movies[position]
    holder.binData(currentMovie)
  }

  override fun getItemCount(): Int {
    return movies.size
  }
}
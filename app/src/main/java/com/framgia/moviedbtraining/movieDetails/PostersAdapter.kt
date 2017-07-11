package com.framgia.moviedbtraining.movieDetails

import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.framgia.moviedbtraining.App
import com.framgia.moviedbtraining.constants.Constants
import com.framgia.moviedbtraining.model.MovieDetails
import kotlinx.android.synthetic.main.item_posters.view.*

class PostersAdapter(private var movies: List<MovieDetails.Posters>,
    private val rowLayout: Int) : RecyclerView.Adapter<PostersAdapter.MovieViewHolder>() {

  class MovieViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    fun bindData(movie: MovieDetails.Posters) {
      with(movie) {
        if (!TextUtils.isEmpty(movie.filePath)) {
          Glide.with(App.self()).load(Constants.WEB_URL + movie.filePath).into(
              itemView.imageViewPoster)
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
    holder.bindData(currentMovie)
  }

  override fun getItemCount(): Int {
    return movies.size
  }
}
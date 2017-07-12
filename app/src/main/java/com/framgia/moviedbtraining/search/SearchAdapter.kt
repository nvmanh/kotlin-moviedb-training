package com.framgia.moviedbtraining.search

import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.framgia.moviedbtraining.App
import com.framgia.moviedbtraining.R
import com.framgia.moviedbtraining.constants.Constants
import com.framgia.moviedbtraining.model.Movie
import kotlinx.android.synthetic.main.item_search_movie.view.*

/**
 * Created by FRAMGIA\pham.duc.nam on 10/07/2017.
 */

class SearchAdapter(private var movies: List<Movie>,
    private val rowLayout: Int) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(rowLayout, parent, false)
    return SearchViewHolder(view)
  }

  override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
    val currentMovie = movies[position]
    holder.binData(currentMovie)
  }

  override fun getItemCount(): Int {
    return movies.size
  }

  class SearchViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    fun binData(movie: Movie) {
      with(movie) {
        itemView.tvTitle.text = movie.title
        itemView.tvRate.text = movie.voteAverage.toString()
        itemView.tvOverview.text = movie.overview
        if (movie.releaseDate!!.length > 4) {
          itemView.tvDate.text = movie.releaseDate?.substring(0, 4)
        }
        if (!TextUtils.isEmpty(movie.posterPath)) {
          Glide.with(App.self())
              .load(Constants.WEB_URL + movie.posterPath!!)
              .placeholder(R.drawable.default_movie)
              .into(itemView.imgPoster)
        }
      }
    }
  }
}

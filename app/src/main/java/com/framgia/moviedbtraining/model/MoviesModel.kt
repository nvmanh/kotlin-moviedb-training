package com.framgia.moviedbtraining.model

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

/**
 * Created by FRAMGIA\pham.duc.nam on 17/07/2017.
 */
class MovieResponse {
  var results: MutableList<Movies> = mutableListOf()
}

@DatabaseTable(tableName = "movie_table")
data class Movies(
    @DatabaseField var id: String? = null,
    @DatabaseField var title: String? = null,
    @DatabaseField var release_date: String? = null,
    @DatabaseField var vote_average: String? = null,
    @DatabaseField var poster_path: String? = null,
    @DatabaseField var overview: String? = null
)

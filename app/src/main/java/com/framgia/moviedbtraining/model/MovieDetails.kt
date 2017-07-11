package com.framgia.moviedbtraining.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class MovieDetails : Serializable {

  @SerializedName("adult")
  var isAdult: Boolean = false
  @SerializedName("backdrop_path")
  var backdropPath: String? = null
  @SerializedName("belongs_to_collection")
  var belongsToCollection: Any? = null
  @SerializedName("budget")
  var budget: Int = 0
  @SerializedName("genres")
  var genres: List<Genre>? = null
  @SerializedName("homepage")
  var homepage: String? = null
  @SerializedName("id")
  var id: Int = 0
  @SerializedName("imdb_id")
  var imdbId: String? = null
  @SerializedName("original_language")
  var originalLanguage: String? = null
  @SerializedName("original_title")
  var originalTitle: String? = null
  @SerializedName("overview")
  var overview: String? = null
  @SerializedName("popularity")
  var popularity: Float = 0.toFloat()
  @SerializedName("poster_path")
  var posterPath: String? = null
  @SerializedName("production_companies")
  var productionCompanies: List<ProductionCompany>? = null
  @SerializedName("production_countries")
  var productionCountries: List<ProductionCountry>? = null
  @SerializedName("release_date")
  var releaseDate: String? = null
  @SerializedName("revenue")
  var revenue: Int = 0
  @SerializedName("runtime")
  var runtime: Int = 0
  @SerializedName("spoken_languages")
  var spokenLanguages: List<SpokenLanguage>? = null
  @SerializedName("status")
  var status: String? = null
  @SerializedName("tagline")
  var tagline: String? = null
  @SerializedName("title")
  var title: String? = null
  @SerializedName("video")
  var isVideo: Boolean = false
  @SerializedName("vote_average")
  var voteAverage: Float = 0.toFloat()
  @SerializedName("vote_count")
  var voteCount: Int = 0
  @SerializedName("posters")
  var posters: List<Posters>? = null

  companion object {
    private const val serialVersionUID = -2372186215564287763L
  }

  class Genre : Serializable {
    @SerializedName("id")
    var id: Int = 0
    @SerializedName("name")
    var name: String? = null

    companion object {
      private const val serialVersionUID = 4602367313038787458L
    }
  }

  class ProductionCompany : Serializable {
    @SerializedName("name")
    var name: String? = null
    @SerializedName("id")
    var id: Int = 0

    companion object {
      private const val serialVersionUID = -160109805736583295L
    }
  }

  class ProductionCountry : Serializable {
    @SerializedName("iso_3166_1")
    var iso31661: String? = null
    @SerializedName("name")
    var name: String? = null

    companion object {
      private const val serialVersionUID = 4440831045624953777L
    }
  }

  class SpokenLanguage : Serializable {
    @SerializedName("iso_639_1")
    var language: String? = null
    @SerializedName("name")
    var name: String? = null

    companion object {
      private const val serialVersionUID = 5240226041919011118L
    }
  }

  class Posters(
      @SerializedName("aspect_ratio")
      var aspectRation: Number?, @SerializedName("file_path")
  var filePath: String, @SerializedName("height")
  var height: Int = 0, @SerializedName("iso_639_1")
  var language: String?, @SerializedName("vote_average")
  var voteAverage: Float = 0.toFloat(), @SerializedName("vote_count")
  var voteCount: Int?, @SerializedName("width")
  var width: Int?) : Serializable
}
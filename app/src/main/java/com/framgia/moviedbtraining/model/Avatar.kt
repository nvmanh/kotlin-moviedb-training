package com.framgia.moviedbtraining.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Avatar(
    @SerializedName("gravatar") var gravatar: Gravatar?) : Serializable {
  class Gravatar(
      @SerializedName("hash") var hash: String?) : Serializable
}
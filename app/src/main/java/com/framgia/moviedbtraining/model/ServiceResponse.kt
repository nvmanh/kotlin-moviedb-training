package com.framgia.moviedbtraining.model

import com.google.gson.annotations.SerializedName

class ServiceResponse {
  @SerializedName("request_token")
  var token: String = ""
  @SerializedName("session_id")
  var session: String = ""
}
package com.framgia.moviedbtraining.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class User(
    @SerializedName("avatar") val avatar: Avatar?,
    @SerializedName("id") var id: Int?,
    @SerializedName("iso_639_1") var language: String?,
    @SerializedName("name") var name: String?,
    @SerializedName("include_adult") var includeAdult: Boolean?,
    @SerializedName("username") var username: String?) : Serializable
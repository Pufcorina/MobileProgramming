package com.example.exam.models

import com.google.gson.annotations.SerializedName

data class Entity (
    @field:SerializedName("id")
    var id: Int = 0,
    @field:SerializedName("title")
    var title: String = "",
    @field:SerializedName("description")
    var description: String = "",
    @field:SerializedName("album")
    var album: String = "new",
    @field:SerializedName("genre")
    var genre: String = "",
    @field:SerializedName("year")
    var year :Int = 0)
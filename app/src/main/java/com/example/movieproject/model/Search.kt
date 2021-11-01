package com.example.movieproject.model

import com.google.gson.annotations.SerializedName

data class Search(
    @SerializedName("Search")
    val resultSearch:MutableList<movie>
)

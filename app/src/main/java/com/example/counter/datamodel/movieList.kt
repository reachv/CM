package com.example.counter.datamodel

data class movieList(
    var results : List<movie>,
    var page : Int,
    var total_pages : Int,
    var total_results : Int
) {
}
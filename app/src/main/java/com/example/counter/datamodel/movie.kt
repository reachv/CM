package com.example.counter.datamodel

import java.io.Serializable

data class movie(var title : String, var poster_path : String, var overview : String, var backdrop_path : String, var id : Int, var vote_average : Double) : Serializable{

}